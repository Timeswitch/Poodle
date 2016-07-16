package de.hsos.kbse.poodle.backend.service;

import de.hsos.kbse.poodle.backend.model.Exam;
import de.hsos.kbse.poodle.backend.model.Professor;
import de.hsos.kbse.poodle.backend.model.Slot;
import de.hsos.kbse.poodle.backend.model.Student;
import de.hsos.kbse.poodle.backend.repository.ExamRepository;
import de.hsos.kbse.poodle.backend.repository.ProfessorRepository;
import de.hsos.kbse.poodle.backend.repository.SlotRepository;
import de.hsos.kbse.poodle.backend.repository.StudentRepository;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

    @Inject
    SlotRepository slotRepository;

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

    public Exam addSlot(Exam e, Slot s){
        e = this.examRepository.reattach(e);
        e.addSlot(s);

        this.slotRepository.add(s);
        return this.examRepository.update(e);
    }

    public Exam addStudent(Exam e, Student s) throws Exception{
        e = this.examRepository.reattach(e);
        s = this.studentRepository.reattach(s);

        if(s == null){
            throw new Exception("Student existiert nicht!");
        }

        e.addStudent(s);
        this.examRepository.update(e);

        return e;
    }

    public Exam addStudent(Exam e, String email) throws Exception {
        Student s = this.studentRepository.findByEmail(email);
        return this.addStudent(e,s);
    }

    public Exam removeStudent(Exam e, Student s){
        e = this.examRepository.reattach(e);
        e.removeStudent(s);
        this.examRepository.update(e);

        return e;
    }

    public Exam freeSlot(Exam e, Slot s){
        s = this.slotRepository.reattach(s);
        s.setStudent(null);

        return this.examRepository.reattach(e);
    }

    public Collection<Exam> findExams(){ //todo lieber reattach?

        Professor p = (Professor)this.sessionService.getCurrentUser();
        return this.examRepository.findByProfessor(p);
    }

    public Exam findExam(Long id){
        return this.examRepository.find(id);
    }

    public Exam refresh(Exam e){
        return this.examRepository.reattach(e);
    }

}
