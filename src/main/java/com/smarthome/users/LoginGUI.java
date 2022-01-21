package com.smarthome.users;

import com.smarthome.commandlineinterface.CLI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class LoginGUI extends JFrame implements ActionListener {

    private final ArrayList<User> usersCopy;
    private final JLabel status;
    private final JTextField userText;
    private final JTextField passText;
    private final JButton loginButton;
    private final JButton signupButton;
    private final JComboBox<String> isAdmin;

    public LoginGUI(ArrayList<User> users)
    {
        super();
        this.usersCopy = users;
        this.setSize(350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("SmartHome - Welcome");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        this.userText = new JTextField(20);
        this.userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        this.passText = new JTextField(20);
        this.passText.setBounds(100, 50, 165, 25);
        panel.add(passText);

        this.loginButton = new JButton("Login");
        this.loginButton.setBounds(10, 90, 80, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        this.signupButton = new JButton("Signup");
        this.signupButton.setBounds(100, 90, 80, 25);
        signupButton.addActionListener(this);
        panel.add(signupButton);

        this.status = new JLabel("");
        this.status.setBounds(10, 120, 300, 25);
        panel.add(status);

        this.isAdmin = new JComboBox<>(new String[]{"user", "admin"});
        this.isAdmin.setBounds(190, 90, 80, 25);
        panel.add(isAdmin);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource()==loginButton){
            if(userAuthentication(userText.getText(), passText.getText())) {
                status.setText("Login Successful!");
                this.setVisible(false);
                try { CLI.runCLI(); } catch (ParseException parseException) { parseException.printStackTrace(); }
            }else{
                status.setText("username or password incorrect!");
            }
        }

        if (e.getSource()==signupButton){
            if(!userAuthentication(userText.getText(), passText.getText())) {
                try {
                    UserAuthentication.userSignUp(userText.getText(), passText.getText(), (isAdmin.getSelectedItem()=="admin"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                status.setText("Sign up success!");
                this.setVisible(false);
                try { CLI.runCLI(); } catch (ParseException parseException) { parseException.printStackTrace(); }
            }else{
                status.setText("account already exist try logging in");
            }
        }
    }

    private boolean userAuthentication(String username, String password){
        for(User user : usersCopy) {
            if (user.username.equals(username) && user.password.equals(password)){
                UserAuthentication.setActiveUser(user);
                return true;
            }
        }
        return false;
    }
}
