# Faircorp Backend 
Based on the [Guillaume Ehret's](https://dev-mind.fr/) course.    
A Springboot application that was hosted on the clever cloud. 
- This applications allows the user to control the temperature of the rooms of each building, the state of the windows and heaters.

[Deployed API](http://faircorp-ainhajar-ibrahim.cleverapps.io/)  
The Front End for this project : [Front-End](https://github.com/Ainhajar-Ibrahim/faircorp_front_end)  
Documentation : [javadoc](https://ainhajar-ibrahim.github.io/faircop/docs/docs/index.html)
## Project setup
After cloning the project:  
Build the project and Run the following class faircop\src\main\java\com\emse\spring\faircorp\Application.java
OR
Run this command in the root file of the project
```
./gradlew bootRun
```  
Then in a browser open https://localhost:8080  
Since Security was disabled the credentiels aren't necessary.  
(A login page will pop up. Give the following credentiels:  
Username: admin  
Password: adminPassword)
## API TEST  
Go to https://localhost:8080/swagger-ui/ or http://faircorp-ainhajar-ibrahim.cleverapps.io/swagger-ui/ and test the API. 
## Docker 
Make sure you built the project and that the jar file is in the build/libs folder. 
Or else run the following command in the root file of the project to build it: 
```
./gradlew build
``` 
To create a container and run it, run the following command in the root file of the project (you need to have docker installed and running): 
```
docker run -p 8080:8080 faircorp/backend
```
## License
©️ 2022 Ainhajar Ibrahim
Licensed under the [LICENSE](https://github.com/Ainhajar-Ibrahim/faircop/blob/main/LICENSE.md)

