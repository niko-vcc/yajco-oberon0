package yajco.oberon0.model.l4;

import yajco.annotation.Range;
import yajco.oberon0.model.Reference;

import java.util.List;

public class ReferenceWithSelector extends Reference {

    private List<Selector> selectors;

    public ReferenceWithSelector(
            String name,
            @Range(minOccurs = 1) List<Selector> selectors) {
        super(name);
        this.selectors = selectors;
    }

    public List<Selector> getSelectors() {
        return selectors;
    }
}
