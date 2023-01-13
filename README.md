[![Java11](https://img.shields.io/badge/java-11-blue)](https://img.shields.io/badge/java-11-blue)
[![Gradle](https://img.shields.io/badge/gradle-v7.6-blue)](https://img.shields.io/badge/gradle-v7.6-blue)

# User API
This project contains two webservices
* One that allows to register a user
* One that displays the details of a registered user

### Environment setup
Minimal requirements: Please make sure following software is installed on your PC.
* [OpenJDK 11](https://adoptopenjdk.net/)
* Lombok plugin is installed in the IDE

### Compile, Run test
```
./gradlew clean build test
```

### Run the app (Method 1 : Using command line)
```
./gradlew bootRun
```

### Run the app (Method 2 : Using docker)
```
docker-compose -d up
```

### Database configuration 
```
# embedded memory databse using by the app
spring:
  datasource:
    url: jdbc:h2:mem:assessment_db
    username: assessment_user
    password: assessment_password
    
# embedded memory database using in the unit & integration tests
spring:
  datasource:
    url: jdbc:h2:mem:assessment_test_db
    username: assessment_test_user
    password: assessment_test_password
```

### Port
The application is running on the default port : 8080

### API documentation url (Swagger)
```
http://localhost:8080/swagger-ui/
```

### API request samples (Postman Collection)
[Postman Collection Json File](User_API.postman_collection.json)

### Example of the AOP Log
```
2023-01-13 13:40:40.415  INFO 16352 --- [io-8080-exec-10] c.a.userapi.controller.UserResource      : Calling endpoint with url = '/api/users/1' and GET method
2023-01-13 13:40:40.415  INFO 16352 --- [io-8080-exec-10] c.a.userapi.controller.UserResource      : Enter: UserResource -> findUserById with argument[s] = [1]
2023-01-13 13:40:40.419  INFO 16352 --- [io-8080-exec-10] c.a.userapi.controller.UserResource      : Exit: UserResource -> findUserById with result = <200 OK OK,UserDTO [id=1, username=username 1, birthDate=2000-01-13, residentialCountry=FR, phone=0650936791, gender=FEMALE,[]>
2023-01-13 13:40:40.419  INFO 16352 --- [io-8080-exec-10] c.a.userapi.controller.UserResource      : Execution time StopWatch 'UserResource -> findUserById': running time = 3439800 ns

```

_Enjoy !_
