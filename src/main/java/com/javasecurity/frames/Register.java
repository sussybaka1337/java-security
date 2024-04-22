/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.javasecurity.frames;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.hibernate.Session;

import com.javasecurity.Helpers;
import com.javasecurity.entities.User;
import com.javasecurity.utilities.HibernateUtil;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame implements ActionListener {
    JButton btnRegister;
    JButton redirectToLogin;
    JTextField usernameField, fullNameField, addressField, phoneNumberField;
    JLabel usernameLabel, fullNameLabel, addressLabel, phoneNumberLabel, passwordLabel;
    JPasswordField passwordField;
    public Register() {
        initComponents();
        btnRegister.addActionListener(this);
        redirectToLogin.addActionListener(this);
        setTitle("Register form");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 400);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            Session session = HibernateUtil.openSession();
            var query = session.createNativeQuery("SELECT COUNT(*) FROM users WHERE username = :username", Integer.class);
            query.setParameter("username", usernameField.getText());
            if (query.getSingleResult() == 1) {
                JOptionPane.showMessageDialog(rootPane, "User already existed. Please choose other username");
                session.close();
                return;
            }
            String passwordText = String.valueOf(passwordField.getPassword());
            String encryptedPassword = Helpers.encryptPassword(passwordText);
            session.beginTransaction();
            User user = new User(
                usernameField.getText(),
                encryptedPassword,
                fullNameField.getText(),
                addressField.getText(),
                phoneNumberField.getText()
            );
            session.persist(user);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(rootPane, "You have been registered. Login now!");
            session.close();
            setVisible(false);
            new Login().setVisible(true);
        }
        if (e.getSource() == redirectToLogin) {
            setVisible(false);
            new Login().setVisible(true);
        }
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

        fullNameLabel = new JLabel("Full name");
        fullNameLabel.setBounds(10, 110, 80, 50);
        fullNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(fullNameLabel);
        fullNameField = new JTextField();
        fullNameField.setBounds(90, 120, 200, 35);
        add(fullNameField);

        addressLabel = new JLabel("Address");
        addressLabel.setBounds(10, 150, 80, 50);
        addressLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(90, 160, 200, 35);
        add(addressField);

        phoneNumberLabel = new JLabel("Phone number");
        phoneNumberLabel.setBounds(10, 200, 120, 50);
        phoneNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(phoneNumberLabel);
        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(130, 210, 200, 35);
        add(phoneNumberField);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(10, 270, 120, 60);
        btnRegister.setFocusPainted(false);
        btnRegister.setFont(new Font(null, Font.BOLD, 18));
        add(btnRegister);

        redirectToLogin = new JButton("Login");
        redirectToLogin.setBounds(150, 270, 120, 60);
        redirectToLogin.setFocusPainted(false);
        redirectToLogin.setFont(new Font(null, Font.BOLD, 18));
        add(redirectToLogin);
    }
}
