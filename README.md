# One Aviation Backend

To start the web-app you'll need to download and install the latest version of MySQL Server
from https://dev.mysql.com/downloads/mysql/

Optionally you can install MySQL Workbench https://dev.mysql.com/downloads/workbench/

After installation, create a server on port 3306. Inside that server create a database. 
You can call it whatever you want, but here we'll call it `mydb`

Next, download this project, or import from version control. Create a new file 
`application.properties` and put the database related information there

```properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/mydb?useSSL=false
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Note: make sure you have the correct username and password.

You can then deploy the application. If you get any problems related to the database, try 
dropping and recreating the database

```mysql
DROP DATABASE mydb;
CREATE DATABASE mydb;
```

If the port is already in use, try running the following commands from command line (MacOS only):

```
lsof -i :8080
kill -9 <PID>
```


Response codes:

* 401 UNAUTHORIZED: the token has expired or doesn't exist. Frontend application needs to logout.
* 403 FORBIDDEN: your can't make that request (you are not the owner, or you don't have the authority)
* 400 BAD_REQUEST: Invalid request data