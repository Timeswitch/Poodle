package de.hsos.kbse.backend.model;

import org.hibernate.annotations.Parent;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by jan on 26.06.2016.
 */
@Vetoed
@Entity
@NamedQueries({
    @NamedQuery(
            name="Slot.findByStudent",
            query="SELECT s FROM Slot s WHERE s.student.id = :id"
    )
})
public class Slot extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    @OneToOne
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
        this.student = student;
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
