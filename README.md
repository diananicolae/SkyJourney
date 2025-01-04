# SkyJourney

## Overview

SkyJourney is a flight booking application that allows users to search and filter flights, to create bookings, to check in for a flight, and to cancel a booking.
It provides authentication and authorization for users.

The application uses Docker images, being deployed on a Kubernetes cluster using `kind`. 
The frontend is a React application that communicates with the backend through REST APIs. 
The backend is composed of multiple Spring Boot microservices that communicate synchronously through HTTP requests, storing data in a MongoDB database.

OpenAPI documentation can be found by running the application and accessing http://localhost:8080/swagger-ui/index.html.

## Architecture

Each microservice is deployed using a Docker image. The images used for deployment can be found here: https://hub.docker.com/repositories/diananicolae.

The Kubernetes cluster is composed of a control plane and two worker nodes, and the infrastructure is built using Terraform.
Depending on their functionalities, the microservice nodes are part of different namespaces.

The cluster uses an Ingress controller to route traffic to the microservices.

The Dockerfiles and Kubernetes configuration files can be found in each microservice's folder.

## Microservices

1. **sky-journey-ui**

The frontend microservice that handles the user interface of the application. Runs on port `80`.

2. **sky-journey-core**

The core microservice that handles all the business logic of the application. Runs on port `8080`.
Functionality includes searching for flights, creating bookings, and getting booking details.

| Flight APIs                          | Details                                                                                                     | Examples                                                                                                                                                                                                                                                    |
|--------------------------------------|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **GET** /flights                     | List all existing flights.                                                                                  | GET http://localhost:8080/flights                                                                                                                                                                                                                           |
| **GET** /flights/{flightId}          | List a specific flight for a given flightId.                                                                | GET http://localhost:8080/flights/FL2820                                                                                                                                                                                                                    |
| **GET** /flights/search              | Search for flights with a set of filters. Can be filtered by airport origin, destination, airline and date. | GET http://localhost:8080/flights/search?origin=ATL&destination=LAX <br/> GET http://localhost:8080/flights/search?origin=ATL&destination=LAX&airlines=AIR_CANADA <br/> GET http://localhost:8080/flights/search?origin=ATL&destination=LAX&date=2024-01-13 |

| Booking APIs                           | Details                                        | Examples                                                                                                                                                                       |
|----------------------------------------|------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **GET** /bookings                      | List all existing bookings.                    | GET http://localhost:8080/bookings                                                                                                                                             |
| **GET** /bookings/{bookingId}          | List a specific booking for a given bookingId. | GET http://localhost:8080/bookings/706224ca-7b2f-4610-ba5b-22c193ac234e                                                                                                        |
| **POST** /bookings/create              | Create a booking for a specific flight.        | POST http://localhost:8080/bookings/create <br/> Content-Type: application/json <br/> ``` { "userId": 1, "flightId": 2, "paymentId": 1, "seat": "WINDOW", "paymentId": 5 } ``` |
| **PUT** /bookings/{bookingId}/check-in | Check-in for a booked flight.                  | PUT http://localhost:8080/bookings/706224ca-7b2f-4610-ba5b-22c193ac234e/check-in                                                                                               |
| **PUT** /bookings/{bookingId}/cancel   | Cancel a booked flight.                        | PUT http://localhost:8080/bookings/706224ca-7b2f-4610-ba5b-22c193ac234e/cancel                                                                                                 |
| **DELETE** /bookings                   | Delete all bookings.                           | DELETE http://localhost:8080/bookings                                                                                                                                          |
| **GET** /bookings/user/{userId}        | Get all existing bookings for a specific user. | GET http://localhost:8080/bookings/user/12345                                                                                                                                  |

3. **sky-journey-payment**

Payment microservice that handles payments in the application. Runs on port `8081`.
Integrates the Stripe payment gateway, creating a payment intent for a booking and confirming the payment.

| Payment APIs                   | Details                                | Examples                                                                                                                    |
|--------------------------------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| **POST** /payments/create      | Create a payment intent for a booking. | POST http://localhost:8081/payments/create <br/> Content-Type: application/json <br/> ``` { "userId": 1, "amount": 100} ``` |

4. **sky-journey-auth**

Authentication and authorization microservice that handles user log in and account creation. Runs on port `8082`.
API calls on /login return a JWT token that can be used to authorize the user for API calls to the core service.

| User APIs                      | Details                            | Examples                                                                                                                                                        |
|--------------------------------|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **POST** /login                | Log into an existing user account. | POST http://localhost:8082/login <br/> Content-Type: application/json <br/> ``` { "username": "test", "password": "test"} ```                                   |
| **GET** /users                 | Get all existing user accounts.    | GET http://localhost:8080/users                                                                                                                                 |
| **POST** /users/create-account | Create a new user account.         | POST http://localhost:8080/users/create-account <br/> Content-Type: application/json <br/> ``` { "name":"Test Test", username": "test", "password": "test"} ``` |

5. **sky-journey-db**

MongoDB database microservice, stores all the data of the application. Runs on port `27017`.
To start the Docker container run the following commands:

6. **sky-journey-dbadmin**

Mongo Express microservice, provides a user interface for the MongoDB database. Runs on port `8083`.

7. **sky-journey-portainer**

Portainer microservice, provides a user interface for the Kubernetes cluster. Runs on port `9000`.

## Setup

The steps to create and run a Kubernetes cluster locally with `kind` are:
1. Install `kind`: https://kind.sigs.k8s.io/docs/user/quick-start/
2. Apply the Terraform configuration to create the cluster infrastructure
```shell
cd k8s-config
terraform init && terraform apply # creates the cluster using create_cluster.tf
cd ..
kind get kubeconfig >> kube_config.yaml # save the kubeconfig file, add path to main.tf
terraform init && terraform apply # configures the cluster using main.tf
```
3. Wait for the cluster to be created and configured (the NGINX ingress controller can take a few minutes to start)
4. Apply the ingress configuration
```shell
kubectl apply -f k8s-config/cluster-ingress.yaml
```
5. Configure Portainer for cluster management
``` shell
kubectl get svc -n portainer # get portainer ClusterIP
kubectl port-forward -n portainer svc/sky-journey-portainer-service 9000:9000
```
Access the Portainer UI and create a new environment from <ClusterIP>:9001.

6. Access the application at http://localhost:8080 using port forwarding
```shell
kubectl port-forward -n ingress-nginx service/ingress-nginx-controller 8080:80
```
7. For more details about the cluster, access the Portainer UI or use the following commands
```shell
kubectl get pods --all-namespaces --field-selector 'metadata.namespace!=kube-system' # list all pods
kubectl get services --all-namespaces # list all services
kubectl logs <pod-name> # get the logs for a specific pod
kubectl port-forward -n core svc/sky-journey-core-service 8080:8080 
kubectl port-forward -n dbadmin svc/sky-journey-dbadmin-service 8083:8083
```

