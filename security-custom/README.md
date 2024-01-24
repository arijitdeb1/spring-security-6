# spring-security-custom
_managing customized authentication mechanism for different REST apis_

 1. By default spring security authenticates all requests. Refer _`defaultSecurityFilterChain`_ method in _`SpringBootWebSecurityConfiguration`_ class which is generating an object of _`SecurityFilterChain`_ with default configurations.
 2. Override the _`defaultSecurityFilterChain`_ method to generate a bean of _`SecurityFilterChain`_ having custom configurations. Refer _`ApiSecurityConfig.java`_ in `_configuration_` package.
 3. _/car_, _/bird_ apis are configured as authenticated and will need credentials.
 4. _/animal_, _/plant_ apis will not ask for authentication as configured.
 5. /insect is configured as deny and will ask for authentication. It'll deny the request after login.