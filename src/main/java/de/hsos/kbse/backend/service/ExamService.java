package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.model.Professor;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.repository.ExamRepository;
import de.hsos.kbse.backend.repository.StudentRepository;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Date;

/**
 * Created by michael on 07/07/16.
 */
@Stateless
@DeclareRoles({"PROFESSOR"})
public class ExamService {

    @Inject
    SessionService sessionService;

    @Inject
    StudentRepository studentRepository;

    @Inject
    ExamRepository examRepository;

    public Exam createExam(String name){
        Exam e = new Exam(name);
        e.setProfessor((Professor)sessionService.getCurrentUser());

        this.examRepository.add(e);

        return e;
    }

    public void addStudent(Exam e, Student s){
        e.addStudent(s);
        this.examRepository.update(e);
    }

    public void removeStudent(Exam e, Student s){
        e.removeStudent(s);
        this.examRepository.update(e);
    }

}
