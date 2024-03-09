# Single Sign On(SSO) with Keycloak and Spring Security
This project demonstrates an implementation for SSO capability using Keycloak and Spring Security.
Below are the features of this implementation - 

1. project contains two Spring Boot Security embedded applications `coach-app` and `player-app`.
2. `coach-app` application can only accessed by users with role `COACH`. 
3. `player-app` application can only accessed by users with roles `PLAYER` or `COACH`.
4. users with `COACH` privilege once logged in to any of above applications can access the other using SSO capability without any additional login.
5. users with `PLAYER` privilege can only access `player-app`.
6. `/manage-player` endpoint in `player-app` can only be accessed by user having `COACH` privilege.
7. users credentials and roles will be configured and maintained by the `Keycloak` AuthZ server.
8. `coach-app` and `player-app` will register themselves as client to `Keycloak` AuthZ server.
9. OAuth/OIDC validation for user will be done using `Authorization Code` grant type.
10. logout capability will sign out the user from all the applications and invalidate the user session.

# Kaycloak setup for SSO
1. Create a `Realm`(example: SSO)
2. Select the above Realm and register two clients as below for the two applications.
   ![ScreenShot](/images/coach-app-client-gs.PNG?raw=true)
   ![ScreenShot](/images/palyer-app-client-gs.PNG?raw=true)

   Configure redirect URL where user will land after valid AuthN/AuthZ for both the applications. Replace below port as configured for each of the application.
   ![ScreenShot](/images/redirect-sso.PNG?raw=true)
   
   Set `Client Authentication` as Off as we'll validating the user with userid/password instead of client with clientId/clientSecret
   Check `Standard flow` which enables support for `Authorization Code` flow for the client. Refer [here](https://github.com/arijitdeb1/spring-security-6/blob/main/security-oauth2-GITHUB/README.md) for more details around various `Grant Types`.
   Leave all other configuration as is - 
   
   ![ScreenShot](/images/Auth-code-sso.PNG?raw=true)
   
   Go to `Client Scopes` and set `microprofile-jwt` as `Default` which so that KeyCloak passes all the roles for the authenticating user to the token.
   ![ScreenShot](/images/microprofile-jwt.PNG?raw=true)
   
   Go to `Realm roles` and create two roles - `PLAYER` and `COACH`
   Go to `Users` - set `Username` and save. Go to `Credentials` and `Set Password`.
   Select the above User and Go to `Role mapping` - Click `Assign role` and select preferred role(PLAYER/COACH) selected above.
   ![ScreenShot](/images/realm-roles.PNG?raw=true)
   
   ![ScreenShot](/images/users-sso.PNG?raw=true)
   user 'poch' has been assigned role COACH while rest all users are PLAYER.
   
# General setup for the applications
1. Include below dependencies to `pom.xml`
          
          spring-boot-starter-security 
          spring-boot-starter-thymeleaf
          spring-boot-starter-web
          thymeleaf-extras-springsecurity6
          spring-boot-starter-oauth2-client
          spring-boot-starter-oauth2-resource-server
          
2. Add below configurations in `application.properties`.
_client registration configuration_:
         
          spring.security.oauth2.client.registration.keycloak.client-id=<client-id>
          spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
          spring.security.oauth2.client.registration.keycloak.scope=openid

_OIDC provider configuration_:
          
          spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8180/realms/<realm-name>
          spring.security.oauth2.client.provider.keycloak.user-name-attribute=preferred_username
          
   we can define user-name-attribute as `preferred_username` so as to populate our controller’s Principal with a proper user.
   
_configuration needed for validating JWT token against Keycloak server_:

          spring.security.oauth2.resourceserver.jwt.jwk-set-uri = http://localhost:8180/realms/sso/protocol/openid-connect/certs
          
   applications will download/archive the public cert during first interaction with Keycloak AuthZ server and subsequent `ACCESS-TOKEN` validations will be done using the public key.   

3. Configure the endpoint accessibility criteria and logout handler in the `KeycloakSecurityConfig` and `KeycloakLogoutHandler` class. Refer specific applications for corresponding configurations. 
4. Endpoint details are available in the corresponding controller class.  

# Execution steps to validate SSO capabilities.
1. Start `player-app` on port 8080 and `coach-app` on port 8081.
2. Users with `COACH` privilege are authorized to access both `coach-app` and `player-app`.
3. Users with `PLAYER` privilege can only access `player-app`. 
4. Open `Chrome Incognito` mode and allow an user with `COACH` privilege to access the `coach-app` at `https:/localhost:8081`.
5. User will land on the Welcome page and click `Sign In to Coach Portal`.
6. `Keycloak` login page will appear on screen, provide the credentials as configured on `Keycloak`.
7. On successful authentication user will be redirected to the home page for `coach-app`.
8. Validate the user session in `Keycloak` console  - Select the user under `Users` and go to `Sessions` tab.
9. Open another tab in the same browser and access `player-app` at `https:/localhost:8080`.
10. Click on `Sign In to Player Portal` - User will not be prompted with a login screen as user is already authenticated and having an active session on `Keycloak` AuthZ server.
11. Click `LogOut` and user will be gracefully logged out from both the application and session will become inactive and removed.
12. User with `PLAYER` privilege can login to `player-app` at port 8080 but cannot access the `Manage Player` link.
13. Only a `COACH` user can login to `player-app` and access `Manage Player` link.
13. Any attempt to access `coach-app` by any user with `PLAYER` privilege will be resulted in `Access Denied`.

# Internal Spring Security Flow to Authenticate and Authorize the users by the applications.
![ScreenShot](/images/sso-login.PNG?raw=true)

After successful Login, if user tries to access other applications registered with `Keycloak` under same `Realm`.
1. `Keycloak` AuthZ server is generate an `Authorization Code` after validating the user session.
2. `Authorization Code` will validated and exchanged by application(example: `player-app`) to generate an `Access Token`.
3. AuthN/AuthZ process flow remains same as shown in above sequence diagram.



   

  
 
   
