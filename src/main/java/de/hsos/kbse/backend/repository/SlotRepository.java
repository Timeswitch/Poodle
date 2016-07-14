package de.hsos.kbse.backend.repository;

import de.hsos.kbse.backend.model.Slot;

import javax.ejb.Stateless;

/**
 * Created by michael on 14/07/16.
 */
@Stateless
public class SlotRepository extends GenericRepository<Slot> {
    protected SlotRepository() {
        super(Slot.class);
    }
}
