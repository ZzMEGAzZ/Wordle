import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player extends JFrame {

    private Game game;
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;
    private JLabel label;
    private JTextField textField;;
    private JTextArea textArea;

    public Player() {
        
        setTitle("Player");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        label = new JLabel("Select Room : ");
        textField = new JTextField(10);
        JButton Room1 = new JButton("room1");
        JButton Room2 = new JButton("room2");
        JButton Room3 = new JButton("room3");
        JButton Room4 = new JButton("room4");
        textArea = new JTextArea(10, 10);

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(Room1);
        panel.add(Room2);
        panel.add(Room3);
        panel.add(Room4);

        add(panel, BorderLayout.NORTH);

        Room1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(1111);
            }
        });

        Room2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(2222);
            }
        });

        Room3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(3333);
            }
        });

        Room4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(4444);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {

        Player playerUI = new Player();
    }
}
