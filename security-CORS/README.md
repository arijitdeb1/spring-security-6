# spring-security-6
#### _<ins>Basic understanding of CORS[Cross Origin Resource Sharing] and related issues</ins>_

 
1. **CORS** is a protocol that enables scripts running on a browser client to interact with resources from a different origin. For example, if a UI app wishes to make ab API call running on a different domain, it would be blocked from doing so by default due to CORS. It is a specification fron W3C implemented by most browsers.

   So CORS is not a security isse/attack but the default protection provided by browsers to stop sharing the data/communication between different origins.

2. "other origins" means URL being accessed from a location different from where the Javascript is running i.e a different scheme, different domain, different port.

3. An Angular application trying to communicate with a SpringBoot application will be blocked by default to CORS.

4. Two ways to unblock CORS and allow communication between an Angular application and SpringBoot applcation - 

 -  Define _`@CrossOrigin`_ annotation as below on top of class already defined as _`@RestController`_


	     @CrossOrigin(origins = "http://localhost:4200")
         //will allow communication from specified application on port 4200

        
          OR
   
          @CrossOrigin(origins = "*")
          //will allow communication from any domain
          
          
 
 
 
 -  Instead of mentioning *`@CrossOrigin`* annotation on all the controller inside our web app, we can define CORS related configurations globally using Spring Security as below -
 
        @Bean
            public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                http.csrf().disable();
                http.headers().frameOptions().disable();
                return http
                        .cors().configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();
                                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));//communication from which specific is allowed
                                configuration.setAllowedMethods(Collections.singletonList("*"));//which HTTP method is allowed
                                configuration.setAllowCredentials(true);//ok to receive credentials for processing
                                configuration.setAllowedHeaders(Collections.singletonList("*"));//allow all headers from another origin
                                configuration.setMaxAge(3600L);//browser will remember this configuration for 1 hour
                                return configuration;
                            }
                        }).and()
                        .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/car", "/bird").authenticated()
                        .requestMatchers("/animal", "/plant","/register").permitAll()
                        .requestMatchers("/insect").denyAll())
                        .formLogin(withDefaults())
                        .httpBasic(withDefaults())
                        .build();
            }  
          
     
 5. If _`CORS`_ enabled, the client will make _`preflight`_ request first to validate CORS and before making the original request.  
 
 
 #### _<ins>Testing CORS integration with a REST client</ins>_ 
 
  1. Pull the _`RestClient`_ application present in the same Git repository.
  2. Change the username/password in portal.js
  
        
        "Authorization": "Basic " + btoa("<USRNAME>" + ":" + "<PASSWORD>")
        
  3. Start the RestClient application at localhost:8090 and click the button.
  4. Comment/Uncomment the CORS integration in the _`SecurityFilterChain`_ configuration in _`ApiSecuritConfig`_ of _`security-CORS`_ application to test CORS in action .
        