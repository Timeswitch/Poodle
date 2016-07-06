package de.hsos.kbse.backend.repository;

import de.hsos.kbse.backend.model.Student;

import javax.ejb.Stateless;

/**
 * Created by michael on 26/06/16.
 */
@Stateless
public class StudentRepository extends GenericUserRepository<Student> {

    public StudentRepository(){
        super(Student.class);
    }
}
