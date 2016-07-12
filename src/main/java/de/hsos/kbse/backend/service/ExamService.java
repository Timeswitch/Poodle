package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.model.Professor;
import de.hsos.kbse.backend.model.Slot;
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

    public Exam addStudent(Exam e, Student s){
        e = this.examRepository.reattach(e);
        e.addStudent(s);
        this.examRepository.update(e);

        return e;
    }

    public Exam addSlot(Exam e, Slot s){
        e = this.examRepository.reattach(e);
        e.addSlot(s);
        this.examRepository.update(e);

        return e;
    }

    public Exam refresh(Exam e){
        return this.examRepository.reattach(e);
    }

    public Exam removeStudent(Exam e, Student s){
        e = this.examRepository.reattach(e);
        e.removeStudent(s);
        this.examRepository.update(e);

        return e;
    }

    public Collection<Exam> findExams(){

        Professor p = (Professor)this.sessionService.getCurrentUser();
        return this.examRepository.findByProfessor(p);
    }

    public Exam findExam(Long id){
        return this.examRepository.find(id);
    }

}
