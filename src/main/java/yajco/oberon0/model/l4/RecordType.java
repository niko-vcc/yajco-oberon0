package yajco.oberon0.model.l4;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Range;
import yajco.annotation.Separator;
import yajco.oberon0.model.Type;
import yajco.oberon0.model.Variable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class RecordType extends Type {
    Map<String, Variable> fields = new LinkedHashMap<>();

    @Before("RECORD") @After("END")
    public RecordType(@Range(minOccurs = 1) @Separator(";") List<FieldList> fieldLists) {
        for (FieldList group: fieldLists) {
            for (Variable variable: group.getVariables()) {
                fields.put(variable.getName(), variable);
            }
        }
    }

    public List<FieldList> getFieldLists() {
        return fields.values().stream()
                .map(v -> new FieldList(singletonList(v), v.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean matches(Type that) {
        return false;
    }
}
