# Lunatech Assignment
Lunatech Labs Assignment

## Run
```
mvn spring-boot:run
```

## Test Report
```
mvn surefire-report:report
```
## web application URL
 http://localhost:8080
 
## swagger UI
http://localhost:8080/swagger-ui.html

## swagger API doc
http://localhost:8080/v2/api-docs
 
## Bonus features implemented
1. Partial/fuzzy Country Name
2. Top 10 most common runway identifications


Anatomy of the application
The application is built using the spring Framework. The 'heart' of the application resides in the folders

##### models
Here we defined the model that describe our data. Basically it are just datastructures via which we can interact with our data (in our case, csv/database). In this application, we created models Country, Airport and Runway.

##### Repo
repository classes airportRepo, countryRepo,runwayRepo is implementing crudRepository for jpa data retrieval.

##### controllers /web/
Each controller consists of actions, which do something with the requests going to the server. In our application, we have QueryController to handle queries, ReportController to generate a report, and HomeController to serve a welcome page.
Also, FuzzySearchController is implemented to search country by supplying prefix of it.

##### DAO services
services are implemented for the functions to return desired data to controller.


##### resource/template
This folder consists of templates to make the html pages rendered by the actions.


## Frameworks/Toolkits
1. Spring Boot
2. Spring Data JPA
3. Spring MVC
4. Lombok
5. Swagger API doc
6. jackson-dataformat-csv for csv parsing 