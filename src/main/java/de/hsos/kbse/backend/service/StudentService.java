package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.model.Slot;
import de.hsos.kbse.backend.model.Student;
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

    public Collection<Student> search(String query){
        return this.studentRepository.search(query);
    }

    public Student registerSlot(Student student, Slot s){


        return student;
    }


}
