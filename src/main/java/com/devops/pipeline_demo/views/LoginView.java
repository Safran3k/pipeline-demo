package com.devops.pipeline_demo.views;

import com.devops.pipeline_demo.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Route("login")
public class LoginView extends VerticalLayout {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginView(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        H3 header = new H3("Login");

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        usernameField.setWidth("250px");
        passwordField.setWidth("250px");

        Button loginButton = new Button("Login");

        loginButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            if (userService.validateUser(username, password)) {
                Notification.show("Login successful!");
            } else {
                Notification.show("Invalid username or password.");
            }
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField);

        VerticalLayout layout = new VerticalLayout(header, formLayout, loginButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setSizeFull();

        add(layout);
    }
}