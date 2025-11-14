resource "aws_sqs_queue" "request_dlq" {
  name = "${var.app_name}-create-model-queue-dlq"
}

resource "aws_sqs_queue" "request_queue" {
  name = "${var.app_name}-create-model-queue"
}

resource "aws_sqs_queue" "notify_dlq" {
  name = "model-zip-completed-queue-dlq"
}

resource "aws_sqs_queue" "notify_queue" {
  name = "model-zip-completed"

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.notify_dlq.arn
    maxReceiveCount = 5
  })
}