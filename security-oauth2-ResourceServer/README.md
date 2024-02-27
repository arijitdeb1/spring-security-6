# Resource Server / Grant Type = Client Credentials
_This service will act as `Resource Server` i.e. it will require valid `Access Token` if any Client/Microservice wants to access any of it's APIs.
Client will use `Grant Type = Client Credentials` to generate the `Access Token` from `Keycloak` AuthZ server after proper authentication with `Client Id/Secret`. Same `Access Token` will be provided to the `Authorization` header while communicating with any API in the `Resource Server`._

 ![ScreenShot](/images/client-credentilas-GT.PNG?raw=true)
 
 ** _**do necessary Keycloak setup as mentioned in [security-oauth2-Keycloak-setup](https://github.com/arijitdeb1/spring-security-6/tree/main/security-oauth2-Keycloak-setup) before proceeding further**_


## Spring Boot setup for Resource Server

 1. Adding below dependency will convert the service into a _`Resource Server`_ which will make Spring Security to validate any request to it using _`Access Token`_.
 
               <dependency>
        		<groupId>org.springframework.boot</groupId>
        		<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
               </dependency> 

 2. This _`Resource Server`_ will be accessed from POSTMAN using a _`Client ID/Secret`_ registered with _`Keycloak`_ to imitate a `Service to Service communication following OAuth2 specifications`.
 3. No username/password authentication or related implementation will be required as we're authenticating a Service(who made request to _`Resource Server`_) based on _`Client ID/Secret`_.
 4. All apis defined in this service has been mapped to a Role in _`ApiSecurityConfig`_. The Client need to have required Roles to access the corresponding api. Roles are registered in Keycloak.
 5. _`KeycloakRoleConverter`_ will parse the _`Access Token`_ and fetch _`Roles`_ and convert them to object of _`GrantedAuthority`_ allowed and understood by Spring Security.
 6. _`KeycloakRoleConverter`_ object is injected in _`ApiSecurityConfig`_ bean configuration.
 7. _`Resource Server`_ and _`Auth Server`_ connectivity is configured in _`application.properties`_. At service startup _`Resource Server`_ will download the _`public certs/keys`_ from _`Auth Server`_ which will be used to decrypt and validate the _`Access Tokens`_ encrypted by the _`Private key`_ of _`Auth Server`_.
 8. Start the service.
 
 ## [Grant Type - `Client Credentials`] POSTMAN collection to get Access Token and request Resource Server apis
 **  **_[Keycloak setup](https://github.com/arijitdeb1/spring-security-6/tree/main/security-oauth2-Keycloak-setup) `#11` should be in place before executing below steps _**
  1. Import POSTMAN Collection - _`OAuth_Rest_Client.postman_collection`_ and update required details as per Keycloak configurations.
  
  **OR**
  ## [Grant Type - `Client Credentials`] POSTMAN setup to get Access Token and request Resource Server apis
  - Set Request - GET http://localhost:8080/plant
  - Set Authorization >> Grant Type - Client Credentials
                         Access Token Url - http://localhost:8180/realms/dev/protocol/openid-connect/token
                         Client ID - <Client ID>
                         Client Secret - <>
                         Scope - openid 
                         Get New Access Token >> proceed >> Use Token
  - Validate request header Authorization = Bearer <Access Token>
  - Validate outcomes based on Role configured in _`ApiSecurityConfig`_.
  
 ## Internal Flow - Validating Access Token by Resource Server
 _Every request to `Resource Server` will contain an `Access Token` embedded in the `Authorization` header. Below sequence flow demonstrates how `Spring Security` framework in `Resource Server` is validating the authenticity of token._
  
 ![ScreenShot](/images/resource-server.PNG?raw=true)