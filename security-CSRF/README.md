# spring-security-6
#### _<ins>Basic understanding of CSRF[Cross Site Request Forgery], related issues and mitigation strategies</ins>_

1. _`CSRF(Cross Site Request Forgery)`_ is a security attack/vulnerability to steal data from web application by execution some privileged action.

2. Spring Security provide CSRF protection by blocking POST/PUT request to web application.
   comment out _`http.csrf().disable()`_ in _`SecurityFilterChain`_ bean and try to register a user using the _`POST`_ api in _`LoginController`_. Will get _`401`_ unauthorized.

3. Recommended to handle CSRF in a proper manner without disabling it.    

4. To test how Spring Security is blocking PUT/POST when CSRF is enabled, refer below changes in _`security-password-encoding`_ application - 

   1. Ignore CSRF check for some public accessible apis while defining bean for _`SecurityFilterChain`_ -
      _`http.csrf().ignoringRequestMatchers("/register","/h2-console/**")`_; 
   2. Introduce another POST api _`/testCSRF`_ in _`LoginController`_ which registered with PermitAll apis but CSRF is not diabled or ignored.
   3. Start application and hit _`/register`_ and _`/testCSRF`_ to evaluate the difference in response due to CSRF block.