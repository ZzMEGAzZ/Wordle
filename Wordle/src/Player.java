import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.Group;

import java.awt.*;
import java.awt.event.*;

public class Player extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    private Game game;
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 100;

    private JLabel label;

    public Player() {

        // set frame
        setTitle("Player");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLocation(((screenWidth/2) - (WIDTH /2)), 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // set label
        label = new JLabel("Select Room : ");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        JButton Room1 = new JButton("room1");
        JButton Room2 = new JButton("room2");
        JButton Room3 = new JButton("room3");
        JButton Room4 = new JButton("room4");

        JButton[] rooms = { Room1, Room2, Room3, Room4 };

        //set button style
        for (JButton room : rooms) {
            room.setBackground(Color.BLACK);
            room.setForeground(Color.WHITE);
            room.setFont(new Font("Arial", Font.BOLD, 15));
            room.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            room.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        //set hover color
        for (JButton room : rooms) {
            room.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    room.setBackground(Color.WHITE);
                    room.setForeground(Color.BLACK);
                }

                public void mouseExited(MouseEvent e) {
                    room.setBackground(Color.BLACK);
                    room.setForeground(Color.WHITE);
                }
            });
        }

        //set panel
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(Room1);
        panel.add(Room2);
        panel.add(Room3);
        panel.add(Room4);
        panel.setBackground(Color.BLACK);

        add(panel, BorderLayout.CENTER);

        /*------------------Set Action-------------------*/
        Room1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(1111, "room1");
            }
        });

        Room2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(2222 , "room2");
            }
        });

        Room3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(3333 , "room3");
            }
        });

        Room4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game(4444 , "room4");
            }
        });

        /*------------------Set Action-------------------*/

        // set visible
        setVisible(true);
    }

    public static void main(String[] args) {

        Player playerUI = new Player();
    }
}
