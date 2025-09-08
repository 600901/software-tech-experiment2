# Software Technology Experiment 2
- the goal of this experiment is to create a simple REST API for a poll app using Spring Boot
### What was done
- decided to use IntelliJ Http Client for testing the simple REST API
- did setup for spring boot project repository with basic github action from previous experiment
- added the domain objects
- added a service class called DomainManager
- made a basic test in IntelliJ http file that tests the creation of users, polls, and checks voting and deletion
- made controllers for the endpoints, had to update some methods in DomainManager
- checked the http file tests
- used spring to write the test in http file as java

### What should be fixed, and other problems
- wanted to use response and request dto in api, but did not have time to change from using domain objects to do mapping between request/response dtos and domain objects in controller classes
- could write more generic test setup