package com.gaaf;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Login {

    private GridPane root;
    private App app;

    public Login(App app) {
        this.app = app;
        crearVista();
    }

    private void crearVista() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        // Usuario
        Label userLabel = new Label("Usuario:");
        root.add(userLabel, 0, 0);
        TextField userField = new TextField();
        root.add(userField, 1, 0);

        // Contraseña
        Label passLabel = new Label("Contraseña:");
        root.add(passLabel, 0, 1);
        PasswordField passField = new PasswordField();
        root.add(passField, 1, 1);

        // Botón Iniciar Sesión
        Button loginButton = new Button("Iniciar Sesión");
        root.add(loginButton, 1, 2);

        // Acción del botón: abrir HomeView
        loginButton.setOnAction(e -> {
            String usuario = userField.getText();
            String contrasena = passField.getText();

            // Aquí puedes validar credenciales si quieres
            if (usuario.equals("admin") && contrasena.equals("1234")) {
                app.mostrarHome(); // cambia de pantalla
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }
        });
    }

    public GridPane getRoot() {
        return root;
    }
}
