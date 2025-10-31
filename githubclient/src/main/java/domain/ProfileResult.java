package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResult {
    
    public String avatar_url;
    public String login;
    public String bio;
    public String location;
    public String blog;
    public String created_at;
    public long following;
    public long followers;
    
    public ProfileResult() {}
    
    public String getAvatarUrl() { return this.avatar_url; }
    public String getLogin(){ return this.login; }
    public String getBio() { return this.bio; }
    public String getLocation() { return this.location; }
    public String getBlog() { return this.blog; }
    public String getCreationDate() { return this.created_at; }
    public long getFollowing() { return this.following; }
    public long getFollowers() { return this.followers; }  
}
