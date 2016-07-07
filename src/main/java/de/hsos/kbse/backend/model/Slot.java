package de.hsos.kbse.backend.model;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by jan on 26.06.2016.
 */
@Embeddable
@Vetoed
public class Slot {

    @OneToOne
    @NotNull
    Student student;

    @NotNull
    Date date;

    @NotNull
    Time time;

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
}
