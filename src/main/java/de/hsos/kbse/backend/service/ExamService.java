package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.model.Professor;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.repository.ExamRepository;
import de.hsos.kbse.backend.repository.ProfessorRepository;
import de.hsos.kbse.backend.repository.StudentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by michael on 07/07/16.
 */
@Stateless
public class ExamService {

    @Inject
    SessionService sessionService;

    @Inject
    StudentRepository studentRepository;

    @Inject
    ProfessorRepository professorRepository;

    @Inject
    ExamRepository examRepository;

    public Exam createExam(String name){
        Exam e = new Exam(name);
        Professor p = this.professorRepository.find(this.sessionService.getCurrentUser().getId());
        e.setProfessor(p);

        this.examRepository.add(e);

        return e;
    }

    public void deleteExam(Exam e){
        this.examRepository.remove(e);
    }

    public void addStudent(Exam e, Student s){
        e.addStudent(s);
        this.examRepository.update(e);
    }

    public void removeStudent(Exam e, Student s){
        e.removeStudent(s);
        this.examRepository.update(e);
    }

    public Collection<Exam> findExams(){

        Professor p = (Professor)this.sessionService.getCurrentUser();
        return this.examRepository.findByProfessor(p);
    }

}
