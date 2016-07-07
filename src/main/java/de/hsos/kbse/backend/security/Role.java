package de.hsos.kbse.backend.security;

/**
 * Created by michael on 06/07/16.
 */
public enum Role {
    PROFESSOR("Professor"),
    STUDENT("Student"),
    GUEST("Guest");

    private String name;

    private Role(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
