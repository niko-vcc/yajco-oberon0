@Parser(
        mainNode = "yajco.oberon0.model.Module",
        skips = {@Skip("\\s"), @Skip("//.*")},
        tokens = {
                @TokenDef(name = "name", regexp = "[A-Za-z][A-Za-z0-9]*"),
                @TokenDef(name = "integer_t", regexp = "[0-9]+")
        },
        options = {@Option(name = "yajco.generateTools", value = "prettyprinter")}
)
package yajco.oberon0.model;

import yajco.annotation.config.Option;
import yajco.annotation.config.Parser;
import yajco.annotation.config.Skip;
import yajco.annotation.config.TokenDef;
