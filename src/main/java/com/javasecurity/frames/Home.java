package com.javasecurity.frames;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.javasecurity.LoginSession;
import com.javasecurity.entities.User;

public class Home extends JFrame implements ActionListener {
    private JLabel showInfoLabel;
    private JButton btnLogout;
    public Home() {
        initComponents();
        btnLogout.addActionListener(this);
        setTitle("Your information");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 400);
    }
    private void initComponents() {
        String text = "";
        User user = LoginSession.getSession();
        text += "<html>";
        text += "<p>ID: " + user.getId() + "</p>";
        text += "<p>Username: " + user.getUsername() + "</p>";
        text += "<p>Full name: " + user.getFullName() + "</p>";
        text += "<p>Address: " + user.getAddress() + "</p>";
        text += "<p>Phone number: " + user.getPhoneNumber() + "</p>";
        text += "</html>";
        showInfoLabel = new JLabel();
        showInfoLabel.setText(text);
        showInfoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        showInfoLabel.setBounds(10, 10, 400, 150);
        add(showInfoLabel);

        btnLogout = new JButton("Log out");
        btnLogout.setBounds(10, 160, 120, 60);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(new Font(null, Font.BOLD, 18));
        add(btnLogout);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogout) {
            setVisible(false);
            new Login().setVisible(true);
            LoginSession.setSession(null);
        }
    }
}
