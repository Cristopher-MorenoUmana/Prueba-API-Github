package repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ProfileResult;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ProfileApiClient {
    
    private final String BASE = "https://api.github.com/users/";
    private final HttpClient http;
    private final ObjectMapper mapper;
    
    public ProfileApiClient() {

        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public ProfileResult getProfile(String pUserName) throws IOException, InterruptedException {

        final String URL = BASE + pUserName;

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {

            throw new RuntimeException("Error al obtener el perfil: " + res.statusCode());
        }

        return mapper.readValue(res.body(), ProfileResult.class);
    }
    
    public boolean userExists(String username) {
        try {
            String url = BASE + username;

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<Void> res = http.send(req, HttpResponse.BodyHandlers.discarding());

            return res.statusCode() == 200; 
        } catch (Exception e) {
            return false;
        }
    }
}