# Kafka Playground

This project facilitates playing around with Redpanda to produce and consume messages, as well as managing Avro schemas through a schema registry.

## Prerequisites

- **Docker** and **Docker Compose** installed on your machine.
- **Gradle** installed to manage the build process.

## Getting Started

### 1. Start Redpanda with Docker Compose

To start the Redpanda container (which includes the Kafka broker and the schema registry), use the provided `docker-compose.yml` file.

#### Steps:

1. Ensure your docker-compose.yml is in the root directory.
2. Run the following to start the Redpanda container:

```bash
docker-compose up -d
```

### 2. Register the Avro Schemas

This project uses the Kafka Schema Registry Gradle Plugin to register Avro schemas with the schema registry running in your Docker container.

#### Steps:

1. Run the following to register your schemas:

```bash
./gradlew registerSchemasTask
```

Note: Ensure your `.avsc` files are located in the `src/main/avro` directory

#### Other useful registry interactions:

- To check the compatibility of your current schema with the schema in the registry, run the following:

```bash
./gradlew testSchemasTask
```

- To change the compatibility level, run the following:

```bash
./gradlew configSubjectsTask
```

### 3. Producing messages to Kafka

Once Redpanda is up and running, and your schemas are registered, you can produce messages to Kafka.

#### Steps:

1. Build the Spring Boot application:

```bash
./gradlew clean build
```

- This will build the application and generate the necessary DTO from the avro files.

2. Start the Spring Boot application:

```bash
./gradlew bootRun
```

3. Make a POST request to the `/send` endpoint. For example, using curl:

```bash
curl -X POST -H "Content-Type: application/json" -d '"Hello, Redpanda!"' http://localhost:8080/send
```

### 3. Consuming messages:

To verify that the message has been sent to your Redpanda Kafka topic, you can either:

#### Use the `rpk` tool to consume messages from the topic:

1. Get the id of the redpanda container by running the following in your terminal:

```bash
docker ps
```

2. Access the container:

```bash
docker exec -it <container_id> bash
```

3. Run the following:

```bash
rpk topic consume your-topic-name --brokers localhost:9092
```

#### Use the Kafka consumer in the Spring Boot Application to consume and log the message:

1. Uncomment out the KafkaConsumer class and run the application again.

### 4. Stopping the Containers

To stop the Redpanda container, run:

```bash
docker-compose down
```

## Getting Started

- To check server-wide compatibility:

```bash
curl -X GET \
  -H "Content-Type: application/vnd.schemaregistry.v1+json" \
  http://localhost:8081/config
```

- To change server-wide compatibility:

```bash
curl -X PUT -H "Content-Type: application/json" --data '{"compatibility": "FULL"}' http://localhost:8081/config
```

- To change compatibility of a particular schema: adjust the `config` block in the `build.gradle`
