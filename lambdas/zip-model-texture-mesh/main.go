package main

import (
	"archive/zip"
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/feature/s3/manager"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/aws-sdk-go-v2/service/sqs"
	"log"
	"os"
)

type ZipRequestPayload struct {
	ModelID     string            `json:"modelId"`
	ModelS3Keys map[string]string `json:"modelS3Keys"`
}

type ZipNotificationPayload struct {
	ModelID    string `json:"modelId"`
	ZipFileKey string `json:"zipFileKey"`
}

var (
	S3Downloader      *manager.Downloader
	S3Uploader        *manager.Uploader
	SQSClient         *sqs.Client
	sourceBucket      string
	destinationBucket string
	notifyQueueURL    string
)

func init() {
	cfg, err := config.LoadDefaultConfig(context.TODO())
	if err != nil {
		log.Fatal("Error to initialize aws config")
		return
	}

	s3Client := s3.NewFromConfig(cfg)
	SQSClient = sqs.NewFromConfig(cfg)

	S3Downloader = manager.NewDownloader(s3Client)
	S3Uploader = manager.NewUploader(s3Client)

	sourceBucket = os.Getenv("SOURCE_BUCKET_NAME")
	destinationBucket = os.Getenv("DESTINATION_BUCKET_NAME")
	notifyQueueURL = os.Getenv("NOTIFY_QUEUE_URL")

	if sourceBucket == "" || destinationBucket == "" || notifyQueueURL == "" {
		log.Fatal("enviroments SOURCE_BUCKET_NAME, DESTINATION_BUCKET_NAME, NOTIFY_QUEUE_URL are required")
	}
}

func handler(ctx context.Context, sqsEvent events.SQSEvent) error {
	for _, message := range sqsEvent.Records {
		log.Printf("processing message ID: %s", message.MessageId)

		if err := processMessage(ctx, message.Body); err != nil {
			log.Printf("fail to process message %s: %v", message.MessageId, err)
			return err
		}

		log.Printf("message %s processed successfully.", message.MessageId)
	}
	return nil
}

func processMessage(ctx context.Context, body string) error {
	var payload ZipRequestPayload
	if err := json.Unmarshal([]byte(body), &payload); err != nil {
		return fmt.Errorf("fail to unmarshal JSON: %w", err)
	}

	if payload.ModelID == "" || len(payload.ModelS3Keys) == 0 {
		return fmt.Errorf("modelId or modelS3Keys are required")
	}

	localZipPath := fmt.Sprintf("/tmp/%s.zip", payload.ModelID)
	defer func(name string) {
		err := os.Remove(name)
		if err != nil {
			log.Printf("error removing file %s: %v\n", name, err)
		}
	}(localZipPath)

	zipFile, err := os.Create(localZipPath)
	if err != nil {
		return fmt.Errorf("fail to create local zip: %w", err)
	}
	defer func(zipFile *os.File) {
		err = zipFile.Close()
		if err != nil {
			log.Printf("error closing file %s: %v\n", zipFile.Name(), err)
		}
	}(zipFile)

	zipWriter := zip.NewWriter(zipFile)

	log.Printf("starting zip to modelId: %s", payload.ModelID)

	for s3Key := range payload.ModelS3Keys {
		log.Printf("downloading: %s", s3Key)

		buffer := manager.NewWriteAtBuffer([]byte{})

		_, err = S3Downloader.Download(ctx, buffer, &s3.GetObjectInput{
			Bucket: aws.String(sourceBucket),
			Key:    aws.String(s3Key),
		})
		if err != nil {
			return fmt.Errorf("fail to download %s: %w", s3Key, err)
		}

		zipEntry, err := zipWriter.Create(s3Key)
		if err != nil {
			return fmt.Errorf("fail to create a zip entry %s: %w", s3Key, err)
		}

		if _, err := zipEntry.Write(buffer.Bytes()); err != nil {
			return fmt.Errorf("fail to write file %s on zip: %w", s3Key, err)
		}
	}

	if err = zipWriter.Close(); err != nil {
		return fmt.Errorf("fail to close zip writer: %w", err)
	}

	log.Printf("local zip created: %s", localZipPath)

	fileToUpload, err := os.Open(localZipPath)
	if err != nil {
		return fmt.Errorf("fail to reopen the local zip to upload: %w", err)
	}
	defer func(fileToUpload *os.File) {
		err = fileToUpload.Close()
		if err != nil {
			log.Printf("error closing upload file %s: %v\n", fileToUpload.Name(), err)
		}
	}(fileToUpload)

	zipS3Key := fmt.Sprintf("%s.zip", payload.ModelID)

	_, err = S3Uploader.Upload(ctx, &s3.PutObjectInput{
		Bucket: aws.String(destinationBucket),
		Key:    aws.String(zipS3Key),
		Body:   fileToUpload,
	})
	if err != nil {
		return fmt.Errorf("fail to make upload on S3: %w", err)
	}

	log.Printf("zip send to s3://%s/%s", destinationBucket, zipS3Key)

	notifyPayload := ZipNotificationPayload{
		ModelID:    payload.ModelID,
		ZipFileKey: zipS3Key,
	}

	notifyBody, err := json.Marshal(notifyPayload)
	if err != nil {
		return fmt.Errorf("fail to create JSON on notification: %w", err)
	}

	_, err = SQSClient.SendMessage(ctx, &sqs.SendMessageInput{
		MessageBody: aws.String(string(notifyBody)),
		QueueUrl:    aws.String(notifyQueueURL),
	})

	if err != nil {
		return fmt.Errorf("fail to send SQS notification: %w", err)
	}

	log.Printf("Notification for model %s sent to queue %s", payload.ModelID, notifyQueueURL)
	return nil
}

func main() {
	lambda.Start(handler)
}
