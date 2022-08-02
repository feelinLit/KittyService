# KittyService


A service to keep track of cats and their owners.


Three microservices stand out from the application:

* Microservice of access to cats
* Microservice of access to owners
* Microservice with external interfaces.

There is also an optional plug-in module with all the JPA entities.

Communication between microservices is done with RabbitMQ.

The service provides a http interface (REST API) to retrieve information about specific cats and owners and to retrieve filtered information

There is an admin role. He has access to all the methods and can create new users. The user is bound to the owner at a 1:1 ratio.

The methods to retrieve information and cat and owner information are protected by Spring Security. Only cat owners and administrators have access to the respective endpoints. All authorised users have access to the methods for filtering, but the output is only about their cats.

<img src="https://user-images.githubusercontent.com/79377488/182382541-2d2c6b4d-125f-456d-b22d-34d1a5652a36.png" width="500">

