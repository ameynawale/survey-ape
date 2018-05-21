# Survey Ape
This is a web based survey application that provides survey composition, distribution, and reporting. This application was hosted on AWS and Docker. The principles, patterns, and methodologies such as DI, AOP, MVC, ORM, and Transactions are implemented.

The names, email IDs, and students IDs of the members
Hanisha Thirtham - thirthamhanisha@gmail.com - 012462034
Amey Nawale -   amey.nawale12@gmail.com - 011979318
Manali Jain - manali.jain@sjsu.edu - 012420109
Suhas Hunsimar -  suhas.s.hunsimar@gmail.com - 012483692

The URL to access your app
http://www.surveyape.ga/

Build instructions
Backend:
1. mvn clean
2. mvn install -Dmaven.test.skip=true

Frontend:
1. npm install
2. npm start

## Getting-Started
These instructions will get you a copy of the project up and running on your AWS cloud. See deployment for notes on how to deploy the project on an EC2 instance.

### :computer: Technology Stack:
* Spring Boot
* Spring Data JPA
* React JS
* REST API
* JSON
* MySQL
* Docker
* AWS

### Prerequisites
What software or accounts you need to create or install
- [Docker Hub Account](https://hub.docker.com/)
	- yourHubUsername -> Docker ID
	- yourRepo        -> Repository created on Docker Hub
- Download -> [Docker for Mac (or Windows)](https://store.docker.com/search?type=edition&offering=community)
- [AWS Account](https://portal.aws.amazon.com/billing/signup#/start) and [EC2 instance creation](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html). (Choose Ubuntu Server 16.04 LTS)
- [Maven build tool](https://maven.apache.org/download.cgi)

## :cloud: Deployment 
### Docker
Docker Container

| Action        | Command       |
| ------------- | ------------- |
| List          | docker ps -a  |
| Remove        | docker rm "NAMES" |
| Stop          | docker stop “NAMES” |
| Logs          | docker logs “NAMES” |

Docker Images

| Action        | Command       |
| ------------- | ------------- |
| List          | docker images |
| Remove        | docker rmi “REPOSITORY/IMAGE ID”

### Steps to create & push image from local machine to Docker Hub
```
1. Navigate to the DIR containing Dockerfile
2. Generate jar file
   1. mvn clean install -Dmaven.test.skip=true
3. Generate docker image
   1. docker login -u=yourHubUsername
   2. docker build . -t <APPLICATION_NAME>:<TAG_NAME>
   3. docker images
   4. docker tag <IMAGE_ID> yourHubUsername/yourRepo:<TAG_NAME>
   5. docker push yourHubUsername/yourRepo
```
### Configuration on AWS console
```
4. Update and install docker
   1. sudo apt update
   2. sudo apt install docker.io
   3. sudo service docker start
   4. sudo usermod -a -G docker ubuntu
   5. exit (login again to ec2 instance)
   6. docker info

5. Login to docker hub
   1. docker login -u=yourHubUsername
   2. docker images
```
### Pull and Run MySQL container
```
6. Pull MySQL container
   1. docker pull mysql
7. Run MySQL container
   1. docker run --name <APPLICATION_DB_NAME> -e MYSQL_ROOT_PASSWORD=<password> -e MYSQL_DATABASE=<SCHEMA_NAME> -e MYSQL_USER=<user> -e MYSQL_PASSWORD=<password> -d -p=3306:3306 mysql:latest
 
Connecting to MySQL
MySQL Command-Line Tool
- docker exec -it <APPLICATION_DB_NAME> mysql -uroot -p

MySQL Workbench
- Connect to MySQL using the credentials created in Step 7.1 MYSQL_USER and MYSQL_PASSWORD
```
### Pull and Run Spring Boot container and Link with MySQL
```
8. Pull Spring Boot app
   1. docker pull yourHubUsername/yourRepo:<TAG_NAME>
9. Link and Run Spring Boot app
   1. docker run -p 8080:8080 --name <APPLICATION_NAME> --link <APPLICATION_DB_NAME>:mysql -d yourHubUsername/yourRepo:<TAG_NAME>
10. Check Logs
    - docker logs <APPLICATION_NAME>
    - docker logs <APPLICATION_DB_NAME>
```
### In case you need to stop & remove containers
```
Stop
- docker stop <APPLICATION_NAME>
- docker stop <APPLICATION_DB_NAME>
- docker rm <APPLICATION_DB_NAME> -v (-v : Option deletes the docker volume created for the mysql container)
Remove containers
- docker rm <APPLICATION_NAME>
- docker rm <APPLICATION_DB_NAME>
Remove images
- docker rmi yourHubUsername/yourRepo:airline-app
- docker rmi mysql:latest
```

## Authors

:octocat: **Suhas Hunsimar** - [HSuhas](https://github.com/HSuhas)

:octocat: **Ameya Nawale** - [ameynawale](https://github.com/ameynawale)

:octocat: **Manali Jain** - [ManaliJain06](https://github.com/ManaliJain06)

:octocat: **Hanisha Thirtham** - [thirthamhanisha](https://github.com/thirthamhanisha)
