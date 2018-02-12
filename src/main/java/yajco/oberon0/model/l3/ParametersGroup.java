package yajco.oberon0.model.l3;

import yajco.annotation.Before;
import yajco.annotation.FactoryMethod;
import yajco.annotation.Separator;
import yajco.oberon0.model.Type;

import java.util.List;

public class ParametersGroup {
    private List<Parameter> parameters;
    private Type type;

    public ParametersGroup(
            @Separator(",") List<Parameter> parameters,
            @Before(":") Type type) {
        this.parameters = parameters;
        this.type = type;
        for (Parameter parameter: parameters) {
            parameter.setType(type);
        }
    }

    @Before("VAR") @FactoryMethod
    static public ParametersGroup variableParameters(
            @Separator(",") List<Parameter> parameters,
            @Before(":") Type type) {
        parameters.forEach(parameter -> parameter.setVariable(true));
        return new ParametersGroup(parameters, type);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Type getType() {
        return type;
    }
}
