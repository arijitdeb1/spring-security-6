# spring-security-custom
#### _<ins>configure multiple in-memory users for authentication</ins>_
whenever we want to define custom security requirement and secure application endpoints, we have to define a bean of `SecurityFilterChain` specifying the access restrictions for application endpoints. These configurations will be loaded in `SpringContext` during application startup and will be referred while invoking the corresponding endpoint 
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
    **/car** , **/bird** will require authentication.
    
    **/animal**, **/plant** will not require authentication
    
    **/insect** will be denied with HTTP status 403. 

 