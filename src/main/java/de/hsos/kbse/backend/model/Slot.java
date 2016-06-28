package de.hsos.kbse.backend.model;

import javax.persistence.Embeddable;

/**
 * Created by jan on 26.06.2016.
 */
@Embeddable
public class Slot {

    String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
