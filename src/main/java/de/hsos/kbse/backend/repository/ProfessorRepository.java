package de.hsos.kbse.backend.repository;

import de.hsos.kbse.backend.model.Professor;
import de.hsos.kbse.backend.model.Student;

/**
 * Created by michael on 07/07/16.
 */
public class ProfessorRepository  extends GenericUserRepository<Professor> {
    public ProfessorRepository() {
        super(Professor.class);
    }
}
