# spring-security-6
#### _**<ins>managing user credentials in a database(H2)</ins>**_

##### <ins>**_Configure embedded H2 database_**</ins>:
   1. Add below dependencies
     
     <dependency>
   		<groupId>com.h2database</groupId>
   		<artifactId>h2</artifactId>
   		<scope>runtime</scope>
   	</dependency>
   	
   	   	
   2. Add below in _`application.properties`_
      
    spring.h2.console.enabled=true
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:h2:~/userdb
    spring.datasource.username=root
    spring.datasource.password=root
    
   Above mentioned url/username/password will be used to connect to H2 console
   
   3. Go to users directory (for eg. C:\Users\admin) and save a file as below-
         
          File name: userdb.mv.db
          Save as type: All Files
          
   4. Open localhost:8080/h2-console in browser and provide url/username/password from above.
   5. Spring security framework will block H2 console. To enable permit request from _`/h2-console`_ and disable headers like _`csrf`_ and _`frameOption`_ during bean generation in _`ApiSecurityConfig`_ class.
   
   
   
##### <ins>**Configure tables(_as defined by spring security framework_) required for spring security demo**</ins>
   
   1. Copy the DDL queries from _`org\springframework\security\spring-security-core\6.2.1\spring-security-core-6.2.1-sources.jar!\org\springframework\security\core\userdetails\jdbc\users.ddl`_  OR below DDL queries
   
    create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
    create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
    create unique index ix_auth_username on authorities (username,authority);
    
   2. Insert some and user details and corresponding authorities.
    
    
    
##### <ins>**_Configure user credentials required for spring security demo_**</ins>
   
   1. Add below dependencies to pom.xml
   
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jdbc</artifactId>
       </dependency>
       <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jpa</artifactId>
       </dependency>
       <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-security</artifactId>
       </dependency>
    
   Add required '_`connector`_' dependency if any other database other than H2 is used. 
   
   2. Create bean of _`JdbcUserDetailsManager`_ which holds required datasource and user table details. Refer _`ApiSecurityConfig`_ class.
   3. Define _`PasswordEncoder`_ bean in _`ApiSecurityConfig`_ which will be used by spring security framework in the background to encode/decode passwords. _`NoOpPasswordEncoder`_ will not perform any encoding for password and will retain plain text.
   4. Include a debug point to _`loadUsersByUsername`_ method of _`JdbcUserDetailsManager`_ to evaluate if application is able to fetch details from database.
   5. Comment out _`@Service`_ annotation on _`EmployeeDetailsService`_ class in _`service`_ package as it's conflicting with _`UserDetailsService`_ implementation here.
   6. Start application and provide credentials defined in database.
   
##### <ins>**Configure tables(_as defined by application developer_) required for spring security demo**</ins>
   1. Create a new table as below and insert user details.
   
          create table employee(id int not null primary key, email varchar_ignorecase(500) not null,pwd varchar_ignorecase(200) not null, role varchar_ignorecase(200)  not null);
   2. Create a entity class for _`Employee`_ and define all the required fields.
   3. Create _`EmployeeDetailsService`_ class in _`service`_ package extending _`UserDetailsService`_ and overriding _`loadUserByUserName`_ method.
   4. Remove/Comment reference/bean of _`UserDetailsService`_ in _`ApiSecurityConfig`_ or else spring security framework will ran into ambiguity between _`UserDetailsService`_ and _`EmployeeDetailsService`_.
   5. Start the application and login using credentials defined in _`employee`_ table.
   
   ##### <ins>**Configure REST endpoint to allow Users to register their credentials in employee table**</ins>
   1. Create _`LoginController`_ with _`PostMapping("/register")`_
   2. PermitAll for _`/register`_ in _`ApiSecurityConfig`_. Disable _`csrf`_ if not done already.
   3. Start the application.
   4. Import _`registerUser.postman_collection`_ to POSTMAN and register a new user 'John'. 
   5. Login to authenticated apis using user credentials for John. 
   
    