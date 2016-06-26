package de.hsos.kbse.backend.Repository;

import de.hsos.kbse.backend.model.Student;

/**
 * Created by michael on 26/06/16.
 */
public class StudentRepository extends GenericRepository<Student> {

    public StudentRepository(){
        super(Student.class);
    }
}
