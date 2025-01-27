
# Real Time Chat App
An application that allows clients to exchange messages in a selected PORT in real-time using Sockets.

## Technologies
- Java 17: Main Backend language, using Java Sockets.

- JavaFX: Java library used in the creation of an interactive UI.

- Spring Security: Responsible in the process of encryption of sensible data, like passwords, and authentication.

- Database (MySQL): Integrated MySQL database to store users information and save the messages history.

- Maven: Dependencies Manager

# Application
The application contains two runnable modules: `Server` and `ClientApp`.

-`Server`: Responsible for starting the server that will connect the clients. Currently, there are two available `PORTS` for communication: [4000] and [4001], which are manually created in the `Server` class.

-`ClientApp`: Responsible for starting the `Client-Side` application, where the Frontend is displayed and the user can interact. Multiple instances can run simultaneously to simulate various users.

Uses different threads to ensure both listening and sending messages happens in real-time via Sockets connection.

# Run Locally

## Prerequisites
- JDK 17
- JavaFX
- Maven 3.6+
- MySQL
- Git

## Configuring

### 1. Clone the Repository
```
git clone https://github.com/your-username/real-time-chat-app.git
cd real-time-chat-app
```
### 2. Installing Dependencies
To install all the necessary dependencies, run the following command at the root of the project's backend : `/chatapp`
```
mvn clean install
```
This command will download all the dependencies specified in the `pom.xml` file and compile the project.

### 3. Configuring Database
Set up the connection to your Database in `DBConnection.java`
```
connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/your-db",
                        "your-username",
                        "your-password"
```
### Running
- Open the project in your IDE. 
- Locate the `Server.java` class and run to start the Server.
- Locate the `ClientApp.java` class and run to start the Client-Side.

Make sure both of them are running when using the app to avoid errors.


## Demo

![App demo](https://github.com/user-attachments/assets/ebb2cdc6-f72d-4eb4-8091-c9487dececb2)


## Contact
Email: alvarocampioni@usp.br


