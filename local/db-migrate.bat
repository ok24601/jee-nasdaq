mvn -f config/mysql/pom.xml flyway:migrate -Ddb.url=jdbc:mysql://localhost:3306/nasdaq -Ddb.username=root -Ddb.password=pass
