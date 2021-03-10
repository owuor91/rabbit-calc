# rabbit-calc

This is a Java REST service powered by rabbitmq that performs these calculations:

- Addition
- Subtraction
- Multiplication
- Division

## Run Tests

```sh
mvn test
```

## Build and Run Project

```sh
docker-compose up -d
mvn -T 2 -pl rest,calculator clean spring-boot:run
```

# API Docs

### Addition

```
    GET localhost:8080/sum?a=123&b=27.32
    
    {
        "result": 150.32
    }
```

### Subtraction

```
    GET localhost:8080/subtract?a=123&b=27.32
    
    {
        "result": 95.68
    }
```

### Multiplication

```
    GET localhost:8080/multiply?a=123&b=27.32
    
    {
        "result": 3360.36
    }
```

### Division

```
    GET localhost:8080/divide?a=123&b=27.32
    
    {
        "result": 4.5021961932
    }
```

