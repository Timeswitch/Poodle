package de.hsos.kbse.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jan on 25.06.2016.
 */

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String password;

    public User(){}

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public long getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
