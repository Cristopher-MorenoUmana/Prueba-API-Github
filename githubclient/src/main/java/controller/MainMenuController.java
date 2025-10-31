package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import una.githubclient.App;
import util.UserSession;
import repository.ProfileApiClient;

public class MainMenuController implements Initializable {

    @FXML
    private Button profileInfoBtn;
    @FXML
    private Button reposInfoBtn;
    @FXML
    private VBox optionsVbox;
    @FXML
    private TextField userNameTextF;
    @FXML
    private Button acceptBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        updateWindowSize();
        
        this.userNameTextF.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                UserSession.setUserName(this.userNameTextF.getText());
            }
});

        acceptBtn.setOnAction(eh -> {

            checkUserName();
            UserSession.setUserName(this.userNameTextF.getText());
        });
    }

    private void checkUserName() {
     
        String user = UserSession.getUserName();
        
        if (user == null || user.isBlank()) {
            
            this.optionsVbox.setDisable(true);
            System.out.println("Nombre de usuario invalido");

            return;
        }
        
        ProfileApiClient profileApiClient = new ProfileApiClient();
        
        boolean existsProfile = profileApiClient.userExists(user);
        
        if(!existsProfile) {
            
            this.optionsVbox.setDisable(true);
            
            System.out.println("El nombre de usuario no existe en github");
            
            return;
        }
        
        System.out.println("El nombre de usuario SI existe en github");
        this.optionsVbox.setDisable(false);
    }

    private void updateWindowSize() {

        Platform.runLater(() -> App.getStage().sizeToScene());
    }
 
    @FXML
    public void profileInfoPressed() throws IOException {
        
        App.setRoot("ProfileInfo");
    }

    @FXML
    public void reposInfoPressed() throws IOException {

        App.setRoot("ReposInfo");
    }
}
