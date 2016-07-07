package de.hsos.kbse.backend.repository;

import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.model.Professor;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * Created by michael on 07/07/16.
 */
@Stateless
public class ExamRepository extends GenericRepository<Exam> {

    public ExamRepository(){
        super(Exam.class);
    }

    @Override
    public boolean add(Exam entity) {

        this.em.refresh(entity.getProfessor());
        return super.add(entity);
    }

    public Collection<Exam> findByProfessor(Professor p){

        TypedQuery<Exam> query = this.em.createNamedQuery("Exam.findByProfessor",this.type);
        query.setParameter("professor", p);
        Collection<Exam> res = query.getResultList();

        return res;
    }
}
