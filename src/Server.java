import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {

  private int host;
  private boolean clientConnected = false;

  public void server(int host) {

    boolean isRunning = true;
    int round = 0;

    try {
      /*----------------------- Setting -----------------------*/
      // create a socket to connect to the server
      ServerSocket server = new ServerSocket(host);

      while (true) {
        
        // create a socket to connect to the server
        Socket player = server.accept();
        // Accept incoming client connections only if no client is currently connected
        if (!clientConnected) {

          // print out the socket information
          System.out.println("Player Connected at " + player);

          // get the output stream from the socket.
          InputStream in = player.getInputStream();

          // create buffer reader
          BufferedReader reader = new BufferedReader(new InputStreamReader(in));

          // get the output stream from the socket.
          OutputStream out = player.getOutputStream();

          // create a print writer
          PrintWriter PrintWrite = new PrintWriter(out, true);

          PrintWrite.println(clientConnected);

          clientConnected = true;
          isRunning = true;

          /*--------------------- end Setting ---------------------*/

          /*--------------------- Game Server ---------------------*/
          while (isRunning) {
            // get the random word
            String word = getWord();
            System.out.println("player room " + host + " is playing with word: " + word);

            while (true) {
              // get the input word.
              String input = reader.readLine();
              System.out.println("player room " + host + " input: " + input + "");

              // check the input word.
              if (input.equals(word)) {
                PrintWrite.println(checkWord(input, word));
                PrintWrite.println(false);
                PrintWrite.println("You win");
                break;
              } else {
                PrintWrite.println(checkWord(input, word));
                round++;
              }
              if (round == 5) {
                PrintWrite.println(false);
                PrintWrite.println("You lose");
                break;
              }
              PrintWrite.println(true);
            }
            round = 0;
            isRunning = Boolean.parseBoolean(reader.readLine());
          }
          System.out.println("Player disconnected");
          clientConnected = false;
        } else {
          PrintWriter PrintWrite = new PrintWriter(player.getOutputStream(), true);
          PrintWrite.println(true);
        }
      }

    } catch (Exception e) {
      System.out.println(e);
    }

  } 
      /*--------------------- End Game Server ---------------------*/

      /*--------------------- Random word ---------------------*/

  public String getWord() {
    List<String> word = new ArrayList<String>();
    String randomWord = "";

    // read file
    try {
      File file = new File("wordList.txt");
      Scanner myReader = new Scanner(file);
      while (myReader.hasNextLine()) {
        word.add(myReader.nextLine());
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    int random = (int) (Math.random() * word.size());
    randomWord = word.get(random);

    return randomWord;
  }

  /*--------------------- End Random word ---------------------*/

  /*--------------------- Check word ---------------------*/

  public String checkWord(String input, String word) {
    String result = "";

    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) == word.charAt(i)) {
        result += "O";
      } else if (word.contains(input.charAt(i) + "")) {
        result += "*";
      } else {
        result += "X";
      }
    }
    return result;
  }

  /*--------------------- End Check word ---------------------*/

  /*--------------------- Thread ---------------------*/

  @Override
  public void run() {
    System.out.println("Thread " + Thread.currentThread().getName() + " is running");
    switch (Thread.currentThread().getName()) {
      case "game1":
        server(1111);
        break;
      case "game2":
        server(2222);
        break;
      case "game3":
        server(3333);
        break;
      case "game4":
        server(4444);
        break;
      default:
        System.out.println("default");
        break;
    }
  }

  /*--------------------- End Thread ---------------------*/

  /*--------------------- Main ---------------------*/

  public static void main(String[] args) {
    Thread t1 = new Thread(new Server());
    t1.setName("game1");
    Thread t2 = new Thread(new Server());
    t2.setName("game2");
    Thread t3 = new Thread(new Server());
    t3.setName("game3");
    Thread t4 = new Thread(new Server());
    t4.setName("game4");

    t1.start();
    t2.start();
    t3.start();
    t4.start();
  }

  /*--------------------- End Main ---------------------*/
}