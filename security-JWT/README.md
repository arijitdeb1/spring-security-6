# Token based AuthN with Spring Security 

## Disadvantages of session(JSESSIONID) based approach 
 * JSESSIONID doesn't contain any user data
 * JSESSIONID is stored as a cookie in user browser which can be misused.
 
## Sequence Flow for Token based authentication

  ![ScreenShot](/images/jwt-arch.PNG?raw=true)
  
## Advantages of Token(JWT) based approach 
 * User credentials is shared only once during first attempt, all subsequent communications are validated using Token.
 * Tokens can be invalidated during any suspicious activities without invalidating the user credentials.
 * Tokens can be set with short life span.
 * Tokens can store user information like Roles/Authorities.
 * Multiple applications within same organization can be easily accessed with same Token without multiple Login attempt.
 * Token is Stateless to avoid any sticky session.
 
## Anatomy of JWT(JSON Web Token) 
  A JWT token(right in below image) consist of 3 parts - Header, Payload(User Details), Signature
  
   ![ScreenShot](/images/jwt-anatomy.PNG?raw=true)



## Required changes to integrate Token generation/validation with spring security

 1. Add below dependency to pom.xml
 
        <dependency>
 			<groupId>io.jsonwebtoken</groupId>
 			<artifactId>jjwt-api</artifactId>
 			<version>0.12.3</version>
 		</dependency>
 		<dependency>
 			<groupId>io.jsonwebtoken</groupId>
 			<artifactId>jjwt-impl</artifactId>
 			<version>0.12.3</version>
 			<scope>runtime</scope>
 		</dependency>
 		<dependency>
 			<groupId>io.jsonwebtoken</groupId>
 			<artifactId>jjwt-jackson</artifactId>
 			<version>0.12.3</version>
 			<scope>runtime</scope>
 		</dependency>
 		
 		
 2. Set the session STATELESS configuration at _`ApiSecurityConfig`_ as we'll be using tokens to validate user across api calls.
 3. set _`Authorization`_ header to CORS configuration at _`ApiSecurityConfig`_ to acknowledge client/UI to support the header where application/server be setting the token going forward.
 4. Create token generation filter logic in _`JWTTokenGenerationFilter`_ class. Once the user credentials are authenticated, the application server will generate the JWT token and set to _`Authorization`_ header. 
 5. A dummy secret key has been configured in _`SecurityConstants`_ class.
 6. _`shouldNotFilter`_ method in  _`JWTTokenGenerationFilter`_ defines the only /custom_login api access when corresponding filter will be executed as we don't want the filter to be executed for any subsequent api access.
 7. Token validation logic in _`JWTTokenValidatorFilter`_ class will be invoked for any subsequent api call after successful login authentication.
 5. Register the above filters at  _`ApiSecurityConfig`_  after/before user authentication.
 6. Create an api - _`/custom_login`_ in _`LoginController`_ which will return the generated token and will be used to test the integration.
 7. Create a 256 bit key in the _`SecurityConstants`_ class to be used as a signature for JWT Token from [here](https://acte.ltd/utils/randomkeygen).
 
 
## Debugging end to end Flow to generate/validate JWT Tokens
 
 1. Import the collection - _`token-generation.postman_collection`_ to POSTMAN.
 2. In POSTMAN, Open request custom_login >> Authorization >> Type = basic Auth >> Set appropriate username/password as configured in database.
 3. On execution of _`/custom_login`_ api, below sequence of events will start -
       
       - _`JWTTokenValidatorFilter`_ will get called as it was configured before _`BasicAuthenticationFilter`_ but _`shouldNotFilter`_  will skip as requested api is _`/custom_login`_.
       - _`authenticate`_ method in _`EmployeeAuthenticationProvider`_ will authenticate the user based on the _`Basic Auth`_ credentials in _`Authorization`_ header.
       - _`JWTTokenGenerationFilter`_ will start after validating _`shouldNotFilter`_ condition and generates JWT token using the user credentials. It also set the _`Authorization`_ header in response with JWT token value.
       - In response to _`/custom_login`_ we're also sending the JWT Token value in the response body.
       - For next call to any api like _`/plant`_ we don't have to pass username/password as _`Basic Auth`_ rather will copy the JWT token from _`/custom_login`_ response and add to _`Authorization`_ header of _`Type = Bearer Token`_.
       - On executing _`/plant`_ api, _`JWTTokenValidatorFilter`_ as configured will start first and validate the token received as part of request header _`Authorization`_. 
       - Any subsequent request to any api after _`/custom_login`_ will require the JWT Token to be set as _`Bearer Token`_ in _`Authorization`_ header.
       - Expiry for JWT Token has been configured as part of Token generation and can be changed to test expiration scenarios.
       - If expired, user will be denied to any apis and have to re-execute _`/custom_login`_ with _`Basic Auth`_ to re-generate JWT token.
       
 

