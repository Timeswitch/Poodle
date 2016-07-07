package de.hsos.kbse.backend.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by jan on 26.06.2016.
 */

@Entity
public class Exam implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotEmpty
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Slot> slots;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Professor professor;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<Student> students;

    public Exam(){}

    public Exam(String name){
        this.name = name;
    }

    public long getId(){
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
}
