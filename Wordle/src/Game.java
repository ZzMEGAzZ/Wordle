import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Game extends JFrame {

    private static final int MAX_GUESSES = 5;
    private int host;
    private String room;

    final private int WIDTH = 700;
    final private int HEIGHT = 850;
    private JTextField inputField;
    private JButton sendButton;
    private JLabel[][] guessLabels;
    Color yellow = new Color(231, 177, 10);
    Color green = new Color(122, 168, 116);
    Color grey = new Color(82, 74, 78);

    public Game(int host, String room) {

        // Create frame
        createGUI(room);

        /*--------------------------Connect Server-----------------------------*/
        try {
            // Connect to server
            Socket player = new Socket("localhost", host);
            System.out.println("Connected to server: " + player);

            // Set timeout
            player.setSoTimeout(100);

            // Create input and output streams
            OutputStream out = player.getOutputStream();
            PrintWriter printWriter = new PrintWriter(out, true);
            InputStream in = player.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // Create guess labels
            if (Boolean.parseBoolean(reader.readLine())) {
                setVisible(false);
            } else {
                inputField.addActionListener(new ActionListener() {
                    int currentGuess = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentGuess >= MAX_GUESSES) {
                            return;
                        }

                        // Get input
                        String input = inputField.getText();
                        if (input.length() == 5) {
                            printWriter.println(input);

                            try {
                                // Get result
                                String result = reader.readLine();
                                for (int i = 0; i < input.length(); i++) {
                                    guessLabels[currentGuess][i].setText(String.valueOf(input.charAt(i)));

                                    // Set background color
                                    if (result.charAt(i) == 'O') {
                                        guessLabels[currentGuess][i].setBackground(green);
                                    } else if (result.charAt(i) == 'X') {
                                        guessLabels[currentGuess][i].setBackground(grey);
                                    } else if (result.charAt(i) == '*') {
                                        guessLabels[currentGuess][i].setBackground(yellow);
                                    }
                                }
                                
                                // Increase current guess
                                currentGuess++;
                                
                                // Check if game is over
                                boolean status = Boolean.parseBoolean(reader.readLine());
                                if (!status) {
                                    // Game over
                                    showMessage("Server: " + reader.readLine());
                                    // Ask if player wants to play again
                                    int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "Confirm",
                                            JOptionPane.YES_NO_OPTION);
                                    if (playAgain == JOptionPane.YES_OPTION) {
                                        printWriter.println(true);
                                        currentGuess = 0;
                                        resetGame();
                                    } else {
                                        printWriter.println(false);
                                        currentGuess = 0;
                                        resetGame();
                                        player.close();
                                        setVisible(false);
                                    }
                                }
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        } else {
                            showMessage("Please enter 5 letters");
                        }
                        inputField.setText("");
                    }
                });
            }

        }
        // Catch exceptions
         catch (SocketTimeoutException e) {
            showMessage("Room is full");
            setVisible(false);

        }
        // Catch exceptions
        catch (IOException e) {
            showMessage("Connection failed");
            setVisible(false);

        }

    }
    /*--------------------------End of Connect Server-----------------------------*/

    /*--------------------------Create GUI-----------------------------*/

    private void createGUI(String room) {
        // Create frame
        setTitle("Wordle Game " + room);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create panels
        Label inputTitle = new Label("Guess the word:");
        inputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.BOLD, 30));
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.postActionEvent();
            }
        });

        // Create input Panels
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputTitle);
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        // Create word Panels
        JPanel wordPanel = new JPanel(new GridLayout(MAX_GUESSES, 5, 10, 10));
        wordPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        wordPanel.setBackground(Color.BLACK);

        // Create guess labels
        guessLabels = new JLabel[MAX_GUESSES][5];

        // Add guess labels to word panel
        for (int row = 0; row < MAX_GUESSES; row++) {
            for (int col = 0; col < 5; col++) {
                guessLabels[row][col] = new JLabel(" ");
                guessLabels[row][col].setHorizontalAlignment(JLabel.CENTER);
                guessLabels[row][col].setOpaque(true);
                guessLabels[row][col].setBackground(Color.BLACK);
                guessLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                guessLabels[row][col].setFont(new Font("Arial", Font.BOLD, 30));
                guessLabels[row][col].setForeground(Color.WHITE);
                guessLabels[row][col].setPreferredSize(new Dimension(50, 50));
                wordPanel.add(guessLabels[row][col]);
            }
        }

        // Create title label with white text
        JLabel titleLabel = new JLabel("Wordle Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);

        // Create title panel with black border
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.add(titleLabel);

        // Add panels to main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(wordPanel, BorderLayout.CENTER);

        // Add title label to title panel
        add(mainPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Set visible
        setVisible(true);
    }

    // Show alert message
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Reset game set to default
    private void resetGame() {
        for (int row = 0; row < MAX_GUESSES; row++) {
            for (int col = 0; col < 5; col++) {
                guessLabels[row][col].setText(" ");
                guessLabels[row][col].setForeground(Color.WHITE);
                guessLabels[row][col].setBackground(Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        int host = Integer.parseInt(args[0]);
        String room = args[1];
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game(host, room);
            }
        });
    }
}