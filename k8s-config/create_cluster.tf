resource "null_resource" "create_kind_cluster" {
  provisioner "local-exec" {
    command = "kind create cluster --config cluster-config.yaml"
  }

  provisioner "local-exec" {
    command = "sleep 10"
  }
}
