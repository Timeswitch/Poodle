package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.Student;

import javax.ejb.Stateless;

@Stateless
public class StudentRepository extends GenericUserRepository<Student> {

    public StudentRepository(){
        super(Student.class);
    }


}
