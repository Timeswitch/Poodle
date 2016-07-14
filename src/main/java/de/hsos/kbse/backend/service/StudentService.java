package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.model.Slot;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.repository.SlotRepository;
import de.hsos.kbse.backend.repository.StudentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by michael on 12/07/16.
 */
@Stateless
public class StudentService {

    @Inject
    StudentRepository studentRepository;

    @Inject
    SlotRepository slotRepository;

    public Student refresh(Student s){
        return this.studentRepository.reattach(s);
    }

    public Collection<Student> search(String query){
        return this.studentRepository.search(query);
    }

    public Student registerSlot(Student student, Slot s){

        student = this.studentRepository.reattach(student);
        s = this.slotRepository.reattach(s);

        s.setStudent(student);

        this.slotRepository.update(s);

        return student;
    }

    public Student freeSlot(Student student, Slot s){
        student = this.studentRepository.reattach(student);
        s = this.slotRepository.reattach(s);

        if(s.getStudent().equals(student)){
            s.setStudent(null);
            this.slotRepository.update(s);
        }

        return student;
    }

    public Collection<Slot> findRegisteredSlots(Student s){
        return this.slotRepository.findByStudent(s);
    }


}
