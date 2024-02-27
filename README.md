
# Spring Security 6

A detailed step-by-step hands-on approach to learn and understand various concepts of Authentication and Authorization using Spring Security 6 and related features. 




## Software Prerequisites

* Java 17
* SpringBoot 3.2.2

## Architecture

![ScreenShot](/images/main-arch.PNG?raw=true)

1. In Java based web applications Servlet Container(Web Sever - Apache Tomcat) takes care of translating the HTTP request to a ServletRequest before handing over to Servlet method(doGet,doPost) and also translating the ServletResponse to HTTP resposnse while responding back to client.

2. The request, before reaching the Servlet pass through a series of Filters to perform certain pre work or security checks based on the configurations in the web application.
_UsernamePasswordAuthenticationFilter_ will extract username/password from HTTP request and create an _UsernamePasswordAuthenticationToken_ object which is an implementation of Authentication interface and will be used to perform authentication.

3. _AuthenticationManager_ is an API that defines how Spring Security is to perform authentication.
_ProviderManager_ is the most commonly used implementation of AuthenticationManager.

4. _AuthenticationManager_ will delegate the task of validating the user to available _AuthenticationProvider_ that is relevent to the authentication object.
_DaoAuthenticationProvider_ will validate the username/password within _UsernamePasswordAuthenticationToken_

5. _AuthenticationProvider_ use _UserDetailsManager/UserDetailsService_ for retrieving, creating, updating, deleting the User Details from DB/Storage system.

6. _PasswordEncoder_ is used by _AuthenticationProvider_ to encode passwords while persisting in storage systems.

7. Once the request has been autheticated, the Authentication will usually be stored in a thread-local _SecurityContext_ managed by the _SecurityContextHolder_. This helps during the upcoming requests from the same server.

## Projects Overview

Below section details out all the projects that were implemented as part of this repository to demonstrate various aspects of AuthN and AuthZ using Spring Security. 

_* in-depth details of implementation are present in the ReadMe section of individual projects_

1. **`security-basic`** is a project that demonstrates a basic Spring Security implementation using default and custom user credentials. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-basic/README.md) within project.

2. **`security-custom`** is a project that demonstrates a Spring Security implementation with custom access specifications/restrictions for multiple REST endpoints in the application. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-custom/README.md) within project.

3. **`security-password-encoding`** has an api to register a new User and encode the provided password using Hashing algorithm configured as part of the project. Same Hashing algo will be used while validating the password during Login. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-password-encoding/README.md) within project.

4. **`security-configure-user`** showcases how to register multiple user credentials in application memory(not recommended for production usecase) and using same for authentication. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-configure-user/README.md) within project.

5. **`security-configure-DBuser`** is a project that provide api to register User and persist credentials within a H2 database. User retrieval has been demonstrated using both default as well as custom approaches.For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-configure-DBuser/README.md) within project.

6. **`security-custom-authentication-authorization`** implements custom authentication logic  overriding default authentication of Spring Security. It also implements user authorization based on user roles, authorities etc. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-custom-authentication-authorization/README.md) within project.

7. **`security-CORS`** demonstrates a CORS use case using Spring Security and custom REST Client. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-CORS/README.md) within project.

8. **`security-CSRF`** demonstrates a CSRF use case using Spring Security. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-CSRF/README.md) within project.

9. **`security-JWT`** demonstrates a Spring Security authentication flow using JWT Tokens. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-JWT/README.md) within project.

10. **`security-oauth2-GITHUB`** explains in brief the fundamentals of `OAuth 2.0` and `OpenID Connect` and also demonstrates a sample OAuth authentication using GITHUB as AuthZ server. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-oauth2-GITHUB/README.md) within project.

11. **`security-oauth2-Keycloak-setup`** has step by step approach to download and setup `Keycloak Authorization Server` on local Windows System and implement OAuth authentication for 2 of the Grant Types - Client Credentials and Authorization Code . For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-oauth2-Keycloak-setup/README.md) within project.

12. **`security-oauth2-ResourceServer`** is an implementation of OAuth Resource Server to test Client Credentials Grant Type scenarios by integrating with Keycloak AuthZ server configured in previous step. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-oauth2-ResourceServer/README.md) within project.

13. **`security-oauth2-GT-Authorization-Code`** provides a setup to test Authorization Code Grant Type scenarios by integrating with Keycloak AuthZ server configured in previous step. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-oauth2-GT-Authorization-Code/README.md) within project.

14. **`security-login-logout`** is a basic implementation of login and logout functionality using Spring Security and Thymeleaf.For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-login-logout/README.md) within project.

15. **`security-sso`** is a Single Sign On implementation containing two services/applications and using Spring Security and Keycloak AuthZ server. For more details refer [README.md](https://github.com/arijitdeb1/spring-security-6/blob/main/security-sso/README.md) within project.


## Useful Links

   * Okta Playground to test OAuth Grant Types - https://www.oauth.com/playground/
   * Keycloak Download - https://www.keycloak.org/downloads
   * Spring Boot Initiaizer - https://start.spring.io/