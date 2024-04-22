package com.javasecurity.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.hibernate.Session;

import com.javasecurity.Helpers;
import com.javasecurity.LoginSession;
import com.javasecurity.entities.User;
import com.javasecurity.utilities.HibernateUtil;

public class Login extends JFrame implements ActionListener {
    JButton btnLogin, redirectToRegister;
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel usernameLabel, passwordLabel;
    public Login() {
        initComponents();
        btnLogin.addActionListener(this);
        redirectToRegister.addActionListener(this);
        setTitle("Login form");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 250);
    }
    private void initComponents() {
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 10, 80, 50);
        usernameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(90, 20, 200, 35);
        add(usernameField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 60, 80, 50);
        passwordLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(90, 70, 200, 35);
        add(passwordField);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(10, 125, 120, 60);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font(null, Font.BOLD, 18));
        add(btnLogin);

        redirectToRegister = new JButton("Register");
        redirectToRegister.setBounds(150, 125, 120, 60);
        redirectToRegister.setFocusPainted(false);
        redirectToRegister.setFont(new Font(null, Font.BOLD, 18));
        add(redirectToRegister);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            var sql = "SELECT * FROM users WHERE username = :username AND `password` = :password";
            Session session = HibernateUtil.openSession();
            var query = session.createNativeQuery(sql, User.class);
            query.setParameter("username", usernameField.getText());
            String passwordText = String.valueOf(passwordField.getPassword());
            String encryptedPassword = Helpers.encryptPassword(passwordText);
            query.setParameter("password", encryptedPassword);
            User user;
            try {
                user = query.getSingleResult();
            } catch (Exception exception) {
                user = null;
            }
            if (user != null) {
                JOptionPane.showMessageDialog(rootPane, "Login successfully!");
                LoginSession.setSession(user);
                setVisible(false);
                new Home().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Failed to login! Please check your credentials again!");
            }
            session.close();
        }
        if (e.getSource() == redirectToRegister) {
            setVisible(false);
            new Register().setVisible(true);
        }
    }
}
