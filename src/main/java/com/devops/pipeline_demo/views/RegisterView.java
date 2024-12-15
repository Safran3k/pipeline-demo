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
    private final TextField usernameField;
    private final PasswordField passwordField;

    @Autowired
    public RegisterView(UserService userService) {
        this.userService = userService;

        H3 header = new H3("Register");

        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");

        usernameField.setWidth("250px");
        passwordField.setWidth("250px");

        Button registerButton = new Button("Register", event -> handleRegistration());
        Button backToLoginButton = new Button("Back to Login", event -> getUI().ifPresent(ui -> ui.navigate("")));

        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField);

        VerticalLayout layout = new VerticalLayout(header, formLayout, registerButton, backToLoginButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setSizeFull();

        add(layout);
    }

    private void handleRegistration() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        String validationError = validateInputs(username, password);
        if (validationError != null) {
            Notification.show(validationError);
            return;
        }

        if (userService.usernameExists(username)) {
            Notification.show("Username already taken.");
            usernameField.clear();
            return;
        }

        userService.registerUser(username, password);
        Notification.show("Registration successful. Redirecting to login page...");

        clearFields();
        getUI().ifPresent(ui -> ui.navigate(""));
    }

    private String validateInputs(String username, String password) {
        if (username.length() < 5) {
            return "Username must be at least 5 characters long.";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }
        return null; // No errors
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}