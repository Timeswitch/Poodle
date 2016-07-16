package de.hsos.kbse.poodle.backend.model;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Vetoed
@DiscriminatorValue("STUDENT")
public class Student extends User{

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private Collection<Exam> exams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @OrderBy("exam, date, time")
    private Collection<Slot> slots;

    public Student(String email, String password){
        super(email, password);
    }

    public Student() {
    }

    public Collection<Exam> getExams(){
        return this.exams;
    }

    public Collection<Slot> getSlots() {
        return slots;
    }

    public boolean hasSlotInExam(Exam e){
       return this.getSlots().stream().anyMatch(slot -> e.equals(slot.getExam()));
    }
}
