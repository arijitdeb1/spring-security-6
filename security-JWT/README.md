# spring-security-6
managing security for web applications using spring-security-6, OAuth2, OIDC etc.

#### _<ins>Required changes to integrate Token generation/validation with spring security</ins>_

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
 3. set _`Authorization`_ header to CORS configuration at _`ApiSecurityConfig`_ to acknowledge client/UI to support the header where we'll be setting the token going forward.
 4. Create token generation filter logic in _`JWTTokenGenerationFilter`_ class and token validation logic in _`JWTTokenValidatorFilter`_ class.
 5. Register the above filters at  _`ApiSecurityConfig`_ 
 6. Create an api - _`/custom_login`_ in _`LoginController`_ which will return the generated token and will be used to test the integration.
 7. Create a 256 bit key in the SecurityConstants class to be used as a signature for JWT Token.
 
 
 #### _<ins>Debugging end to end Flow to generate/validate JWT Tokens</ins>_
 
 1. Import the collection - _`token-generation.postman_collection`_ to POSTMAN.
 2. In POSTMAN, Open request custom_login >> Authorization >> Type = basic Auth >> Set appropriate username/password as configured in database.
 3. On execution of _`/custom_login`_ api, below sequence of events will start -
       
       - _`JWTTokenValidatorFilter`_ will get called as it was configured before _`BasicAuthenticationFilter`_ but _`shouldNotFilter`_  will skip as requested api is _`/custom_login`_.
       - _`authenticate`_ method in _`EmployeeAuthenticationProvider`_ will authenticate the user based on the _`Basic Auth`_ credentials in _`Authorization`_ header.
       - _`JWTTokenGenerationFilter`_ will start after validating _`shouldNotFilter`_ condition and generates JWT token using the user credentials. It also set the _`Authorization`_ header in response with JWT token value.
       - In response to _`/custom_login`_ we're also sending the JWT Token value in the response body.
       - For next call to any api like _`/plant`_ we don't have to pass usrname/password as _`Basic Auth`_ rather will copy the JWT token from _`/custom_login`_ response and add to _`Authorization`_ header of _`Type = Bearer Token`_.
       - On executing _`/plant`_ api, _`JWTTokenValidatorFilter`_ as configured will start first and validate the token received as part of request header _`Authorization`_. 
       - Any subsequent request to any api after _`/custom_login`_ will require the JWT Token to be set as _`Bearer Token`_ in _`Authorization`_ header.
       - Expiry for JWT Token has been configured as part of Token generation and can be changed to test expiration scenarios.
       - If expired, user will be denied to any apis and have to re-execute _`/custom_login`_ with _`Basic Auth`_ to re-generate JWT token.
       
 

