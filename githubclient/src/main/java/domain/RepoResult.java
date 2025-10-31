package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoResult {

    public String name;
    public String updated_at;
    public String description;
    public String language;
    public long stargazers_count;
    public long forks_count;

    public Map<String, Integer> languages;

    public RepoResult() {}

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLanguage() { return language; }
    public long getStargazers_count() { return stargazers_count; }
    public long getForks_count() { return forks_count; }
    public String getUpdated_at() { return updated_at; }

    public Map<String, Integer> getLanguages() { return languages; }
    public void setLanguages(Map<String, Integer> languages) { this.languages = languages; }
}