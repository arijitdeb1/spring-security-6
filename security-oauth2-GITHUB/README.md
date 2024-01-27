# spring-security-6



- **Resource Owner** - who requests _`OAuth Client`_ to authn/authz with _`Auth Server`_ and on successful validation, fetch the required details from _`Resource Server`_ [eg: any user]
- **OAuth Client** - who communicates with _`Auth Server`_ with required _`client id/secret`_ to authorize the _`Resource Owner`_ and get _`Access Tokens`_ to fetch required details from _`Resource Server`_ based on allowed _`scopes`_. [eg: any website that allows user to login using their Facebook,Google,Git credentials]
- **Authorization Server** - who authorizes the *`Resource Owne`*r and *`OAuth Clien`*t and generates _`Access Tokens/Refresh Tokens`_. [eg: Facebook,Google,Git etc]
- **Resource Server** - who provides *`Resource Owne`*r's details to *`OAuth Client`*s after successful authorization of _`Access Token`_ with _`Auth Server`_. [eg: Facebook,Google,Git etc]

##### <ins>Setting OAuth Client with GITHUB</ins>
 1. Go to GITHUB profile >> Settings >> Developer settings >> OAuth Apps >> New OAuth App. [_To work with current project use http://localhost:8080 as Homepage/Callback URL_]
 2. Provide required details and create a new OAuth Client.
 3. Copy the _`Client ID`_ and `Client Secret`
 
#### _**<ins>Setup a OAuth Client in Spring Boot with Spring Security</ins>**_

 1. Any request to localhost:8080 will be intercepted by the GitHub login page.
 2. Required Github client credentials are defined in _`SpringSecurityOAuth2GithubConfig`_ class. Similar configurations can be replaced with properties defined in application.properties.
 3. Upon successful authorization flow will be redirected to home.html
  




Client ID
351ff678604fa59d2d70
Client secrets
39912bfa93bfdf2dfbb70769b1a9b290f48b9e83