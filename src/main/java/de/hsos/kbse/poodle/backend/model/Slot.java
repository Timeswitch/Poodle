package de.hsos.kbse.poodle.backend.model;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

@Vetoed
@Entity
public class Slot extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Student student;

    @NotNull
    Date date;

    @NotNull
    Time time;

    @ManyToOne
    @NotNull
    Exam exam;

    public Long getId(){
        return this.id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {

        if(this.student != null){
            if(this.student.getSlots().contains(this)){
                this.student.getSlots().remove(this);
            }
        }

        this.student = student;

        if(student != null && !student.getSlots().contains(this)){
            student.getSlots().add(this);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setExam(Exam exam){
        this.exam = exam;
    }

    public Exam getExam(){
        return this.exam;
    }
}
