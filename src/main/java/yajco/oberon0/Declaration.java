package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;

public abstract class Declaration {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
