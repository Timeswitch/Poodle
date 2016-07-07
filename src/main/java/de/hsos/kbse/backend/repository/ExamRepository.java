package de.hsos.kbse.backend.repository;

import de.hsos.kbse.backend.model.Exam;

/**
 * Created by michael on 07/07/16.
 */
public class ExamRepository extends GenericRepository<Exam> {

    public ExamRepository(){
        super(Exam.class);
    }
}
