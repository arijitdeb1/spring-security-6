# spring-security-6
##### _Do necessary Keycloak setup mentioned in _`security-oauth2-Keycloak-setup`__
- **Resource Owner** - who requests _`OAuth Client`_ to authn/authz with _`Auth Server`_ and on successful validation, fetch the required details from _`Resource Server`_ [eg: any user]
- **OAuth Client** - who communicates with _`Auth Server`_ with required _`client id/secret`_ to authorize the _`Resource Owner`_ and get _`Access Tokens`_ to fetch required details from _`Resource Server`_ based on allowed _`scopes`_. [eg: any website that allows user to login using their Facebook,Google,Git credentials]
- **Authorization Server** - who authorizes the *`Resource Owne`*r and *`OAuth Clien`*t and generates _`Access Tokens/Refresh Tokens`_. [eg: Facebook,Google,Git etc]
- **Resource Server** - who provides *`Resource Owne`*r's details to *`OAuth Client`*s after successful authorization of _`Access Token`_ with _`Auth Server`_. [eg: Facebook,Google,Git etc]

_This service will act as `Resource Server` i.e. will require using valid Access Token if any Client wants to access any of it's APIs_

##### <ins>Spring Boot setup for Resource Server</ins>
 1. Adding below dependency will convert the service into a _`Resource Server`_ which will make Spring Security to validate any request to it using _`Access Token`_.
 
               <dependency>
        		<groupId>org.springframework.boot</groupId>
        		<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
               </dependency> 

 2. This _`Resource Server`_ will be accessed from POSTMAN using a _`Client ID/Secret`_ registered with _`Keycloak`_ to imitate a <ins>Service to Service communication following OAuth2 specifications</ins>.
 3. No username/password authentication or related implementation will be required as we're authenticating a Service(who made request to _`Resource Server`_) based on _`Client ID/Secret`_.
 4. All apis defined in this service has been mapped to a Role in _`ApiSecurityConfig`_. The Client need to have required Roles to access the corresponding api. Roles are registered in Keycloak.
 5. _`KeycloakRoleConverter`_ will parse the _`Access Token`_ and fetch _`Roles`_ and convert them to object of _`GrantedAuthority`_ allowed and understood by Spring Security.
 6. _`KeycloakRoleConverter`_ object is injected in _`ApiSecurityConfig`_ bean configuration.
 7. _`Resource Server`_ and _`Auth Server`_ connectivity is configured in _`application.properties`_. At service startup _`Resource Server`_ will download the _`public certs/keys`_ from _`Auth Server`_ which will be used to decrypt and validate the _`Access Tokens`_ encrypted by the _`Private key`_ of _`Auth Server`_.
 8. Start the service.
 
 ##### <ins>POSTMAN setup to get Access Token and request Resource Server apis</ins>[Grant Type - Client Credentials]
  1. Import POSTMAN Collection - _`OAuth_Rest_Client.postman_collection`_ and update required details as per Keycloak configurations.
  
  OR
  - Authorization >> Grant Type - Client Credentials
                   Access Token Url - http://localhost:8180/realms/dev/protocol/openid-connect/token
                   Client ID - <Client ID>
                   Client Secret - <>
                   Scope - openid >> Get New Access Token >> proceed >> Use Token
  - Validate header Authorization = Bearer <Access Token>
  - Set Request - GET http://localhost:8080/plant - validate outcomes based on Role configured in _`ApiSecurityConfig`_.
  
  
  ##### <ins>POSTMAN setup to get Authorization code, Access Token for a User registered with Keycloak</ins>[Grant Type - Authorization Code]
   1. Import POSTMAN Collection - _`OAuth_User_Client.postman_collection`_.
   
   OR
  ##### <ins>POSTMAN setup to get Authorization code for a User registered with Keycloak</ins>
 
  1. Execute following URI from browser. Configure client id/secret/redirect URI as required.
      http://localhost:8180/realms/dev/protocol/openid-connect/auth?client_id=clientuser&response_type=code&scope=openid&redirect_uri=http://localhost:7080/sample 
    
  2. Provide username/password on screen.
  3. Copy highlighted part from response to above request . This is _`Authorization code`_
   http://localhost:7080/sample?session_state=63015d07-e82a-4036-9e5f-b04b0f51f19c&iss=http%3A%2F%2Flocalhost%3A8180%2Frealms%2Fdev&_`code=e3ee13c7-2059-4a29-aeb0-7cd7534f374e.63015d07-e82a-4036-9e5f-b04b0f51f19c.a629804e-3255-47e5-8f23-2d3032345507`_.
   
   ##### <ins>POSTMAN setup to get Access Token based on Authorization code from above</ins>
   
   1. POST http://localhost:8180/realms/dev/protocol/openid-connect/token
   2. Body - x-www-form-urlencoded
      client_id = 
      client_secret = 
      grant_type = authorization_code
      code = <above highlighted code>
      redirect_uri =
      scope = openid
   3. Copy the Access Token from response
   
   ##### <ins>POSTMAN setup to call Rest apis in Resource Server</ins>
   1. set header Authorization = Bearer <Access Token>
      