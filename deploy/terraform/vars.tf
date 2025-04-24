// Common

variable "service_account_key_file" {
  type = string
}

variable "cloud_id" {
  type = string
}

variable "folder_id" {
  type = string
}

variable "zone" {
  type = string
}

// Network

variable "network_name" {
  type = string
}

variable "v4_cidr_blocks" {
  type = list(string)
}

variable "ip_address" {
  type = string
}

// Compute

variable "compute_cloud_name" {
  type = string
}

variable "image_id" {
  type = string
}

variable "size" {
  type = number
}