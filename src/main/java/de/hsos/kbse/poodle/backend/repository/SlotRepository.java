package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.Slot;
import de.hsos.kbse.poodle.backend.model.Student;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Stateless
public class SlotRepository extends GenericRepository<Slot> {
    protected SlotRepository() {
        super(Slot.class);
    }

    public Collection<Slot> findByStudent(Student s){
        TypedQuery<Slot> q = this.em.createNamedQuery("Slot.findByStudent",Slot.class);
        q.setParameter("id",s.getId());

        return q.getResultList();
    }
}
