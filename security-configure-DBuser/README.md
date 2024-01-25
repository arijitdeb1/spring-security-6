# spring-security-6
#### _**<ins>managing user credentials in a database(H2)</ins>**_

<ins>**Configure embedded H2 database**</ins>:
   1. Add below dependencies
     
     <dependency>
   		<groupId>com.h2database</groupId>
   		<artifactId>h2</artifactId>
   		<scope>runtime</scope>
   	</dependency>
   	
   	   	
   2. Add below in _`application.properties`_
      
    spring.h2.console.enabled=true
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:h2:~/userdb
    spring.datasource.username=root
    spring.datasource.password=root
    
   Above mentioned url/username/password will be used to connect to H2 console
   
   3. Go to users directory (for eg. C:\Users\admin) and save a file as below-
         
          File name: userdb.mv.db
          Save as type: All Files
          
   4. Open localhost:8080/h2-console in browser and provide url/username/password from above.
   
   
   
   <ins>**Configure tables required for spring security demo**</ins>
   
   1. Copy the DDL queries from _`org\springframework\security\spring-security-core\6.2.1\spring-security-core-6.2.1-sources.jar!\org\springframework\security\core\userdetails\jdbc\users.ddl`_  OR below DDL queries
   
    create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
    create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
    create unique index ix_auth_username on authorities (username,authority);
    
   2. Insert some and user details and corresponding authorities.
    
    
    
   <ins>**Configure user credentials required for spring security demo**</ins>
   
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
   5. Start application and provide credentials defined in database.
   
    