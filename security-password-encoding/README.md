# spring-security-password-encoding
Encoding user defined password with Hashing algorithms provided by Spring security frameworks

#### _**<ins>Registering user with hashed password</ins>**_
 1. Define a bean in _`ApiSecurityConfig`_ that returns a _`BCryptPasswordEncoder`_ object.
 2. Encode the user defined password using the bean created above before persisting into database. Refer _`register`_ method in _`LoginController`_.
 3. Start the application.
 4. Register a user with _`registerUser.postman_collection`_ from POSTMAN.
 

 1. set a debugger at _`additionalAuthenticationChecks`_ method of _`DaoAuthenticationProvider`_ to understand the internals used by Spring Security to validate the password.
 