package yajco.oberon0.model.l4;

import yajco.annotation.Before;
import yajco.annotation.Range;
import yajco.oberon0.model.Assignment;
import yajco.oberon0.model.Expression;

import java.util.List;

public class AssignmentWithSelector extends Assignment {
    private final List<Selector> selectors;

    public AssignmentWithSelector(
            String name,
            @Range(minOccurs = 1) List<Selector> selectors,
            @Before(":=") Expression expression) {
        super(name, expression);
        this.selectors = selectors;
    }

    public List<Selector> getSelectors() {
        return selectors;
    }
}
