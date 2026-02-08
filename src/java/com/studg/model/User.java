package com.studg.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private String username;
    private String password;
    private int points = 0;
    private int plays = 0;
    private Set<String> learnedSubjects = new HashSet<>();
    private Set<String> profileItems = new HashSet<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    public int getPoints() { 
        return points; 
    }
    public void setPoints(int points) { 
        this.points = points; 
    }
    public int getPlays() { 
        return plays; 
    }
    public void setPlays(int plays) { 
        this.plays = plays; 
    }
    public Set<String> getLearnedSubjects() { 
        return learnedSubjects; 
    }
    public void addSubject(String s) { 
        learnedSubjects.add(s); 
    }
    public Set<String> getProfileItems() {
        return profileItems;
    }
    public void addItem(String itemKey) {
        profileItems.add(itemKey);
    }
    public void removeItem(String itemKey) { profileItems.remove(itemKey); }
}
