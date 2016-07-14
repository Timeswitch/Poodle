package de.hsos.kbse.backend.model;

import de.hsos.kbse.backend.security.Role;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ManyToAny;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by jan on 26.06.2016.
 */

@Entity
@Vetoed
@DiscriminatorValue("STUDENT")
public class Student extends User{

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private Set<Exam> exams;

    public Student(){
        super();
//        this.role = Role.STUDENT;
    }

    public Student(String email, String password){
        super(email, password);
    }

    public Collection<Exam> getExams(){
        return this.exams;
    }
}
