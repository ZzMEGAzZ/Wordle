# Wordle Game
## with Socket and Multithreading using GUI in Java 

## This is a Wordle game created using Java programming language with socket and multithreading, which allows multiple players to play simultaneously.

### Requirements
 - Java Development Kit (JDK) 1.8 or later
 - Eclipse or any other Java IDE
 - Git

### Getting Started
  To get started with the Wordle game, follow these steps:

  1. Clone the repository to your local machine using Git:
  
  ```
  git clone https://github.com/ZzMEGAzZ/wordle-game.git
  ```
  
  2. Open the project in Eclipse or any other Java IDE.
  3. Run the Server.java file to start the server. The server will be started on the default port 1234.
  4 .Run the Player.java file to start the client. You can run multiple instances of the client to simulate multiple players.
  5. select your Room
  6. Once the players have joined the game, the server will randomly select a word and send it to the clients.
  7. The players have 5 attempts to guess the word. Each guess will be checked by the server, and the correct letters will be displayed in the correct positions.
  8. The player who guesses the word correctly wins the game.

### GUI Design
The GUI of the Wordle game is designed using the Java Swing library. The following are the main components of the GUI:

  - JFrame: The main window of the game.
  - JPanel: The panel that contains all the components of the game.
  - JLabel: The label that displays the word to be guessed and the letters guessed by the player.
  - JTextField: The text field where the player enters the guess.
  - JButton: The button that the player clicks to submit the guess.
  
### Multithreading
  The Wordle game uses multithreading to handle multiple clients simultaneously. The Server class creates a new thread for each client that connects to the server. The ClientHandler class handles the communication between the server and the client. Each instance of the ClientHandler class runs in a separate thread.

### Socket Programming
  The Wordle game uses socket programming to establish communication between the server and the clients. The Server class creates a ServerSocket object that listens on a specific port for incoming client connections. When a client connects to the server, the Server class accepts the connection and creates a new thread to handle the communication with the client. The Client class creates a Socket object that connects to the server on the specified port.

### Conclusion
  The Wordle game with socket and multithreading using GUI in Java is a fun and interactive game that allows multiple players to play simultaneously. The game showcases the use of multithreading and socket programming to handle multiple clients simultaneously. The GUI design using the Java Swing library makes the game visually appealing and easy to use.
  
  ![wordle](https://user-images.githubusercontent.com/85662757/234306376-8d02d375-ac98-4292-9a61-0d0883cb4783.png)

  
