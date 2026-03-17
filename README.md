# benford

## Building & Running

To build or run the project, use one of the following tasks:

| Task                                    | Description                                                          |
| -----------------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`                        | Run the tests                                                        |
| `./gradlew build`                       | Build everything                                                     |
| `./gradlew buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `./gradlew buildImage`                  | Build the docker image to use with the fat JAR                       |
| `./gradlew publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `./gradlew run`                         | Run the server                                                       |
| `./gradlew runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

Make a request:
```shell
curl -X POST http://localhost:8080/api/v1/benford/check \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Invoice 123.45 payment 45 refund 302 amount 987",
    "significanceLevel": 0.05
  }'
```

## TODO

- Requirements review
- Structure sync
- Monitoring
- Api documentation
- Api security
- Configuration from environment
- Build/deploy