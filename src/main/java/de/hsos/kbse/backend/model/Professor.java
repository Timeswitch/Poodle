package de.hsos.kbse.backend.model;

import de.hsos.kbse.backend.security.Role;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jan on 26.06.2016.
 */

@Entity
@Vetoed
public class Professor extends User implements Serializable{

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "professor")
    private List<Exam> exams;

    public Professor(){
        super();
        this.role = Role.PROFESSOR;
    }

    public Professor(String email, String password){
        super(email, password);
    }

    public List<Exam> getExams(){
        return this.exams;
    }
}
