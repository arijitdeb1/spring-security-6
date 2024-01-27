# spring-security-6

##### **<ins>Keycloak Auth Server setup</ins>**

1. Keycloak page >> Download >> Server >> downlaod the zip
2. Extract downloaded zip and copy to Program files 
3. Kecloak page >> Getting Started >> Open JDK >> follow the instruction
4. Go to Keycloak folder in any terminal and execute below command 
             bin\kc.bat start-dev --http-port 8180
5. Access Keycloak at localhost:8180
6. Create a admin user [admin/admin] 
7. Go to Administration Console >> Login 
8. Realm - a namespace in Authserver where you can create your users,roles etc. default relam - master
9. Create Realm - dev
10. To view all configurations, URLs for a realm Go To - Realm Settings >> OpenID Endpoint Configuration OR http://localhost:8180/realms/dev/.well-known/openid-configuration
11. _`Setup Auth Server for API to API or Service to Service communication`_ - [Grant Type - Client Credentials]
       1. One service(eg Postman) will need a Client ID/Secret to communicate to another service following OAuth2 specification.
       2. Client >> Create client [clientapi]
       3. Client Type - Open ID, Client ID - <any name> >> next
       4. Client Authentication - ON 
       5. Authentication Flow - Dselect Standard Flow/Authorization Code Grant Type
                                Dselect Direct Access Grants/Resource Owner Password Credentials Grant
                                Select Service Account Roles/Client Credentials Grant
       6. Save
       7. Realm Roles >> Create Role
       8. Clients >> <Client ID> >> Service Account Roles >> Assign Role >> Select >> Adds
       
 12.  _`Setup Auth Server for User(login) to API or User(login) to Service communication`_ - [Grant Type - Authorization Code] 
       1. _`Keycloak`_ will authenticate the logged in User to generate _`Authorization Code`_ and then _`Access Token`_ in subsequent request to _`Auth Server`_.
       2. Client >> Create client [clientuser]
       3. Client Type - Open ID, Client ID - <any name> >> next
       4. Client Authentication - ON 
       5.  Authentication Flow - Select only Standard Flow/Authorization Code Grant Type >> Next
       6. Valid redirect URIs - [http://localhost:7080/sample]
       7. Save
       8. Users >> Add User
       9. Users >> Role mapping >> Assign Role