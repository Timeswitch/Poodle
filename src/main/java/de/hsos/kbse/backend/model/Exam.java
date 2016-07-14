package de.hsos.kbse.backend.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jan on 26.06.2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name="Exam.findByProfessor",
                query="SELECT e FROM Exam e WHERE e.professor = :professor")
})
public class Exam extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam", fetch = FetchType.EAGER)
    private List<Slot> slots = new ArrayList<>();

    @ManyToOne
    private Professor professor;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<Student> students = new ArrayList<>();

    public Exam(){}

    public Exam(String name){
        this.name = name;
    }

    @Override
    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Slot> getSlots(){
        return this.slots;
    }

    public void setSlots(List<Slot> slots){
        this.slots = slots;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void addStudent(Student e){
        if(!this.getStudents().contains(e)){
            this.getStudents().add(e);
        }

        if(!e.getExams().contains(this)){
            e.getExams().add(this);
        }
    }

    public void addSlot(Slot s){
        if(!this.getSlots().contains(s)){
            this.getSlots().add(s);
        }

        s.setExam(this);
    }

    public void removeStudent(Student e){
        this.students.remove(e);
    }
}
