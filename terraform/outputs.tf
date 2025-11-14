output "spring_app_access_key_id" {
  description = "Access Key ID for the Spring Boot application user"
  value       = aws_iam_access_key.spring_app_keys.id
}

output "spring_app_secret_access_key" {
  description = "Secret Access Key for the Spring Boot application user"
  value       = aws_iam_access_key.spring_app_keys.secret
  sensitive   = true
}

output "aws_region" {
  description = "AWS region where the resources were created"
  value = var.aws_region
}

output "aws_s3_bucket_name" {
  description = "Bucket name to upload files"
  value = aws_s3_bucket.source_files_bucket.id
}

output "aws_sqs_model_created_queue_url" {
  description = "URL to SQS queue to send create model texture and meshes zip request"
  value = aws_sqs_queue.request_queue.id
}

output "aws_sqs_model_zip_completed_queue_url" {
  description = "URL to SQS queue to receive model zip notifications"
  value = aws_sqs_queue.notify_queue.id
}