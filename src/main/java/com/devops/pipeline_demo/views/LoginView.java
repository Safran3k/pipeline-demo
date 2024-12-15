package com.devops.pipeline_demo.views;

import com.devops.pipeline_demo.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@Route("")
public class LoginView extends VerticalLayout {

    private final UserService userService;

    @Autowired
    public LoginView(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;

        H3 header = new H3("Login");

        TextField usernameField = createTextField("Username");
        PasswordField passwordField = createPasswordField("Password");

        Button loginButton = new Button("Login", event -> handleLogin(usernameField, passwordField));
        Button registerButton = new Button("Create account", event -> getUI().ifPresent(ui -> ui.navigate("register")));

        FormLayout formLayout = new FormLayout(usernameField, passwordField);
        VerticalLayout layout = new VerticalLayout(header, formLayout, loginButton, registerButton);

        layout.setAlignItems(Alignment.CENTER);
        layout.setSizeFull();
        add(layout);
    }

    private TextField createTextField(String label) {
        TextField textField = new TextField(label);
        textField.setWidth("250px");
        return textField;
    }

    private PasswordField createPasswordField(String label) {
        PasswordField passwordField = new PasswordField(label);
        passwordField.setWidth("250px");
        return passwordField;
    }

    private void handleLogin(TextField usernameField, PasswordField passwordField) {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        if (!isInputValid(username, password)) {
            return;
        }

        if (userService.validateUser(username, password)) {
            saveUserToSession(username);
            Notification.show("Login successful!");
            getUI().ifPresent(ui -> ui.navigate("welcome", QueryParameters.simple(Map.of("username", username))));
        } else {
            Notification.show("Invalid username or password.");
            clearFields(usernameField, passwordField);
        }
    }

    private boolean isInputValid(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Notification.show("Username and password cannot be empty.");
            return false;
        }
        return true;
    }

    private void saveUserToSession(String username) {
        VaadinSession.getCurrent().setAttribute("username", username);
    }

    private void clearFields(TextField usernameField, PasswordField passwordField) {
        usernameField.clear();
        passwordField.clear();
    }
}