# OpenID Connect/OAuth2.0 authentication and authorization

## OAuth/OIDC Fundamentals
- **Resource Owner** - who requests _`OAuth Client`_ to authn/authz with _`Auth Server`_ and on successful validation, fetch the required details from _`Resource Server`_ [eg: any user]
- **OAuth Client** - who communicates with _`Auth Server`_ with required _`client id/secret`_ to authorize the _`Resource Owner`_ and get _`Access Tokens`_ to fetch required details from _`Resource Server`_ based on allowed _`scopes`_. [eg: any website that allows user to login using their Facebook,Google,Git credentials]
- **Authorization Server** - who authorizes the *`Resource Owne`*r and *`OAuth Clien`*t and generates _`Access Tokens/Refresh Tokens`_. [eg: Facebook,Google,Git etc]
- **OAuth Client** has to register with **Authorization Server** to get `client_id` and `client_secret`.
- **Resource Server** - who provides `Resource Owner`'s details to *`OAuth Client`*s after successful authorization of _`Access Token`_ with _`Auth Server`_. [eg: Facebook,Google,Git etc]
- **Grant Types** - select a GT based on the type of OAuth requirement. example:  Authorization Code and Client Credentials.
- **AUTHORIZATION CODE** - is a temporary code assigned to client after client authZ.
- **ACCESS TOKEN** - is a code assigned to client to access Resource Server on behalf of user.
- **REFRESH TOKEN** - is a token provided by AuthZ server along with `ACCESS TOKEN` to regenerate the `ACCESS TOKEN` once expired.
- **OpenID Connect** -
     * `OpenID Connect` is a protocol that sits on top of OAuth2.0 framework. While `OAuth2.0` provides an authorization via an `ACCESS TOKEN` containing scopes, `OpenID Connect` provides authentication by introducing a new `ID Token` which contains a new set of information and claims specifically for identity.
     * With the `ID Token`, OpenID Connect bring standards around sharing identity details among the applications.
     * The `OpenID Connect` flow looks same as OAuth. The only differences are in the initial request, a specific scope of openid is used and in the final exchange the client receives both an `ACCESS TOKEN` and `ID TOKEN`. 
     * Implementing OpenID Connect on top of OAuth 2.0 completes an IAM(Identity & Access Management) strategy.
     * OIDC exposes the standardized `/userinfo` endpoint which can accessed using the `ACCESS TOKEN` after successful authentication.

  
  **<ins>Grant Type - Authorization Code</ins>**  
  - In step #3, client has to provide below details to AuthZ server.
    * _`client_id`_ - id assigned to client after registration with AuthZ server.
    * _`redirect_uri`_ - URI to which AuthZ server needs to redirect after successful authentication.
    * _`scope`_ - level of access allowed to client.
    * _`state`_ - CSRF Token value to protect from CSRF attacks
    * _`response_type`_ - value is `code` which indicates that `AUTHORIZATION CODE` is being used.
  - In Step #5, after receiving a _`AUTHORIZATION CODE`_ from AuthZ server, client will request for a _`ACCESS TOKEN`_ from AuthZ server with below details.
    * _`code`_ - the `AUTHORIZATION CODE` from above.
    * _`client_id`_ and _`client_secret`_ - the client credentials which are registered with the AuthZ server.
    * _`grant_type`_ - value as `authorization_code` to indicate the type of GT being used.
    * _`redirect_uri`_ - URI to which AuthZ server needs to redirect after successful authentication.
  
    
  ![ScreenShot](/images/authz-code-GT.PNG?raw=true)

  [Click here to observe a demo in OAuth 2.0 playground](https://www.oauth.com/playground/)
  
  **<ins>Grant Type - Client Credentials</ins>** (_no user involved, communication between 2 secured micro services_ )
  - In step #1, below details we passed when making a request to AuthZ server.
    * _`client_id`_ and _`client_secret`_ - the credentials of client to authenticate itself.
    * _`scope`_ - level of access allowed to client.
    * _`grant_type`_ - value as `client_credentials` to indicate the type of GT being used.
    
  ![ScreenShot](/images/client-credentilas-GT.PNG?raw=true)
  
  **<ins>Token Validation by Resource Server</ins>**
  - _`ACCESS TOKEN`_ provided by AuthZ server need to be validated by the Resource server on every interaction.
  - Below approach of validating the Token using Public cert is considered as recommended approach as it avoids communicating with AuthZ server for validating tokens.
  
  ![ScreenShot](/images/token-validation.PNG?raw=true)
  
## Configuring OAuth Client with GITHUB as AuthZ Server
 1. Go to GITHUB profile >> Settings >> Developer settings >> OAuth Apps >> New OAuth App. [_To work with current project use http://localhost:8080 as Homepage/Callback URL_]
 2. Provide required details and create a new OAuth Client.
 3. Copy the _`Client ID`_ and `Client Secret`
 
## Setup an OAuth Client in Spring Boot with Spring Security

 1. Any request to localhost:8080 will be intercepted by the GitHub login page.
 2. Required configurations to enable an OAuth2.0 login in place of usual login has been done in _`SpringSecurityOAuth2GithubConfig`_ class.  
 3. Required Github client credentials are defined in _`SpringSecurityOAuth2GithubConfig`_ class. Similar configurations can be replaced with properties defined in _`application.properties`_.
 4. Upon successful authorization flow will be redirected to Callback URL as configured in GITHUB.
 5. Api in _`OAuthClientController`_ has the required `GET` api for Callback URL which will also receive the _`OAuth2AuthenticationToken`_ during redirect and display the contents of `home.html`.
  

