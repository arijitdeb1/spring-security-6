# Grant Type = Authorization Code
![ScreenShot](/images/authz-code-GT.PNG?raw=true)

Follow below setups which provides a sample hands on to understand Authorization Code flow.
  
  ## POSTMAN collection to get Authorization code, Access Token for a User registered with Keycloak
  **  **_Keycloak setup `#11` mentioned in above link should be in place _**
   1. Import POSTMAN Collection - _`OAuth_User_Client.postman_collection`_.
   
   **OR**
  ## POSTMAN setup to get Authorization code for a User registered with Keycloak
 
  1. Execute following URI from **browser**. Configure client_id/secret/redirect URI as required. As the below URI will land onto Keycloak Login page, hence can't be invoked from POSTMAN. 
      http://localhost:8180/realms/dev/protocol/openid-connect/auth?client_id=clientuser&response_type=code&scope=openid&redirect_uri=http://localhost:7080/sample 
    
  2. Provide username/password on screen for users configured in Keycloak and submit.
  3. Copy below highlighted part from updated URI in browser. This is _`Authorization code`_
   http://localhost:7080/sample?session_state=63015d07-e82a-4036-9e5f-b04b0f51f19c&iss=http%3A%2F%2Flocalhost%3A8180%2Frealms%2Fdev&_`code=e3ee13c7-2059-4a29-aeb0-7cd7534f374e.63015d07-e82a-4036-9e5f-b04b0f51f19c.a629804e-3255-47e5-8f23-2d3032345507`_.
   
  ## POSTMAN setup to get Access Token based on Authorization code from above
   
   4. POST http://localhost:8180/realms/dev/protocol/openid-connect/token
   5. Body - **x-www-form-urlencoded**
   
      client_id = 
      
      client_secret = 
      
      grant_type = authorization_code
      
      code = <above highlighted code> 
      
      redirect_uri =
      
      scope = openid
      
   6. Copy the Access Token from response
   7. If the request fails with "invalid_grant" error signifies that the Authorization Code from `#3` has expired and need to re-initiate the URI in `#1`. The expiry for Authorization Code is very limited and hence need to quickly copied and configured at `#5`.
   
  ## POSTMAN setup to call REST apis in [Resource Server](https://github.com/arijitdeb1/spring-security-6/tree/main/security-oauth2-ResourceServer)
  _with above setup in place you can even access the REST APIs in [Resource Server](https://github.com/arijitdeb1/spring-security-6/tree/main/security-oauth2-ResourceServer)_
   
   8. set header Authorization = Bearer <Access Token>.
   
   9. Validate outcomes based on Role configured in _`ApiSecurityConfig`_ and Keycloak.
      