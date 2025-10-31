package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import una.githubclient.App;
import util.UserSession;
import repository.RepoApiClient;
import java.util.List;
import domain.RepoResult;
import java.util.Map;
import javafx.scene.chart.XYChart;

public class ReposInfoController implements Initializable {

    @FXML
    private TextField nameTextF;
    @FXML
    private TextArea descriptionTextF;
    @FXML
    private TextField languageTextF;
    @FXML
    private Button nextRepoBtn;
    @FXML
    private Button previousRepoBtn;
    @FXML
    private TextField stargazersTextF;
    @FXML
    private TextField forksTextF;
    @FXML
    private TextField lastUpdateTextF;
    @FXML
    private Button backBtn;
    @FXML
    private BarChart<String, Number> languajesBarChart;
    @FXML
    private Text currentIndexText;
    @FXML
    private Text finalIndexText;

    private List<RepoResult> repos;

    private RepoApiClient api;

    private Integer index = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        updateWindowSize();

        try {
            loadData();
        } catch (IOException | InterruptedException ex) {
            System.getLogger(ReposInfoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        loadDataByIndex();
        manageIndex();
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
        
        this.api = new RepoApiClient();
        this.repos = this.api.getRepos(userName);
        this.api.getLanguages(userName, this.repos);
        
        this.index = 0;
    }

    private void manageIndex() {

        int maxIndex = repos.size() - 1;
        int minIndex = 0;

        this.finalIndexText.setText(String.valueOf(repos.size()));

        this.currentIndexText.setText(String.valueOf(this.index + 1));

        this.nextRepoBtn.setOnAction(eh -> {
            if (this.index < maxIndex) {
                this.index++;
                loadDataByIndex();
                this.currentIndexText.setText(String.valueOf(this.index + 1));
            }
        });

        this.previousRepoBtn.setOnAction(eh -> {
            if (this.index > minIndex) {
                this.index--;
                loadDataByIndex();
                this.currentIndexText.setText(String.valueOf(this.index + 1));
            }
        });
    }

    private void loadDataByIndex() {
        
        if (repos == null || repos.isEmpty()) {
            System.out.println("No hay repositorios para mostrar.");
            return;
        }

        RepoResult repo = this.repos.get(this.index);

        this.nameTextF.setText("Nombre: " + repo.getName());
        this.languageTextF.setText("Lenguaje Principal: " + repo.getLanguage());
        this.stargazersTextF.setText("Estrellas: " + repo.getStargazers_count());
        this.forksTextF.setText("Forks: " + repo.getForks_count());
        this.lastUpdateTextF.setText("Última actualización: " + repo.getUpdated_at());
        this.descriptionTextF.setText("Descripción: " + repo.getDescription());
        
        this.languajesBarChart.getData().clear();

        Map<String, Integer> languajes = repo.getLanguages();

        if (languajes != null && !languajes.isEmpty()) {
            
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(repo.getName());

            for (Map.Entry<String, Integer> entry : languajes.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            this.languajesBarChart.getData().add(series);
        } else {
            System.out.println("El repositorio no tiene datos de lenguajes o no fueron cargados.");
        }

    }
}

