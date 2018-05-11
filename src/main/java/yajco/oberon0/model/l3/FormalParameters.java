package yajco.oberon0.model.l3;

import yajco.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class FormalParameters extends ArrayList<Parameter> {
    public FormalParameters() {
    }

    @Before("(") @After(")")
    public FormalParameters(
            @Range(minOccurs = 1) @Separator(";") List<ParametersGroup> groups) {
        for (ParametersGroup group : groups) {
            addAll(group.getParameters());
        }
    }

    @Exclude
    public FormalParameters(Collection<Parameter> parameters) {
        addAll(parameters);
    }

    public List<ParametersGroup> getGroups() {
        return stream()
                .map(p -> new ParametersGroup(singletonList(p), p.getType()))
                .collect(Collectors.toList());
    }
}
