# spring-security-6
managing security for web applications using spring-security-6, OAuth2, OIDC etc.

Keycloak Auth Server setup

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
10. Setup Auth Server for API to API or Service to Service communication - 
       1. One service(eg Postman) will need a Client ID/Secret to communicate to another service following OAuth2 specification.
       2. Client >> Create client
       3. Client Type - Open ID, Client ID - <any name> >> next
       4. Client Authentication - ON 
       5. Authentication Flow - Dselect Standard Flow/Authorization Code Grant Type
                                Dselect Direct Access Grants/Resource Owner Password Credentials Grant
                                Select Service Account Roles/Client Credentials Grant
       6. Save
       