### Prerequisites

* Java 17
  ```sh
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  ```
  
* Maven
  ```sh
  https://maven.apache.org/download.cgi
  ```
  
* MySql (Server and Workbench)
  ```sh
  https://dev.mysql.com/downloads/installer/
  ```
  
#### MySQL database prerequisites
* Import
  1. Go to MySQL Workbench
  2. Administration -> Management
  3. Data Import/Restore 
  4. Import Options -> Import from Self-Contained File
      ```sh
      .../sql-dump.sql
      ```
  5. Start Import 

* User
  1. Go to file from directory
      ```sh
      .../src/main/resources/hikari.properties
      ```
  2. Get username and password (change if needed)
  3. Go to MySQL Workbench
  4. Administration -> Management
  5. Users and Privileges
  6. Add Account -> Login
  7. Login Name: 
      ```sh
      username from .../src/main/resources/hikari.properties
      ```
  8. Password:
      ```sh
      password from .../src/main/resources/hikari.properties
      ```
  9. Confirm Password:
      ```sh
      password from .../src/main/resources/hikari.properties
      ```
  10. Apply
  11. Schema Privileges -> Add Entry...
  12. Selected schema: dropwizard_form
  13. Select "ALL" -> Apply

### Build

There should be already MySQL database that has the data from sql-dump.sql.

Credentials that can be changed are in __src/main/resources/hikari.properties__.

1. Unzip dropwizard-form-sql-master.zip
2. Run cmd
3. Go to project folder
   ```
   cd .../dropwizard-form-sql-master
   ```
4. Build the project
   ```sh
   mvn clean package
   ```
5. Run the project
   ```sh
   java -jar ./target/dropwizardTask-1.0-SNAPSHOT.jar server dropwizard-config.yml
   ```
6. Go to url
   ```sh
   http://localhost:8080/assets
   ```
