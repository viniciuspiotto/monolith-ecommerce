variable "aws_region" {
  description = "AWS region to implant the resources"
  type = string
  default = "us-east-2"
}

variable "app_name" {
  description = "Common prefix to resources"
  type = string
  default = "3d-ecommerce"
}

variable "lambda_source_dir" {
  description = "Path to zip model textures and meshes lambda file"
  type = string
  default = "../lambdas/zip-model-texture-mesh"
}

variable "lambda_handler_name" {
  description = "Name to zip model textures and meshes lambda file"
  type = string
  default = "bootstrap"
}