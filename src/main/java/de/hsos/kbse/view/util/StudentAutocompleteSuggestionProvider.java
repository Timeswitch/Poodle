package de.hsos.kbse.view.util;

import com.vaadin.cdi.ViewScoped;
import de.hsos.kbse.backend.service.StudentService;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteQuery;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestion;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by michael on 12/07/16.
 */
@ViewScoped
public class StudentAutocompleteSuggestionProvider implements AutocompleteSuggestionProvider {

    @EJB
    StudentService studentService;

    @Override
    public Collection<AutocompleteSuggestion> querySuggestions(AutocompleteQuery autocompleteQuery) {
        String query = autocompleteQuery.getTerm();
        ArrayList<AutocompleteSuggestion> result = new ArrayList<>();

        if(query != null && !query.equals("")){
            this.studentService.search(query).forEach(student -> {
                AutocompleteSuggestion s = new AutocompleteSuggestion(student.getEmail());
                result.add(s);
            });
        }

        return result;
    }
}
