package de.hsos.kbse.backend.model;

import de.hsos.kbse.backend.security.Role;

import javax.persistence.*;

/**
 * Created by jan on 25.06.2016.
 */

@Entity
@Inheritance
@DiscriminatorColumn(name = "role")
@Table(name="POODLE_USER")
@NamedQueries({
        @NamedQuery(name="User.findByEmail",
                    query="SELECT u FROM User u WHERE u.email = :email AND (:type IS null OR u.role = :type)"),
        @NamedQuery(name="User.search",
                    query="SELECT u FROM User u WHERE u.email LIKE :query AND (:type IS null OR u.role = :type)")
})
public abstract class User extends Model{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    protected String email;

    protected String password;

//    @Enumerated(EnumType.STRING)
//    protected Role role;

    @Column(name = "role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){}

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
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

    public Role getRole() {
        return role;
    }

    public static User make(Role role){
        User u = null;

        switch(role){
            case PROFESSOR:
                u = new Professor();
                break;
            case STUDENT:
                u = new Student();
                break;
        }

        return u;
    }
}
