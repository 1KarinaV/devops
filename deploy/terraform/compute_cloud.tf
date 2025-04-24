resource "yandex_compute_instance" "devops-vm" {
  name = var.compute_cloud_name
  platform_id = "standard-v1"

  resources {
    cores  = 4
    memory = 4
  }

  boot_disk {
    initialize_params {
      image_id = var.image_id
      size = var.size
    }
  }

  network_interface {
    subnet_id = yandex_vpc_subnet.devops-subnet.id
    nat = true
    nat_ip_address = yandex_vpc_address.devops-static-ip.external_ipv4_address.0.address
  }

  metadata = {
    ssh-keys = "debian:${file("~/.ssh/github.pub")}"
    user-data = "${file("./init/init.sh")}"
  }
}

output "external_ip" {
  value = yandex_compute_instance.devops-vm.network_interface.0.nat_ip_address
}