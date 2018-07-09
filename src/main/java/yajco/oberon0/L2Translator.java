package yajco.oberon0;

import yajco.oberon0.model.Module;
import yajco.oberon0.model.parser.ParseException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class L2Translator extends L1Translator {
    public static void main(String[] args) throws IOException, ParseException {
        new L2Translator().run(args);
    }

    @Override
    protected List<ParserError> checkTypes(Module module) {
        return L2TypeChecker.check(module);
    }

    @Override
    protected List<ParserError> transformTree(Module module) {
        L2Transformation.transform(module);
        return Collections.emptyList();
    }
}
