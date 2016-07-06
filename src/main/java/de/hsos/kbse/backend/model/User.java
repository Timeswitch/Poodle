package de.hsos.kbse.backend.model;

import javax.persistence.*;

/**
 * Created by jan on 25.06.2016.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="POODLE_USER")
@NamedQueries({
        @NamedQuery(name="User.findByEmail",
                    query="SELECT u FROM User u WHERE u.email = :email")
})
public class User implements Model{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

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
