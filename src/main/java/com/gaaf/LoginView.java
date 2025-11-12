package com.gaaf;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginView {
    private final BorderPane root = new BorderPane();
    private final AuthDAO dao = new AuthDAO();

    public LoginView(App app){
        root.setPadding(new Insets(12));

        // Logo y titulo
        ImageView logo = null;
        try {
            logo = new ImageView(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
            logo.setFitHeight(40); 
            logo.setPreserveRatio(true);
        } catch (Exception ignore){}

        Label titulo = new Label("Acceso");
        titulo.getStyleClass().add("titulo");

        HBox header = (logo != null) ? new HBox(10, logo, titulo) : new HBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));

        // Campos
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario (nombre)");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Contrasena");

        Button btnLogin = new Button("Iniciar sesion");
        btnLogin.setDefaultButton(true);
        btnLogin.setPrefWidth(180);

        VBox form = new VBox(8, new Label("Usuario"), txtUsuario,
                                new Label("Contrasena"), txtPass,
                                btnLogin);
        form.setPadding(new Insets(16));
        form.setAlignment(Pos.CENTER_LEFT);

        BorderPane card = new BorderPane(form);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color:#ffffff; -fx-border-color:#d0d3d9; -fx-border-radius:10; -fx-background-radius:10;");

        VBox center = new VBox(10, header, card);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(20));
        root.setCenter(center);
        root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // ACCIÓN LOGIN
        btnLogin.setOnAction(e -> {
            String u = txtUsuario.getText() != null ? txtUsuario.getText().trim() : "";
            String p = txtPass.getText() != null ? txtPass.getText() : "";
            if(u.isEmpty() || p.isEmpty()){
                new Alert(Alert.AlertType.WARNING, "Completa usuario y contrasena.").showAndWait();
                return;
            }
            try{
                Usuario usr = dao.login(u, p);
                if(usr == null){
                    new Alert(Alert.AlertType.ERROR, "Credenciales invalidas o usuario inactivo.").showAndWait();
                } else {
                    // Guardamos usuario en la sesión
                    Sesion.iniciarSesion(usr);

                    app.setUsuarioActual(usr); // si tu app lo usa
                    app.mostrarHome(); // navega al Home
                }
            } catch(Exception ex){
                new Alert(Alert.AlertType.ERROR, "Error autenticando: " + ex.getMessage()).showAndWait();
            }
        });
    }

    public BorderPane getRoot(){ return root; }
}
