package yajco.oberon0;

import yajco.oberon0.model.Module;
import yajco.oberon0.model.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

public class L3Translator extends L2Translator {
    public static void main(String[] args) throws IOException, ParseException {
        new L3Translator().run(args);
    }

    @Override
    protected List<ParserError> resolveNames(Module module) {
        return L3NamesResolver.resolve(module);
    }

    @Override
    protected List<ParserError> checkTypes(Module module) {
        return L3TypeChecker.check(module);
    }

    @Override
    protected List<ParserError> transformTree(Module module) {
        super.transformTree(module);
        L3Transformation.liftProcedures(module);
        return Collections.emptyList();
    }

    @Override
    protected void generateCode(Module module, Writer output) {
        L3CodeGenerator.generate(module, new PrintWriter(output));
    }
}
