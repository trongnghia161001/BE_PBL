# BE_PBL

Online Bookstore Website.

This online bookstore project adopts a microservices architecture, utilizing Spring Boot, Axon Framework, Apache Kafka, Saga Pattern, MySQL, API Gateway, CQRS Pattern, Event Sourcing, integration of VNPAY for payment, and deployment using Docker.

Project Deployment Model

![Screenshot (614)](https://github.com/trongnghia161001/BE_PBL/assets/75027006/c416cf79-944d-40b3-a41b-442c5bbd5e47)

To apply the Domain-Driven Design (DDD) Layered Architecture using the Axon Framework

![Screenshot (629)](https://github.com/trongnghia161001/BE_PBL/assets/75027006/2b783398-a3e1-4f80-b203-8d22de076bdd)

Purpose:
Driven by practical needs, there is a demand for a system to assist in managing the online book purchasing process. Additionally, with the current trend of technological advancements, there is a need for a system that aids humans in quickly and conveniently fulfilling their requirements. Through the field of information system analysis and design, the main purpose of the current project is to "Build an online bookstore website" to facilitate a simplified and efficient book purchasing process. Furthermore, the software also assists the admin in easily accessing revenue statistics and similar analyses.

Scope:
The software is intended for use on a specific book-selling website and can accommodate multiple simultaneous users.

Actors:
The system consists of two actors: users and admin.
To have a clearer understanding of the actors and the functional requirements of the system, they will be modeled using use case diagrams, which will be presented later.

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/29edb10c-05ec-4c51-a4d8-80c8520d7565)

Here are some sequence diagrams illustrating various functionalities in the project

Sequence diagram for the login functionality.

![Screenshot 2023-06-11 105316](https://github.com/trongnghia161001/BE_PBL/assets/75027006/96f0519d-066c-49bc-82b3-db25eb9b47af)

Sequence diagram for the "Forgot Password" functionality.

![Screenshot 2023-06-11 105415](https://github.com/trongnghia161001/BE_PBL/assets/75027006/4a66e66e-8760-4689-8247-5bb06a381295)

Sequence diagram for the "Manage User" functionality - Add.

![Screenshot 2023-06-11 105350](https://github.com/trongnghia161001/BE_PBL/assets/75027006/bf1ff3b1-15f3-4f64-bd2b-31e5b0d9b12a)

Sequence diagram for the "Manage User" functionality - Edit.

![Screenshot 2023-06-11 105704](https://github.com/trongnghia161001/BE_PBL/assets/75027006/2c0beacf-dd78-49ff-8e8a-b0ec710520b9)

Sequence diagram for the "Manage User" functionality - Delete.

![Screenshot 2023-06-11 105504](https://github.com/trongnghia161001/BE_PBL/assets/75027006/8ea3c623-0855-43d0-87c1-b1d1e3ddfcb8)

Sequence diagram for the "ShoppingCart Management" functionality.

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/8a6f4ddb-a947-495a-bd66-5c3cd7f6ba7f)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/4f8ed01e-5439-4074-ac8a-688db5989893)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/e52bb276-bd07-4d3d-a3c4-696b45d5f876)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/7c1895e1-8e9c-4b64-97d7-a91fa0a1c007)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/63edb674-2749-40b0-8e68-2aec2272e40b)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/a543a69b-c962-4bfd-9038-6d29410155d4)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/d9174dc7-934b-4d77-8141-ec6a3268d162)

Sequence diagram for the "Order Management" functionality - Create.

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/8a21b4b2-3376-40fe-8845-f23e89300ca0)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/3be5d657-14d1-4f0a-a979-51e6a36b8f4c)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/9f73ee0c-b762-4dda-8ba5-89024be90b49)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/6b3c7213-7c2f-4eb1-961a-426143f69136)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/20a54de4-4f6b-4c9e-9500-b91e5e990f9c)

Sequence diagram for the "Payment" functionality.

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/8e46f6b2-2549-46bd-a295-a2107bcffba2)

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/06c5d36a-7b12-4645-ae69-8b9d655e326d)

DATABASE

![Screenshot 2023-06-28 225703](https://github.com/trongnghia161001/BE_PBL/assets/75027006/f74c2dfc-d780-4047-a53e-704479732c05)

To build a web:

Download and install Java 17.
Download and install IntelliJ.
Download MySQL 8.0.29.
Download Axonserver 
Download Apache kafka

Step 1: Before running the program, make sure to install all the required libraries in the pom.xml file in all services.

Step 2: Initialize the database for each service

![Screenshot (849)](https://github.com/trongnghia161001/BE_PBL/assets/75027006/fc19f590-ffd8-4d26-870a-4c5a0e128e39)

Step 3: Go to the downloaded AxonServer, open a terminal and run the following command: java -jar axonserver.jar

![Screenshot (639)](https://github.com/trongnghia161001/BE_PBL/assets/75027006/3b65c09b-82a8-4736-bdd9-fafd1ae4fc20)

Step 4: Go to the downloaded kafka folder, open the terminal twice to execute the following 2 commands:
Command 1: zookeeper-server-start.bat C:\kafka_2.13-3.2.0\config\zookeeper.properties
Command 2: kafka-server-start.bat C:\kafka_2.13-3.2.0\config\server.properties

![Screenshot (664)](https://github.com/trongnghia161001/BE_PBL/assets/75027006/c0284391-2450-4ce6-bc86-c8831fbb74d8)

And finally start each service and enter the postman software to experience the project's functionality.

In addition, I also use docker to build the project:
Step 1: Create jar file for each service

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/63f6dfd1-093c-4cba-b893-17fb96adbbf4)

Step 2: Write Dockerfile with the following template

![image](https://github.com/trongnghia161001/BE_PBL/assets/75027006/84764025-0445-40f3-b6e9-45be47f4eb0a)

Step 3: Step 3: Write docker-compose.yml with the following template

![Screenshot 2023-06-28 233132](https://github.com/trongnghia161001/BE_PBL/assets/75027006/3ff956f7-aea8-433b-99c0-1040dd175029)

Note:
You must download docker to your computer and have a docker hub account







