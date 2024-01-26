# spring-security-6
#### _**<ins>Managing customized Authentication and Authorization</ins>**_

#### _**<ins>Custom Authentication</ins>**_

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