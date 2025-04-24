resource "yandex_vpc_network" "devops-network" {
  name = var.network_name
}

resource "yandex_vpc_subnet" "devops-subnet" {
  network_id     = yandex_vpc_network.devops-network.id
  v4_cidr_blocks = var.v4_cidr_blocks
}

resource "yandex_vpc_address" "devops-static-ip" {
  name = var.ip_address

  external_ipv4_address {
    zone_id = var.zone
  }
}