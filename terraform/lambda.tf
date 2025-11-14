data "archive_file" "lambda_zip" {
  type = "zip"
  source_file = "${var.lambda_source_dir}/${var.lambda_handler_name}"
  output_path = "/tmp/zip-lambda.zip"
}

resource "aws_s3_object" "lambda_code" {
  bucket = aws_s3_bucket.lambda_code_bucket.id
  key = "zip-lambda-${data.archive_file.lambda_zip.output_md5}.zip"
  source = data.archive_file.lambda_zip.output_path
}

resource "aws_lambda_function" "zip_lambda" {
  function_name = "zip-lambda"

  s3_bucket = aws_s3_bucket.lambda_code_bucket.id
  s3_key = aws_s3_object.lambda_code.key

  role = aws_iam_role.zip_lambda_role.arn

  handler = var.lambda_handler_name
  runtime = "provided.al2023"

  timeout = 30
  memory_size = 256

  environment {
    variables = {
      SOURCE_BUCKET_NAME = aws_s3_bucket.source_files_bucket.id
      DESTINATION_BUCKET_NAME = aws_s3_bucket.zips_bucket.id
      NOTIFY_QUEUE_URL = aws_sqs_queue.notify_queue.id
    }
  }

  source_code_hash = data.archive_file.lambda_zip.output_base64sha256
}

resource "aws_lambda_event_source_mapping" "lambda_trigger" {
  event_source_arn = aws_sqs_queue.request_queue.arn
  function_name = aws_lambda_function.zip_lambda.arn
  batch_size = 1
}