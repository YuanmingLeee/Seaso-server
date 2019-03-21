# Seaso-server
Seaso spring server

## Install
### Install database
If you are under NTU LAN, you do not need to change the setting in `application.yml`. 
Otherwise, please install MySQL from the official [website](https://dev.mysql.com/downloads/mysql/) with your OS version.  

Then, you shall change the setting in `application.yml`:
```
datasource:
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: root    # this is your db username
    password: password # this is your db password
    driver-class-name: com.mysql.cj.jdbc.Driver
```
### Install maven
#### IDE User
If you are using an IDE (e.g. IDEA) which integrates Maven, you can skip this part. Please check your IDE manual for running mvn.

#### Windows User
Go [here](https://maven.apache.org/download.cgi). Install and add it into path. Detailed installation instruction can be
found [here](https://maven.apache.org/guides/getting-started/windows-prerequisites.html).
### Unix User
run
```
sudo apt-get install mvn
# if that does not work
sudo apt-get install maven
```

After installation, type
```
mvn -version
```
to verify.

### Install Project dependencies
#### IDE User
If you are using an IDE that supported Maven, click mvn->install for installation; Click mvn->clean for a cleanup.

#### Other User
On the very first time of running,
```
mvn install
```
This process will take a few minutes.
When there is another build/update, please remember to cleanup:
```
mvn clean
```
## Run
First, run test to see if the setting is correct:
```
mvn test
```
If all tests pass, you can pack up a jar file
```
mvn package
```
Then run the jar
```
java -jar target/seaso-0.0.1-SNAPSHOT.jar
```
If you are running/testing the project under IDEA, your IDE will automatically detect the entrance.   
On your first run, you have to init database data. Please run `data.sql` under `/db/mysql/` after you startup the server. 
