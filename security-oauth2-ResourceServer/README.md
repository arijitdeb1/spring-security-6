# Resource Server
_This service will act as `Resource Server` i.e. will require using valid Access Token if any Client wants to access any of it's APIs_

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
  
  
  ## [Grant Type - `Authorization Code`]POSTMAN collection to get Authorization code, Access Token for a User registered with Keycloak</ins>
  **  **_Keycloak setup `#11` mentioned in above link should be in place _**
   1. Import POSTMAN Collection - _`OAuth_User_Client.postman_collection`_.
   
   **OR**
  ## [Grant Type - `Authorization Code`]POSTMAN setup to get Authorization code for a User registered with Keycloak
 
  1. Execute following URI from **browser**. Configure client_id/secret/redirect URI as required. As the below URI will land onto Keycloak Login page, hence can't be invoked from POSTMAN. 
      http://localhost:8180/realms/dev/protocol/openid-connect/auth?client_id=clientuser&response_type=code&scope=openid&redirect_uri=http://localhost:7080/sample 
    
  2. Provide username/password on screen for users configured in Keycloak and submit.
  3. Copy below highlighted part from updated URI in browser. This is _`Authorization code`_
   http://localhost:7080/sample?session_state=63015d07-e82a-4036-9e5f-b04b0f51f19c&iss=http%3A%2F%2Flocalhost%3A8180%2Frealms%2Fdev&_`code=e3ee13c7-2059-4a29-aeb0-7cd7534f374e.63015d07-e82a-4036-9e5f-b04b0f51f19c.a629804e-3255-47e5-8f23-2d3032345507`_.
   
  ## [Grant Type - `Authorization Code`]POSTMAN setup to get Access Token based on Authorization code from above
   
   4. POST http://localhost:8180/realms/dev/protocol/openid-connect/token
   5. Body - x-www-form-urlencoded
      client_id = 
      client_secret = 
      grant_type = authorization_code
      code = <above highlighted code> 
      redirect_uri =
      scope = openid
   6. Copy the Access Token from response
   7. If the request fails with "invalid_grant" error signifies that the Authorization Code from `#3` has expired and need to re-initiate the URI in `#1`. The expiry for Authorization Code is very limited and hence need to quickly copied and configured at `#5`.
   
  ## [Grant Type - `Authorization Code`]POSTMAN setup to call Rest apis in Resource Server
   8. set header Authorization = Bearer <Access Token>
   9. Validate outcomes based on Role configured in _`ApiSecurityConfig`_ and Keycloak.
      