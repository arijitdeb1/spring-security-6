# Keycloak AuthZ Server Setup

1. [Keycloak page](https://www.keycloak.org/downloads) >> Download >> Server >> download the zip
2. Extract downloaded zip and copy to Program files 
3. [Keycloak page](https://www.keycloak.org/downloads) >> Getting Started >> Open JDK >> follow the instructions
4. Go to Keycloak folder in any terminal and execute below command 
             bin\kc.bat start-dev --http-port 8180
5. Access Keycloak at localhost:8180
6. Create a admin user [admin/admin] 
7. Go to Administration Console >> Login 
8. Realm - a namespace in AuthZ server where you can create your users,roles etc. default realm - `master`
9. Create Realm - `dev`
10. To view all configurations, URLs for a realm Go To - Realm Settings >> OpenID Endpoint Configuration OR (http://localhost:8180/realms/dev/.well-known/openid-configuration)
11. `**[Grant Type - Client Credentials]**` - **Setup Auth Server for API to API or Service to Service communication** - 
       1. One service(ex: POSTMAN) will need a Client ID/Secret to communicate to another service(ex: [Resource Server](https://github.com/arijitdeb1/spring-security-6/tree/main/security-oauth2-ResourceServer)) following OAuth2 specification.
       2. Client >> Create client [clientapi]
       3. Client Type - Open ID, Client ID - <any name> >> next
       4. Client Authentication - ON 
       5. Authentication Flow - Uncheck Standard Flow/Authorization Code Grant Type
                                Uncheck Direct Access Grants/Resource Owner Password Credentials Grant
                                Check Service Account Roles/Client Credentials Grant
                                
       ![ScreenShot](/images/client-credentials-keycloak.PNG?raw=true) 
                               
       6. Save and validate/copy the Client Secret under `Credentials` tab.            
       7. Create New Roles in Keycloak - Realm Roles >> Create Role.
       8. Assign the above Roles to Client - Clients >> <Client ID> >> Service Account Roles >> Assign Role >> Select >> Adds.
       9. Configure and start [Resource Server](https://github.com/arijitdeb1/spring-security-6/tree/main/security-oauth2-ResourceServer) application.
       
 12.  `**[Grant Type - Authorization Code]**` - **Setup Auth Server for User(login) to API or User(login) to Service communication** -  
       1. _`Keycloak`_ will authenticate the logged in User to generate _`Authorization Code`_ and then _`Access Token`_ in subsequent request to _`Auth Server`_.
       2. Client >> Create client [clientuser]
       3. Client Type - Open ID, Client ID - <any name> >> next
       4. Client Authentication - ON 
       5.  Authentication Flow - Select only Standard Flow/Authorization Code Grant Type >> Next
       ![ScreenShot](/images/authz-code-keycloak.PNG?raw=true) 
       6. Set redirect URIs - [_http://localhost:7080/sample is a dummy URI representing a UI application where user will land after successful authentication_]
       ![ScreenShot](/images/authz-code-redirect.PNG?raw=true) 
       7. Save and validate/copy the Client Secret under `Credentials` tab.
       8. Add some user who will trigger the authentication - Users >> Add User
       9. Add some Role to the above users - Users >> Role mapping >> Assign Role