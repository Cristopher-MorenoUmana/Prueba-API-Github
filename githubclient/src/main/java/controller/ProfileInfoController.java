package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import una.githubclient.App;
import util.UserSession;
import repository.ProfileApiClient;
import domain.ProfileResult;

public class ProfileInfoController implements Initializable {

    @FXML
    private ImageView profileImageView;
    @FXML
    private TextField nameTextF;
    @FXML
    private TextField followersTextF;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private TextField followingsTextF;
    @FXML
    private TextField locationTextF;
    @FXML
    private TextField creationDateTextF;
    @FXML
    private TextArea blogTextArea;
    @FXML
    private Button backBtn;
    
    private ProfileApiClient api;
    
    private ProfileResult profile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        updateWindowSize();
        
        System.out.println(UserSession.getUserName());        

        try {
            loadData();
        } catch (IOException | InterruptedException ex) {
            System.getLogger(ProfileInfoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void updateWindowSize() {

        Platform.runLater(() -> App.getStage().sizeToScene());
    }
    
    @FXML 
    public void backPressed() throws IOException {
        
        App.setRoot("MainMenu");
        updateWindowSize();
    }
    
    private void loadData() throws IOException, InterruptedException {

        String userName = UserSession.getUserName();
        
        this.api = new ProfileApiClient();

        this.profile = this.api.getProfile(userName);
        
        try {
            Image avatar = new Image(profile.getAvatarUrl(), true);
            this.profileImageView.setImage(avatar);
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen del perfil.");
        }

        this.nameTextF.setText("Nombre: " + profile.getLogin());
        this.followersTextF.setText("Seguidores: " + this.profile.getFollowers());
        this.followingsTextF.setText("Seguidos: " + this.profile.getFollowing());
        this.locationTextF.setText("Ubicacion: " + this.profile.getLocation());
        this.creationDateTextF.setText("Fecha de creación: " + this.profile.getCreationDate());
        this.bioTextArea.setText("Bio: " + this.profile.getBio());
        this.blogTextArea.setText("Blog: " + this.profile.getBlog());
    }
}
