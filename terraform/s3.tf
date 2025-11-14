resource "aws_s3_bucket" "source_files_bucket" {
  bucket = "${var.app_name}-files"
}

resource "aws_s3_bucket" "zips_bucket" {
  bucket = "${var.app_name}-zips"
}

resource "aws_s3_bucket" "lambda_code_bucket" {
  bucket = "${var.app_name}-lambda-code"
}