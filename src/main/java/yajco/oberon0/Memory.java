package yajco.oberon0;

import yajco.annotation.Exclude;

@Exclude
public interface Memory {
    public String getName();
    public boolean isMutable();
}
