# Order microservice
____
*__This project is a course project part. Provided functionality:__*
- *operations with customer*
- *operations with customer's order*
- *communication with catalogue service for approving/rejecting order with total price calculation*
- *Jaeger tracing available*
## Used technologies
**Spring, Gradle, Hibernate, Postgresql, Flywaydb, Swagger, Docker, RabbitMQ, Lombok, Eureka, Jaeger**
## How to start application
- Install RabbitMQ
- Eureka Discovery Server should be started
- Annotation processor should be enabled in Intellij IDEA settings.
- Set up database
    - install Postresql on your PC
    - choose DB port 5432 or leave it as default
    - set up `spring.datasource.username` and `spring.datasource.password` as `postgres` and `coolgame` **OR** change according fields in properties file if you want to set other ones
    - create database `orders` in postresql
- Change server port if needed
   
## How to use
- To see exposed endpoints and you can visit Swagger application page http://localhost:8082/swagger-ui.html after application is started.
- Communication with order service : incoming - rest, outcoming - rabbitmq.
- Once the order is placed it's current state is CREATED, approvement request is sended with rabbitmq to catalogue service which is checking are all items are present in catalogue and calcing the total price for order. Catalogue service sending a rest request for updating current state of order and it's total price.

# UML sequnce
[UML sequence diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=C4S2BsFMAIEkDsBuB7EBjGBbdAnZBnSHRdGYHAQ3nwrVGXgChHbhkdoBhcESeYZt178AtAD4A8jgAmRaNjR5CxUgC5oAB3C0YVaO1k51GgsH0aiFek1YhEVmFMPzcBIiQyMnchUvelxbw5fNxUMdTQAC0g0AGsQeABzaBAAM2g0AFd8Nkw5SAAPEBz8ABpoGhIk-Rk5BOhpKwoAIwpCRiE+YAAeESCXRVCPSHUDOUVIB2loKnwAdyJmfpDlYfExTibwZETMrFdVtWhKZuawTABHUdrgyHwaRJh7HkbrFjo7By4tnb2BvzCkGYsls9mAjhu-yGpA6P12+0Ghww4k2wAo23hUKRIwy6LQCWSYw4FEwyEy-Bm8GmODu-mqYEgmDKmkywFA1SJxzuJmoZGQ+lZGlZ0Auez2wMmHzBMFR6N+CIBw3eoGl3zRGL+K38nlh6vlWO1kBRcM1B0N6mgjzZBPkdweMFSeEwAuAQrMosg4q8kK1gN6so1Cuh4WgqUgIES8AyPC612cNPwPMI0GgEtBXwD+t9SuVn3B0GWZsB3uc2YCkh9ReG6kyGleNs5CVS7EwVhADEYIKlX0LiMNHRjogrparRytlJqhlzqt7iphnX4vVnwZx49SCXRk7q8GbOFb1iAA)

