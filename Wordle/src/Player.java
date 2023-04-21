import java.io.*;
import java.net.*;
import java.util.*;

public class Player {

    public static void main(String[] args) {
        while (true) {
            int room = 0;
            System.out.println("enter room number 1-4:");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                room = scanner.nextInt();
            }
            if (room < 1 || room > 40 ) {
                System.out.println("invalid room number");
            } else {
                Player player = new Player();
                if (room == 1) {
                    player.player(1111);
                } else if (room == 2) {
                    player.player(2222);
                } else if (room == 3) {
                    player.player(3333);
                } else if (room == 4) {
                    player.player(4444);
                }
            }
        }

    }

    public void player(int host) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        boolean status = true;

        try {
            // create a socket to connect to the server
            Socket player = new Socket("localhost", host);
            // print out the socket information
            System.out.println("Connected to server: " + player);
            player.setSoTimeout(100); // set a 5-second timeout

            // get the output stream from the socket.
            OutputStream out = player.getOutputStream();

            // create a print writer
            PrintWriter PrintWrite = new PrintWriter(out, true);

            // get the input stream from the socket.
            InputStream in = player.getInputStream();

            // create buffer reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            if (Boolean.parseBoolean(reader.readLine())) {
                System.out.println("room is full");
                return;
            } else {
                System.out.println("connectsuccess");
            }

            /*---------------------end Setting---------------------*/

            // get the input name.
            System.out.print("Enter your name : ");
            String name = scanner.nextLine();

            // send the name to server
            PrintWrite.println(name);

            while (isRunning) {
                while (status) {
                    // get the input word.
                    System.out.print("Enter your word : ");
                    String input = scanner.nextLine();

                    while (input.length() != 5) {
                        System.out.println("Please enter 5 letters");
                        System.out.print("Enter your word : ");
                        input = scanner.nextLine();
                    }

                    // send the word to server
                    PrintWrite.println(input);

                    // get the result from server
                    String result = reader.readLine();
                    System.out.println("Server: " + result);

                    status = Boolean.parseBoolean(reader.readLine());
                }
                System.out.println("Server: " + reader.readLine());
                System.out.print("Play again? (y/n) : ");
                String again = scanner.nextLine();

                if (again.equals("n")) {
                    PrintWrite.println(false);
                    isRunning = false;
                } else if (again.equals("y")) {
                    PrintWrite.println(true);
                    status = true;
                }
            }

            // close the socket connection.
            player.close();

        } catch (SocketTimeoutException e) {
            System.out.println("Server did not respond within timeout period");
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
