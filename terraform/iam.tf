resource "aws_iam_role" "zip_lambda_role" {
  name = "zip-lambda-role"

  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      },
    ]
  })
}

resource "aws_iam_policy" "zip_lambda_policy" {
  name = "zip-lambda-policy"

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Sid    = "AllowCloudWatchLogs"
        Effect = "Allow"
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ]
        Resource = "arn:aws:logs:*:*:*"
      },
      {
        Sid    = "AllowSQSTrigger"
        Effect = "Allow"
        Action = [
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage",
          "sqs:GetQueueAttributes"
        ]
        Resource = aws_sqs_queue.request_queue.arn
      },
      {
        Sid    = "AllowS3SourceRead"
        Effect = "Allow"
        Action = [
          "s3:GetObject",
          "s3:ListBucket"
        ]
        Resource = [
          aws_s3_bucket.source_files_bucket.arn,
          "${aws_s3_bucket.source_files_bucket.arn}/*"
        ]
      },
      {
        Sid    = "AllowS3DestWrite"
        Effect = "Allow"
        Action = "s3:PutObject"
        Resource = "${aws_s3_bucket.zips_bucket.arn}/*"
      },
      {
        Sid    = "AllowSQSNotifySend"
        Effect = "Allow"
        Action = "sqs:SendMessage"
        Resource = aws_sqs_queue.notify_queue.arn
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_attach" {
  role = aws_iam_role.zip_lambda_role.name
  policy_arn = aws_iam_policy.zip_lambda_policy.arn
}

resource "aws_iam_user" "spring_app_user" {
  name = "${var.app_name}-spring-app-user"
}

resource "aws_iam_policy" "spring_app_policy" {
  name = "${var.app_name}-spring-app-policy"

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Sid    = "AllowSourceBucketWriteRead"
        Effect = "Allow"
        Action = [
          "s3:PutObject",
          "s3:PutObjectAcl",
          "s3:GetObject",
          "s3:DeleteObject"
        ]
        Resource = "${aws_s3_bucket.source_files_bucket.arn}/*"
      },
      {
        Sid    = "AllowBucketList"
        Effect = "Allow"
        Action = "s3:ListBucket"
        Resource = aws_s3_bucket.source_files_bucket.arn
      },
      {
        Sid    = "AllowDestinationBucketRead"
        Effect = "Allow"
        Action = "s3:GetObject"
        Resource = "${aws_s3_bucket.zips_bucket.arn}/zips/*"
      },
      {
        Sid    = "AllowSQSSendMessage"
        Effect = "Allow"
        Action = "sqs:SendMessage"
        Resource = aws_sqs_queue.request_queue.arn
      },
      {
        Sid    = "AllowSQSNotifyRead"
        Effect = "Allow"
        Action = [
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage",
          "sqs:GetQueueAttributes"
        ]
        Resource = aws_sqs_queue.notify_queue.arn
      }
    ]
  })
}

resource "aws_iam_user_policy_attachment" "spring_app_attach" {
  user       = aws_iam_user.spring_app_user.name
  policy_arn = aws_iam_policy.spring_app_policy.arn
}

resource "aws_iam_access_key" "spring_app_keys" {
  user = aws_iam_user.spring_app_user.name
}