provider "kubernetes" {
  config_path = "~/master/an2/ims/SkyJourney/kube_config.yaml"
  config_context_cluster = "kind-kind"
}

resource "kubernetes_namespace" "core" {
  metadata {
    name = "core"
  }
}

resource "kubernetes_namespace" "portainer" {
  metadata {
    name = "portainer"
  }
}

resource "kubernetes_namespace" "dbadmin" {
  metadata {
    name = "dbadmin"
  }
}

resource "null_resource" "kind_cluster" {
  depends_on = [
    kubernetes_namespace.core,
    kubernetes_namespace.portainer,
    kubernetes_namespace.dbadmin
  ]

  triggers = {
    kubernetes_resources = sha256(join(",", [
      filesha256("sky-journey-auth/sky-journey-auth-deployment.yaml"),
      filesha256("sky-journey-auth/sky-journey-auth-service.yaml"),
      filesha256("sky-journey-core/sky-journey-core-deployment.yaml"),
      filesha256("sky-journey-core/sky-journey-core-service.yaml"),
      filesha256("sky-journey-db/sky-journey-db-deployment.yaml"),
      filesha256("sky-journey-db/sky-journey-db-service.yaml"),
      filesha256("sky-journey-dbadmin/sky-journey-dbadmin-deployment.yaml"),
      filesha256("sky-journey-dbadmin/sky-journey-dbadmin-service.yaml"),
      filesha256("sky-journey-payment/sky-journey-payment-deployment.yaml"),
      filesha256("sky-journey-payment/sky-journey-payment-service.yaml"),
      filesha256("sky-journey-portainer/sky-journey-portainer-deployment.yaml"),
      filesha256("sky-journey-portainer/sky-journey-portainer-service.yaml"),
      filesha256("sky-journey-portainer/portainer-sa.yaml"),
      filesha256("sky-journey-portainer/portainer-rbac.yaml"),
      filesha256("sky-journey-portainer/portainer-cluster.yaml"),
      filesha256("sky-journey-ui/sky-journey-ui-deployment.yaml"),
      filesha256("sky-journey-ui/sky-journey-ui-service.yaml"),
      "https://downloads.portainer.io/ce2-19/portainer-agent-k8s-nodeport.yaml"
    ]))
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-db/sky-journey-db-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-db/sky-journey-db-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-core/sky-journey-core-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-core/sky-journey-core-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-payment/sky-journey-payment-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-payment/sky-journey-payment-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-auth/sky-journey-auth-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-auth/sky-journey-auth-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-ui/sky-journey-ui-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-ui/sky-journey-ui-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-dbadmin/sky-journey-dbadmin-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-dbadmin/sky-journey-dbadmin-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-portainer/portainer-sa.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-portainer/portainer-cluster.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-portainer/portainer-rbac.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-portainer/sky-journey-portainer-deployment.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f sky-journey-portainer/sky-journey-portainer-service.yaml"
  }

  provisioner "local-exec" {
    command = "kubectl apply -f https://downloads.portainer.io/ce2-19/portainer-agent-k8s-nodeport.yaml"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "kind delete cluster"
  }
}

resource "kubernetes_namespace" "ingress_namespace" {
  depends_on = [null_resource.kind_cluster]

  metadata {
    name = "nginx-ingress"
  }
}

resource "null_resource" "install_nginx_ingress" {
  depends_on = [kubernetes_namespace.ingress_namespace]

  provisioner "local-exec" {
    command = "kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml"
  }
}
