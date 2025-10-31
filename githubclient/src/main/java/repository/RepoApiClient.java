package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.RepoResult;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class RepoApiClient {

    private final String BASE = "https://api.github.com/users/";
    private final HttpClient http;
    private final ObjectMapper mapper;

    public RepoApiClient() {

        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<RepoResult> getRepos(String pUserName) throws IOException, InterruptedException {

        final String URL = BASE + pUserName + "/repos?per_page=100&sort=updated";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(URL)).GET().timeout(Duration.ofSeconds(10)).build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {

            throw new RuntimeException("Error al obtener los repositorios: " + res.statusCode());
        }

        return mapper.readValue(res.body(), new TypeReference<List<RepoResult>>() {
        });
    }

    public void getLanguages(String username, List<RepoResult> repos) throws IOException, InterruptedException {

        for (RepoResult r : repos) {
            String langUrl = "https://api.github.com/repos/" + username + "/" + r.getName() + "/languages";

            HttpRequest langReq = HttpRequest.newBuilder()
                    .uri(URI.create(langUrl))
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> res = http.send(langReq, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200) {
                Map<String, Integer> langs = mapper.readValue(res.body(),
                        new TypeReference<Map<String, Integer>>() {
                });
                r.setLanguages(langs);
            }
        }
    }
}