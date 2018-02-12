package yajco.oberon0.model.l3;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Range;
import yajco.annotation.Separator;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Statement;

import java.util.List;

public class ProcedureCall extends Statement {
    private String name;
    private List<Expression> actualParameters;

    public ProcedureCall(String name) {
        this.name = name;
    }

    public ProcedureCall(String name,
                         @Before("(") @After(")") @Separator(",") @Range(minOccurs = 1)
                                 List<Expression> actualParameters) {
        this.name = name;
        this.actualParameters = actualParameters;
    }

    public String getName() {
        return name;
    }

    public List<Expression> getActualParameters() {
        return actualParameters;
    }
}
