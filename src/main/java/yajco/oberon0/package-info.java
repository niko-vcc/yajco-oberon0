@Parser(
        mainNode = "yajco.oberon0.Module",
        skips = {@Skip("\\s"), @Skip("//.*")},
        tokens = {
                @TokenDef(name = "name", regexp = "[A-Za-z][A-Za-z0-9]*"),
                @TokenDef(name = "integer", regexp = "[0-9]+")
        },
        options = {@Option(name = "yajco.generateTools", value = "visitor")}
)
package yajco.oberon0;

import yajco.annotation.config.Option;
import yajco.annotation.config.Parser;
import yajco.annotation.config.Skip;
import yajco.annotation.config.TokenDef;
