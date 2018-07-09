package yajco.oberon0;

import yajco.oberon0.model.Module;
import yajco.oberon0.model.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class L4Translator extends L3Translator {
    public static void main(String[] args) throws IOException, ParseException {
        new L4Translator().run(args);
    }

    @Override
    protected List<ParserError> checkTypes(Module module) {
        return L4TypeChecker.check(module);
    }

    @Override
    protected void generateCode(Module module, Writer output) {
        L4CodeGenerator.generate(module, new PrintWriter(output));
    }
}
