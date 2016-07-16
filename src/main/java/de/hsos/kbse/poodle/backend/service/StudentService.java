package de.hsos.kbse.poodle.backend.service;

import de.hsos.kbse.poodle.backend.model.Slot;
import de.hsos.kbse.poodle.backend.model.Student;
import de.hsos.kbse.poodle.backend.repository.SlotRepository;
import de.hsos.kbse.poodle.backend.repository.StudentRepository;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

    public Student registerSlot(Student student, Slot s) throws Exception{

        student = this.studentRepository.reattach(student);
        s = this.slotRepository.reattach(s);

        if(s.getStudent() == null){
            s.setStudent(student);
            this.slotRepository.update(s);
        }else {
            throw new Exception("Slot ist belegt!");
        }


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
}
