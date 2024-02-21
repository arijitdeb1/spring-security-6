# Login/Logout using Spring Security and Thymeleaf

## Project Setup and Execution
1. Include following dependencies while generating the project in [start.spring.io](https://start.spring.io) -
            
            Spring Security            
            H2 database
            Spring Web
            Thymeleaf

2. Provide H2 database and connection details in `application.properties`.
3. We'll use Employee table and corresponding 'role' column for Employee/User authN and authZ. Register some user using POSTMAN collection - `registerUser.postman_collection`
4. Create `login.html` in `resources/templates` as per Thymeleaf specification.
5. Create `home.html` in `resources/templates` as per Thymeleaf specification.
6. Create `manage-employee` in `resources/templates` as per Thymeleaf specification.
7. Configure above pages as REST endpoints in `EmployeeController`.
8. Configure authN/authZ for above endpoints in `SpringSecurityConfiguration` class.
9. Start the application and access `localhost:<port>/login`.
