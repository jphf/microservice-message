
# Simple Chat

A simple chat service implementation.

## Architect

![image](https://github.com/jphf/microservice-message/blob/master/architecture.PNG)

The prefix "microservice-" is omitted. For example, "web" refers to "microservice-web".

## Steps to Setup

**1. Clone the application**

```shell
git clone https://github.com/jphf/microservice-message.git
```

**2. Run the services with docker-compose**

```shell
docker-compose -f docker-compose.yml up -d
```
**3. Run the microservices**

* microservice-discovery-eureka
* microservice-api-send
* microservice-history
* microservice-receive
* microservice-user
* microservice-web

The app will start running at <http://localhost:8081>.
