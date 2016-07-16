package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.Professor;

/**
 * Created by michael on 07/07/16.
 */
public class ProfessorRepository  extends GenericUserRepository<Professor> {
    public ProfessorRepository() {
        super(Professor.class);
    }
}
