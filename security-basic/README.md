# spring-security-basic
 1. Add below dependency to pom.xml
    
    
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
 2. Start the application. Look for below in the generated logs -
**_Using generated security password: *****_**

 3. Open any browser and try to access the application [ _**localhost:8080/hello**_ ]
 4. Prompt appears to provide user/password. 

              Username: user
              Password: [_Using generated security password_]
 5. Credentials will be requested only during first login. Spring security framework will store the credentials and wouldn't ask re-authentication as long as same browser is used.
 6. Every restart of application will generate a new password. It can be avoided by creating custom username/password to be used for authentication.
 
 7. **Setting custom credentials**  
    copy below two in _**application.properties**_ -
    
    _spring.security.user.name = admin_
    
    _spring.security.user.password = admin_
    
    For additional properties, please refer spring documentation
    
    https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.security 
    
 9. Access the application using the credentials defined above.
 
    
