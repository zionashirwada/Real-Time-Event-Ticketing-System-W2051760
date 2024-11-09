# `REAL-TIME-EVENT-TICKETING-SYSTEM-W2051760`


<p align="left">
		<em>Built with the tools and technologies:</em>
</p>
<p align="center">
	<img src="https://img.shields.io/badge/Chart.js-FF6384.svg?style=plastic&logo=chartdotjs&logoColor=white" alt="Chart.js">
	<img src="https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=plastic&logo=JavaScript&logoColor=black" alt="JavaScript">
	<img src="https://img.shields.io/badge/HTML5-E34F26.svg?style=plastic&logo=HTML5&logoColor=white" alt="HTML5">
	<img src="https://img.shields.io/badge/TypeScript-3178C6.svg?style=plastic&logo=TypeScript&logoColor=white" alt="TypeScript">
	<img src="https://img.shields.io/badge/JSON-000000.svg?style=plastic&logo=JSON&logoColor=white" alt="JSON">
	<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=plastic&logo=openjdk&logoColor=white" alt="java">
	<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff" alt="spring">
	<img src="https://img.shields.io/badge/Tailwind%20CSS-%2338B2AC.svg?logo=tailwind-css&logoColor=white" alt="tailwind">
</p>

<br>

<details><summary>Table of Contents</summary>

- [`REAL-TIME-EVENT-TICKETING-SYSTEM-W2051760`](#real-time-event-ticketing-system-w2051760)
  - [📍 Overview](#-overview)
  - [👾 Features](#-features)
  - [🧩 Modules](#-modules)
  - [🚀 Getting Started](#-getting-started)
    - [🔖 Prerequisites](#-prerequisites)
    - [📦 Installation](#-installation)
      - [Build the project from source:](#build-the-project-from-source)
      - [Backend Installation and Usage](#backend-installation-and-usage)
      - [Frontend Installation and Usage](#frontend-installation-and-usage)
    - [🤖 Usage](#-usage)
      - [Backend Usage](#backend-usage)
      - [Frontend Usage](#frontend-usage)
  - [📌 System Architecture](#-system-architecture)
    - [Backend Components](#backend-components)
    - [Frontend Components](#frontend-components)
  - [🧑‍💻 System Workflow](#-system-workflow)
  - [💻 OOP Concepts Utilized](#-oop-concepts-utilized)
  - [🤝 API Documentation](#-api-documentation)
      - [Configuration](#configuration)
      - [System State](#system-state)
      - [Customer Manager](#customer-manager)
      - [VIP Customer Manager](#vip-customer-manager)
      - [Vendor Manager](#vendor-manager)
      - [Ticket Pool](#ticket-pool)
  - [📂 Repository Structure](#-repository-structure)
  - [🎗 License](#-license)
  - [🙌 Acknowledgments](#-acknowledgments)

</details>
<hr>

## 📍 Overview

This Real-Time Event Ticketing System manages concurrent ticket releases and purchases by leveraging the Producer-Consumer pattern with a multithreaded architecture. The system is composed of a Spring Boot backend and an Angular frontend, connected via REST APIs and WebSocket for real-time communication.

---

## 👾 Features

- Concurrency Management: Simultaneous handling of ticket releases and purchases.
- Data Integrity: Thread-safe operations using synchronization for consistent ticket availability.
- Multi-threaded Environment: Employs producer-consumer threading to manage multiple vendors and customers.
- Real-Time Updates: Provides a UI with real-time status on ticket availability and transactions.
- Basic Logging: Tracks and records system activities for audit and troubleshooting.
- Websocket Usage: Using websockets for real-time updates.
- API Usage: Using API to connect frontend and backend.

---

## 🧩 Modules

<details closed><summary>backend</summary>

| File | Summary |
| --- | --- |
| [configuration.json](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/configuration.json) | This File Holds all the configeration details in JSON Format |


</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend</summary>

| File | Summary |
| --- | --- |
| [TicketingSystemBackendApplication.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/TicketingSystemBackendApplication.java) | The file contains the main class for the Spring Boot application  `TicketingSystemBackendApplication`, which serves as the entry point for initializing and running the backend service of the ticketing system.  |

</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend.producer</summary>

| File | Summary |
| --- | --- |
| [Vendor.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/producer/Vendor.java) |The `Vendor` class implements a thread that manages the release of tickets into a shared `TicketPool`, with functionality to pause, resume, and stop the process while logging updates and transactions. It uses services to send real-time updates and logs, ensuring concurrent ticket management in a multi-threaded environment. 
 |
| [VendorManager.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/producer/VendorManager.java) | The `VendorManager` class manages a collection of vendor threads for ticket distribution, allowing for initialization, starting, pausing, resuming, and stopping of vendors while updating the vendor count through a service. It ensures proper resource management by handling vendor lifecycle events and logging actions for monitoring. |

</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend.model</summary>

| File | Summary |
| --- | --- |
| [Ticket.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/Ticket.java) | The Java  class `Ticket` represents a ticket entity with an immutable identifier. It includes a constructor for initialization, a getter for accessing the ID, and a toString method for a readable representation.|
| [TicketUpdate.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/TicketUpdate.java) | The `TicketUpdate` class encapsulates information about ticket actions (add/remove) related to entities (vendor/customer/system), providing methods to manage and represent these updates in a ticketing system. It includes attributes for action type, entity, name, ticket count, and total tickets, along with constructors, getters, setters, and a `toString` method for easy access and representation. |
| [TransactionLog.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/TransactionLog.java) | The `TransactionLog` class in Java represents a log entry for ticket transactions involving vendors or customers, detailing the action, entity, name, ticket count, and total tickets. It includes constructors, getters, setters, and a `toString` method for easy data management and representation. |
| [Configuration.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/Configuration.java) | The `Configuration` class in Java manages settings for a ticketing system, including attributes for total tickets, release rate, retrieval rate, and maximum capacity, with constructors and getter/setter methods for encapsulation and deserialization support. |
| [SystemState.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/SystemState.java) | This class is to define the states of the system |
| [SystemStateManager.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/SystemStateManager.java) | The `SystemStateManager` class manages system states using synchronization and the `volatile` keyword to ensure thread safety, allowing threads to wait for specific state transitions and ensuring visibility of state changes across threads. It includes methods to set, get, and wait for the system state to become `RUNNING`. 
 |
| [CountUpdate.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/model/CountUpdate.java) | The `CountUpdate` class in Java encapsulates and manages vendor and customer counts with private fields and public getter and setter methods, promoting encapsulation. It initializes counts through a constructor and allows updates via setter methods. |

</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend.config</summary>

| File | Summary |
| --- | --- |
| [WebSocketConfig.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/config/WebSocketConfig.java) | This configuration class sets up WebSocket communication in a Spring Boot application by registering a STOMP endpoint and configuring a simple message broker for real-time message routing. It allows clients to connect to `/ws-ticketing` and uses `/topic` and `/app` as message routing prefixes. |
| [CorsConfig.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/config/CorsConfig.java) | This Spring Boot configuration class sets up CORS to allow requests from `http://localhost:4200` for specified HTTP methods (GET, POST, PUT, DELETE, OPTIONS). It uses the `WebMvcConfigurer` interface to apply these settings to all endpoints in the application.|

</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend.consumer</summary>

| File | Summary |
| --- | --- |
| [Customer.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/consumer/Customer.java) | The `Customer` class simulates a customer purchasing tickets in a multi-threaded environment, with mechanisms for pausing, resuming, and stopping, while integrating services for transaction updates and logging. It continuously attempts to purchase tickets from a shared `TicketPool` and handles success or failure accordingly. 
 |
| [CustomerManager.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/consumer/CustomerManager.java) | The `CustomerManager` class manages multiple `Customer` threads for purchasing tickets concurrently from a shared `TicketPool`, providing methods to start, pause, resume, add, and remove customers while ensuring thread safety and logging significant events. |

</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend.controller</summary>

| File | Summary |
| --- | --- |
| [CustomerController.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/controller/CustomerController.java) |The `CustomerController` is a [Spring Boot](https://spring.io/projects/spring-boot) REST controller that provides endpoints for managing customers, including operations to get the customer count, add, remove, pause, and resume customer actions. It uses dependency injection to utilize the `CustomerManager` service for business logic execution. 
 |
| [TicketPoolController.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/controller/TicketPoolController.java) | This Spring Boot REST controller manages vendor operations with endpoints for counting, adding, removing, pausing, and resuming vendors. It uses `VendorManager` for business logic and provides responses via HTTP requests. |
| [ConfigurationController.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/controller/ConfigurationController.java) | This Spring Boot REST controller handles vendor operations with endpoints for counting, adding, removing, pausing, and resuming vendors. It leverages `VendorManager` for executing business logic and responds to HTTP requests. |
| [SystemController.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/controller/SystemController.java) | The `SystemController` is a Spring Boot REST controller that manages system state through endpoints for starting, pausing, stopping, resetting, and checking status, ensuring operations are valid and providing appropriate feedback. It uses dependency injection for business logic and handles errors with meaningful HTTP responses. |
| [VendorController.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/controller/VendorController.java) | The `VendorController` class is a REST controller in a Spring application that manages vendor-related operations, including getting the vendor count, adding, removing, pausing, and resuming vendor actions. It uses the `VendorManager` service to perform these operations and provides endpoints for each action. |

</details>

<details closed><summary>backend.src.main.java.lk.W2051760.ticketing_system_backend.service</summary>

| File | Summary |
| --- | --- |
| [TicketUpdateService.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/service/TicketUpdateService.java) | The `ConfigurationService` class is a Spring service that manages application configuration data by saving and loading it from a JSON file using Jackson's `ObjectMapper`. It provides methods to serialize a `Configuration` object to JSON and deserialize JSON back into a `Configuration` object. |
| [SystemManagementService.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/service/SystemManagementService.java) | The `SystemManagementService` class in a Spring application manages the state and operations of a system involving vendors, customers, and a ticket pool, ensuring proper initialization, state transitions, and real-time status updates. It uses synchronized methods for thread safety and broadcasts system states via messaging templates. |
| [CountUpdateService.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/service/CountUpdateService.java) | The `CountUpdateService` is a Spring service that manages and broadcasts real-time updates of vendor and customer counts to WebSocket clients in a thread-safe manner. It uses `SimpMessagingTemplate` for messaging and logs updates for monitoring. |
| [TicketPool.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/service/TicketPool.java) | The `TicketPool` class manages a pool of tickets, ensuring the total does not exceed a specified maximum capacity, and provides thread-safe methods for adding and removing tickets while sending real-time updates via `TicketUpdateService`. It includes logging and error handling to maintain robustness and provide diagnostic information. |
| [TransactionLogService.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/service/TransactionLogService.java) | The `TransactionLogService` is a Spring service that broadcasts `TransactionLog` objects to WebSocket clients using `SimpMessagingTemplate`, allowing real-time updates. It also logs each broadcasted transaction for monitoring purposes. |
| [ConfigurationService.java](https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760/blob/main/backend/src/main/java/lk/W2051760/ticketing_system_backend/service/ConfigurationService.java) | The `ConfigurationService` class is a Spring service that handles saving and loading configuration data to and from a JSON file using Jackson's `ObjectMapper`. It provides methods for serializing and deserializing a `Configuration` object. |

</details>

---

## 🚀 Getting Started

### 🔖 Prerequisites

**Java Development Kit (JDK)** 	[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#) : `version Java 17 or higher` </br>
**Node.js** [![NodeJS](https://img.shields.io/badge/Node.js-6DA55F?logo=node.js&logoColor=white)](#): `Version: >= 16.x.x` </br>
**npm** 	[![npm](https://img.shields.io/badge/npm-CB3837?logo=npm&logoColor=fff)](#) : `Version: >= 8.x.x` </br>
**Angular CLI** [![Angular](https://img.shields.io/badge/Angular-%23DD0031.svg?logo=angular&logoColor=white)](#): `Version: 18.2.9` </br>
**Tailwind CSS** [![TailwindCSS](https://img.shields.io/badge/Tailwind%20CSS-%2338B2AC.svg?logo=tailwind-css&logoColor=white)](#): `Version: >= 3.x.x` </br>
**TypeScript** [![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=fff)](#): `Version: >= 4.x.x` </br>
**Maven**: `Version: 3.9.9` </br>
**Spring Boot**: `Version: 3.2.0` </br>
**WebSocket**: `Version: 5.3.0` </br>

### 📦 Installation

#### Build the project from source:

1. Clone the Real-Time-Event-Ticketing-System-W2051760 repository:
```sh
❯ git clone https://github.com/zionashirwada/Real-Time-Event-Ticketing-System-W2051760
```

2. Navigate to the project directory:
```sh
❯ cd Real-Time-Event-Ticketing-System-W2051760
```

#### Backend Installation and Usage

1. Navigate to the backend directory:
```sh
❯ cd backend
```
2. Install dependencies and build the project:
```sh
❯ ./mvnw clean install
```

#### Frontend Installation and Usage

1. Navigate to the frontend directory:
```sh
❯ cd frontend
```
2. Install dependencies:
```sh
❯ npm install
```



### 🤖 Usage

To run the project, execute the following command:

#### Backend Usage

1. Navigate to the backend directory:
```sh
❯ cd backend
```
2. Run the backend server:
```sh
❯ ./mvnw spring-boot:run
```

#### Frontend Usage

1. Navigate to the frontend directory:
```sh
❯ cd frontend
```

2. Run the frontend development server:

```sh
❯ ng serve
```
This will start the development server at `http://localhost:4200`.

---

## 📌 System Architecture

### Backend Components
1. System States
 - NOT_CONFIGURED: Initial state; awaiting configuration.
 - STOPPED: Configuration loaded; system idle.
 - RUNNING: Actively processing tickets.
 - PAUSED: Operations temporarily suspended.

2. Key Components
- TicketPool:
   - Shared resource holding tickets.
   - Manages ticket addition/removal with max capacity constraints.
   - Thread-safe, preventing race conditions in ticket operations.
- User (Abstract Class):
   - Base class for user entities, including common fields `id` and `name`.
   - Inherited by `Customer`, `Vendor`, and `VIPCustomer` classes.
- Vendors (Producer):
   - Separate threads releasing tickets at intervals.
   - Managed by `VendorManager` for lifecycle control.
   - Can be paused, resumed, or stopped.
- Customers (Consumer):
   - Threads attempting to purchase tickets.
   - Managed by `CustomerManager`.
- VIPCustomer (Customer Subclass):
   - Specialized consumer with unique ticket-purchasing behavior.
   - Manages `loyaltyPoints` and other VIP-specific properties.
### Frontend Components
1. SystemControlComponent:
	- Displays system status.
	- Provides controls to start, pause, stop, and reset the system.
	- Real-time updates via WebSocket.
2. TicketPoolStatusComponent:
	- Displays ticket pool statistics, including capacity and total tickets.
	- Monitors capacity, triggering auto-pause if limits are reached.
3. LineChartComponent:
	- Visualizes ticket operations over time.
	- Tracks cumulative tickets released/purchased, updating in real time.
---
## 🧑‍💻 System Workflow
1. Initialization
Upon starting the application, the backend loads configuration details and initializes system components as per `configuration.json`.

2. System Operations
- Start System:

 - Triggered by UI via `/api/system/start`.
 - Initiates Vendor and Customer threads to begin ticket processing.
- Ticket Release (Vendor):

 - Periodic release of tickets into `TicketPool`.
 - Validates against pool capacity.
- Ticket Purchase (Customer):

 - Customers attempt to purchase tickets from `TicketPool`.
 - System tracks successful and failed purchases with real-time updates.
3. Real-Time Updates
- WebSocket broadcasts for ticket and system status updates.
- Displays current pool size, ticket counts, and transaction logs.
---

## 💻 OOP Concepts Utilized

| Concept                              | Status     | Notes                                                                 |
|--------------------------------------|------------|-----------------------------------------------------------------------|
| **Class Declaration**                | ✅         | Implemented `User`, `Customer`, `Vendor`, and `VIPCustomer` classes  |
| **Object as Instance of Class**      | ✅         | Instances created, with endpoints for `VIPCustomer` management       |
| **Access Modifiers**                 | ✅         | Used `public`, `private`, `protected`                                |
| **Passing by Reference / Value**     | ✅         | Objects passed by reference, primitives by value                     |
| **Static Methods and Variables**     | ✅         | Used `static` variables like `logger`                                |
| **Static / Non-Static Context**      | ✅         | Used both contexts across different classes                          |
| **Instance, Class, Local Variables** | ✅         | `id`, `name` as instance variables; static variables for logging     |
| **Java Predefined Classes**          | ✅         | Used classes like `Thread`, `BlockingQueue`, `Logger`                |
| **String Class**                     | ✅         | Used for fields like `id` and `name` in `User`                       |
| **Packages**                         | ✅         | Organized classes into packages                                      |
| **Inheritance**                      | ✅         | `Customer` and `Vendor` inherit from `User`                          |
| **Creating Subclasses**              | ✅         | Created `VIPCustomer` as subclass of `Customer`                      |
| **Override Methods**       	       | ✅         |  Implemented `run()` method from `Runnable` in `Customer`            |
| **Substitution Principle**           | ✅         | Implemented with `User` parent type                                  |
| **Dynamic Binding**                  | ✅         | `run()` method dynamically bound at runtime                          |
| **Polymorphism**                     | ✅         | `User` type allows handling of `Customer`, `Vendor`, etc.            |
| **Object Class in Java**             | ✅         | Used `toString()` method override in `TicketUpdate`,`Ticket`         |
| **Final Classes and Methods**        | ✅         | Used `final` for `SystemStateManager` class.                         |
| **Abstract Classes**                 | ✅         | `User` is an abstract class                                          |
| **Interfaces**                       | ✅         | Implemented `Runnable` interface in `Customer`                       |
| **Collections and Data Structure**   | ✅         | Used `List`, `BlockingQueue`, etc.                                   |
| **List, Queue, Map**                 | ✅         | `List` and `BlockingQueue` used                                      |


---

## 🤝 API Documentation

The Real-Time Ticketing System API provides endpoints to configure the system, manage vendors and customers, monitor system status, and manage ticket pool capacity.
For additional details on using each endpoint, refer to the full API documentation on Postman.

---

#### Configuration

1. **Get Configuration**
   - **Endpoint**: `GET /api/configuration`
   - **Description**: Retrieves the current configuration settings.
   - **Response**:
     ```json
     {
       "totalSystemTickets": 5000,
       "ticketReleaseRate": 5,
       "customerRetrievalRate": 3,
       "maxTicketCapacity": 900
     }
     ```

2. **Set Configuration**
   - **Endpoint**: `POST /api/configuration`
   - **Description**: Updates system configuration.
   - **Request Body**:
     ```json
     {
       "totalSystemTickets": 5000,
       "ticketReleaseRate": 5,
       "customerRetrievalRate": 3,
       "maxTicketCapacity": 900
     }
     ```

---

#### System State

1. **Get System Status**
   - **Endpoint**: `GET /api/system/status`
   - **Description**: Retrieves the system's current status (e.g., NOT_CONFIGURED, STARTED, STOPPED, PAUSED).

2. **Start System**
   - **Endpoint**: `POST /api/system/start`
   - **Description**: Starts the system.
   - **Response**: "System started successfully."

3. **Pause System**
   - **Endpoint**: `POST /api/system/pause`
   - **Description**: Pauses the system.
   - **Response**: "System paused successfully."

4. **Stop and Reset System**
   - **Endpoint**: `POST /api/system/stop-reset`
   - **Description**: Stops and resets the system, clearing all actions.
   - **Response**: "System stopped and reset successfully."

---

#### Customer Manager

1. **Retrieve Customer Count**
   - **Endpoint**: `GET /api/customers/count`
   - **Description**: Gets the count of active customers.

2. **Add Customer**
   - **Endpoint**: `POST /api/customers/add`
   - **Description**: Adds a new customer to the system.

3. **Remove Customer**
   - **Endpoint**: `POST /api/customers/remove`
   - **Description**: Removes a customer from the system.

4. **Pause Customer**
   - **Endpoint**: `POST /api/customers/pause`
   - **Description**: Pauses actions for a specific customer.

5. **Resume Customer**
   - **Endpoint**: `POST /api/customers/resume`
   - **Description**: Resumes actions for a specific customer.

---

#### VIP Customer Manager

1. **Retrieve VIP Customer Count**
   - **Endpoint**: `GET /api/vip-customers/count`
   - **Description**: Gets the count of active VIP customers.

2. **Add VIP Customer**
   - **Endpoint**: `POST /api/vip-customers/add`
   - **Description**: Adds a new VIP customer to the system.

3. **Remove VIP Customer**
   - **Endpoint**: `POST /api/vip-customers/remove`
   - **Description**: Removes a VIP customer from the system.

4. **Pause VIP Customer**
   - **Endpoint**: `POST /api/vip-customers/pause`
   - **Description**: Pauses actions for a specific VIP customer.

5. **Resume VIP Customer**
   - **Endpoint**: `POST /api/vip-customers/resume`
   - **Description**: Resumes actions for a specific VIP customer.

---

#### Vendor Manager

1. **Retrieve Vendor Count**
   - **Endpoint**: `GET /api/vendors/count`
   - **Description**: Retrieves the count of active vendors.

2. **Add Vendor**
   - **Endpoint**: `POST /api/vendors/add`
   - **Description**: Adds a new vendor to the system.

3. **Remove Vendor**
   - **Endpoint**: `POST /api/vendors/remove`
   - **Description**: Removes a vendor from the system.

4. **Pause Vendor**
   - **Endpoint**: `POST /api/vendors/pause`
   - **Description**: Pauses actions for a vendor.

5. **Resume Vendor**
   - **Endpoint**: `POST /api/vendors/resume`
   - **Description**: Resumes actions for a vendor.

---

#### Ticket Pool

1. **Get Ticket Pool Status**
   - **Endpoint**: `GET /api/ticket-pool-status`
   - **Description**: Retrieves current ticket pool status, including total tickets and maximum capacity.
   - **Response**:
     ```json
     {
       "totalTickets": 0,
       "maxTicketCapacity": 0
     }
     ```



---

## 📂 Repository Structure
	
```sh
├── .angulardoc.json
├── .gitignore
├── LICENSE
├── README.md
├── backend
    ├── .gitattributes
    ├── .gitignore
    ├── .mvn
    │   └── wrapper
    │   │   └── maven-wrapper.properties
    ├── config
    │   └── configuration.json
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
    │   ├── main
    │       ├── java
    │       │   └── lk
    │       │   │   └── W2051760
    │       │   │       └── ticketing_system_backend
    │       │   │           ├── TicketingSystemBackendApplication.java
    │       │   │           ├── config
    │       │   │               ├── CorsConfig.java
    │       │   │               └── WebSocketConfig.java
    │       │   │           ├── controller
    │       │   │               ├── ConfigurationController.java
    │       │   │               ├── CustomerController.java
    │       │   │               ├── SystemController.java
    │       │   │               ├── TicketPoolController.java
    │       │   │               ├── VIPCustomerController.java
    │       │   │               └── VendorController.java
    │       │   │           ├── model
    │       │   │               ├── Configuration.java
    │       │   │               ├── CountUpdate.java
    │       │   │               ├── SystemState.java
    │       │   │               ├── SystemStateManager.java
    │       │   │               ├── Ticket.java
    │       │   │               ├── TicketUpdate.java
    │       │   │               ├── TransactionLog.java
    │       │   │               ├── User.java
    │       │   │               ├── consumer
    │       │   │               │   ├── Customer.java
    │       │   │               │   ├── CustomerManager.java
    │       │   │               │   ├── VIPCustomer.java
    │       │   │               │   └── VIPCustomerManager.java
    │       │   │               └── producer
    │       │   │               │   ├── Vendor.java
    │       │   │               │   └── VendorManager.java
    │       │   │           └── service
    │       │   │               ├── ConfigurationService.java
    │       │   │               ├── CountUpdateService.java
    │       │   │               ├── SystemManagementService.java
    │       │   │               ├── TicketPool.java
    │       │   │               ├── TicketUpdateService.java
    │       │   │               └── TransactionLogService.java
    │       └── resources
    │       │   ├── application.properties
    │       │   ├── configuration.json
    │       │   └── log4j2.xml
    │   └── test
    │       └── java
    │           └── lk
    │               └── W2051760
    │                   └── ticketing_system_backend
    │                       └── TicketingSystemBackendApplicationTests.java
└── frontend
    ├── .angular
        └── desktop.ini
    ├── .editorconfig
    ├── .gitignore
    ├── README.md
    ├── angular.json
    ├── package-lock.json
    ├── package.json
    ├── postcss.config.js
    ├── public
        └── favicon.ico
    ├── src
        ├── app
        │   ├── app.component.css
        │   ├── app.component.html
        │   ├── app.component.spec.ts
        │   ├── app.component.ts
        │   ├── app.config.ts
        │   ├── app.routes.ts
        │   ├── components
        │   │   ├── configuration-form
        │   │   │   ├── configuration-form.component.css
        │   │   │   ├── configuration-form.component.html
        │   │   │   ├── configuration-form.component.spec.ts
        │   │   │   └── configuration-form.component.ts
        │   │   ├── count-display
        │   │   │   ├── count-display.component.css
        │   │   │   ├── count-display.component.html
        │   │   │   ├── count-display.component.spec.ts
        │   │   │   └── count-display.component.ts
        │   │   ├── line-chart
        │   │   │   ├── line-chart.component.spec.ts
        │   │   │   └── line-chart.component.ts
        │   │   ├── system-control
        │   │   │   ├── system-control.component.css
        │   │   │   ├── system-control.component.html
        │   │   │   ├── system-control.component.spec.ts
        │   │   │   └── system-control.component.ts
        │   │   ├── ticket-pool-status
        │   │   │   ├── ticket-pool-status.component.css
        │   │   │   ├── ticket-pool-status.component.html
        │   │   │   ├── ticket-pool-status.component.spec.ts
        │   │   │   └── ticket-pool-status.component.ts
        │   │   ├── toast-container
        │   │   │   └── toast-container.component.ts
        │   │   └── transaction-log
        │   │   │   ├── transaction-log.component.css
        │   │   │   ├── transaction-log.component.html
        │   │   │   ├── transaction-log.component.spec.ts
        │   │   │   └── transaction-log.component.ts
        │   ├── models
        │   │   ├── configuration.model.ts
        │   │   ├── count-update.model.ts
        │   │   ├── ticket-update.model.ts
        │   │   └── transaction-log.model.ts
        │   └── services
        │   │   ├── configuration.service.spec.ts
        │   │   ├── configuration.service.ts
        │   │   ├── management.service.spec.ts
        │   │   ├── management.service.ts
        │   │   ├── system-control.service.spec.ts
        │   │   ├── system-control.service.ts
        │   │   ├── ticket.service.spec.ts
        │   │   ├── ticket.service.ts
        │   │   ├── toast.service.ts
        │   │   ├── web-socket-service.service.spec.ts
        │   │   └── web-socket-service.service.ts
        ├── environments
        │   ├── environment.prod.ts
        │   └── environment.ts
        ├── index.html
        ├── main.ts
        └── styles.css
    ├── tailwind.config.js
    ├── tsconfig.app.json
    ├── tsconfig.json
    └── tsconfig.spec.json
```

---

## 🎗 License

This project is protected under the [MIT License](#) License. 

---

## 🙌 Acknowledgments

I would like to express my sincere gratitude to the lecturers at the Institute of Information Technology (IIT) for their invaluable guidance and support throughout this project. Special thanks to the Object-Oriented Programming (OOP) module team, whose insights and teaching laid the foundation for this project. Their dedication to fostering a deep understanding of OOP principles and real-world applications has been instrumental in my development. Thank you for inspiring and empowering me to take on this challenge..

---
