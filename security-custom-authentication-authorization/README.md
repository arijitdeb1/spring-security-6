# Custom AuthN and AuthZ

#### _**<ins>Custom Authentication using user defined Authentication Providers</ins>**_

The _`AuthenticationProvider`_ in Spring Security takes care of the authentication logic. The default implementation of the _`AuthenticationProvider`_ is to delegate the responsibility of finding the user in the system to a _`UserDetailsService`_ implementation and _`PasswordEncoder`_ for password validation. But if we have a custom authentication requirement that is not fulfilled by Spring Security frameworks, then we can build our won authentication logic by implementing the _`AuthenticationProvider`_ interface.

Like we can have a _`AuthenticationProvider`_ implementation for Username/Password kind of AuthN, another one for OAuth2 kind of AuthN and one for OTP variant of AuthN.

It is the responsibility of the _`ProviderManager`_ which is an implementation of _`AuthenticationManager`_ to check with all implementations of _`AuthenticationProvider`_ and try to AuthN the user.

 1. DaoAuthenticationProvider has default implementation for Spring Security Authentication and will be replaced as part of custom implementation.
 2. Create class _`EmployeeAuthenticationProvider`_ in _`configuration`_ package which will implement custom authentication.
 3. Implement _`supports`_ method to return _`UsernamePasswordAuthenticationToken`_ which means the implementation will support Username/Password kind of authentication. Other types of authentication are available in same _`package`_ as _`UsernamePasswordAuthenticationToken`_
 4. Implement _`authenticate`_ method to load user details, do necessary authentication and populate the outcome to a _`Authentication`_ object to return. 
 5. Start the application
 
 #### _**<ins>Custom Authorization</ins>**_

 1. Authorities/Roles information in Spring Security is stored inside _`GrantedAuthority`_. There is only _`getAuthority`_ method to return the name of Authority/Role.
 2. _`SimpleGrantedAuthority`_ is the default implementation class for _`GrantedAuthority`_ in Spring Security framework.
 3. We'll be using the _`role`_ in _`Employee`_ class to authorize the Users. Refer the _`SimpleFilterChain`_ bean configuration to view how the roles are mapped to the apis.
 4. Start the application and validate the apis with corresponding users
 
 
 # Authority Vs Role
 
    | Authority | Role     |
    
    | --- | --- | 
    
    | Authority is like an individual privilege or an action | Role is a group of priilege or action |
    
    | Restricting access in fine-grained manner | Restricting access in coarse-grained manner |
    
    | Ex: VIEWANIMAL, VIEWCAR etc. | Ex: ROLE_ADMIN, ROLE_USER (ROLE_prefix only to be used while configuring role in DBs. But when we refer to the rule we can do it by it's name/prefix) |
    
    | hasAuthorit(), hasAnyAuthority(), access() | hasRole(), hasAnyRole(), access() |
    
    
    | Arijit | Audvik |
    | -----  | -----  |
    | one    | two    |
    
    
    
     
     
     