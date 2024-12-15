package com.devops.pipeline_demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;

@Route("welcome")
public class WelcomeView extends VerticalLayout implements BeforeEnterObserver {

    public WelcomeView() {
        add(createLogoutButton());
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String username = (String) VaadinSession.getCurrent().getAttribute("username");

        if (username == null) {
            redirectToLoginPage();
        } else {
            add(createWelcomeMessage(username));
        }
    }

    private Button createLogoutButton() {
        return new Button("Logout", event -> {
            VaadinSession.getCurrent().close();
            redirectToLoginPage();
        });
    }

    private H1 createWelcomeMessage(String username) {
        return new H1("Welcome " + username + "!");
    }

    private void redirectToLoginPage() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }
}