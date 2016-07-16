package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.Professor;

import javax.ejb.Stateless;

@Stateless
public class ProfessorRepository  extends GenericUserRepository<Professor> {
    public ProfessorRepository() {
        super(Professor.class);
    }
}
