package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Token;

public class Variable extends Declaration {
    @Before("VAR") @After(";")
    public Variable(String name, @Before(":") @Token("name") String type) {
        setName(name);
    }
}
