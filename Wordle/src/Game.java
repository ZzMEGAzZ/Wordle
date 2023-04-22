import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.HostnameVerifier;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {

    private int host;

    private JTextField inputField;
    private JTextArea messageArea;
    private JButton sendButton;

    public Game(int host) {
        createGUI();

        try {
            Socket player = new Socket("localhost", host);
            System.out.println("Connected to server: " + player);
            player.setSoTimeout(100);

            OutputStream out = player.getOutputStream();
            PrintWriter PrintWrite = new PrintWriter(out, true);
            InputStream in = player.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            if (Boolean.parseBoolean(reader.readLine())) {
                setVisible(false);
            } else {
                showMessage("-----------------------------------------Rules-----------------------------------------------");
                showMessage("    -- guess the word in 5 letters then press enter or click send -- ");
                showMessage("    - if return 'O' you guess the word correctly");
                showMessage("    - if return 'X' you guess the word incorrectly");
                showMessage("    - if return '*'' you guess the word correctly but not in the right order");
                showMessage("    -- You can guess the word in 5 Times -- ");
                showMessage("-----------------------------------------------------------------------------------------------");
                showMessage("\n   ready to play");
            }

            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = inputField.getText();
                    if (input.length() == 5) {
                        PrintWrite.println(input);
                        try {
                            String result = reader.readLine();
                            showMessage("    Your input is: " + input + " " + result);
                            boolean status = Boolean.parseBoolean(reader.readLine());
                            if (!status) {
                                showMessage("    Server: " + reader.readLine());
                                int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "Confirm", JOptionPane.YES_NO_OPTION);
                                if (playAgain == JOptionPane.YES_OPTION) {
                                    PrintWrite.println(true);
                                } else {
                                    PrintWrite.println(false);
                                    player.close();
                                    setVisible(false);
                                }
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else {
                        showMessage("    -- Please enter 5 letters --");
                    }
                    inputField.setText("");
                }
            });

        } catch (SocketTimeoutException e) {
            //alert user that connection timed out
            JLabel label = new JLabel("Room is full");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", JOptionPane.ERROR_MESSAGE);

            //close current JFrame
            setVisible(false);
            
        } catch (IOException e) {
            //alert user that connection failed
            JLabel label = new JLabel("Connection failed");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", JOptionPane.ERROR_MESSAGE);

            //close current JFrame
            setVisible(false);

        }
    }

    private void createGUI() {
        setTitle("Word Game");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inputField = new JTextField(20);
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.postActionEvent();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showMessage(String message) {
        messageArea.append(message + "\n");
    }

    public static void main(String[] args) {
        int host = Integer.parseInt(args[0]);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game(host);
            }
        });
    }
}
