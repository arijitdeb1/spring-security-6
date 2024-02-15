
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