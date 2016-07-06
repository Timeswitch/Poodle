package de.hsos.kbse.backend.model;

import de.hsos.kbse.backend.security.Role;

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
public abstract class User implements Model{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    protected String email;

    protected String password;

    @Enumerated(EnumType.STRING)
    protected Role role;

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
