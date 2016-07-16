package de.hsos.kbse.poodle.backend.model;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Vetoed
@DiscriminatorValue("PROFESSOR")
public class Professor extends User implements Serializable{

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "professor")
    private List<Exam> exams;

    public Professor(){
    }

    public Professor(String email, String password){
        super(email, password);
    }

    public List<Exam> getExams(){
        return this.exams;
    }
}
