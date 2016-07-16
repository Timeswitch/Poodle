package de.hsos.kbse.poodle.backend.security;

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
