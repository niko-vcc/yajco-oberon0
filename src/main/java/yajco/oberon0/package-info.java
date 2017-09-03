@Parser(
        mainNode = "yajco.oberon0.Module",
        skips = {@Skip("\\s"), @Skip("//.*")},
        tokens = @TokenDef(name = "name", regexp = "[A-Za-z][A-Za-z0-9]*")
)
package yajco.oberon0;

import yajco.annotation.config.Parser;
import yajco.annotation.config.Skip;
import yajco.annotation.config.TokenDef;
