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

@Route("register")
public class RegisterView extends VerticalLayout {

    private final UserService userService;

    @Autowired
    public RegisterView(UserService userService) {
        this.userService = userService;

        H3 header = new H3("Register");

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        usernameField.setWidth("250px");
        passwordField.setWidth("250px");

        Button registerButton = new Button("Register");
        Button backToLoginButton = new Button("Back to Login");

        registerButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            if (userService.usernameExists(username)) {
                Notification.show("Username already taken.");
                return;
            }

            userService.registerUser(username, password);
            Notification.show("User registered successfully.");
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField);

        VerticalLayout layout = new VerticalLayout(header, formLayout, registerButton, backToLoginButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setSizeFull();

        add(layout);
    }
}
