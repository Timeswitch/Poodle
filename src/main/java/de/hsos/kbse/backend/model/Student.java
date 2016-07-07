package de.hsos.kbse.backend.model;

import de.hsos.kbse.backend.security.Role;
import org.hibernate.annotations.ManyToAny;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jan on 26.06.2016.
 */

@Entity
@Vetoed
public class Student extends User{

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST)
    private List<Exam> exams;

    public Student(){
        super();
        this.role = Role.STUDENT;
    }

    public Student(String email, String password){
        super(email, password);
    }

    public List<Exam> getExams(){
        return this.exams;
    }
}
