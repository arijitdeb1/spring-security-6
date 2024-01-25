# spring-security-custom
#### _<ins>configure multiple users for authentication</ins>_

 1. Include below dependencies to pom.xml
 
        <dependency>
 			<groupId>org.springframework.boot</groupId>
 			<artifactId>spring-boot-starter-security</artifactId>
 		</dependency>
 		<dependency>
 			<groupId>org.springframework.boot</groupId>
 			<artifactId>spring-boot-starter-web</artifactId>
 		</dependency>
 		
 2. Use _`InMemoryUserDetailsManager`_ and _`UserDetails`_ class to register users with role/permissions [not recommended for production]. Refer _`ApiSecurityConfig`_ class for implementation.
 3. Remove any username/password configuration from _`application.properties`_
 4. Refer bean configuration in _`ApiSecurityConfig`_ class for apis that require authentication. 

 