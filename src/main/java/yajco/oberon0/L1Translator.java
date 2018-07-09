package yajco.oberon0;

import yajco.oberon0.model.Module;
import yajco.oberon0.model.operators.Mod;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class L1Translator {
    public static void main(String[] args) throws IOException, ParseException {
        new L1Translator().run(args);
    }

    public void run(String[] args) throws IOException, ParseException {
        if (args.length < 1) {
            System.err.println("Error: no input file");
            System.exit(1);
        }
        FileReader input = new FileReader(args[0]);
        String outputFileName = outputFileName(args[0]);
        FileWriter output = new FileWriter(outputFileName);
        translate(input, output);
        output.close();
    }

    private static String outputFileName(String inputFileName) {
        return inputFileName + ".c";
    }

    protected void translate(Reader input, Writer output) throws ParseException {
        Module module = parse(input);

        List<ParserError> errors = resolveNames(module);
        if (!errors.isEmpty()) printErrorsAndExit(errors);

        errors = checkTypes(module);
        if (!errors.isEmpty()) printErrorsAndExit(errors);

        errors = transformTree(module);
        if (!errors.isEmpty()) printErrorsAndExit(errors);

        generateCode(module, output);
    }

    protected Module parse(Reader input) throws ParseException {
        LALRModuleParser parser = new LALRModuleParser();
        return parser.parse(input);
    }

    protected List<ParserError> resolveNames(Module module) {
        return NamesResolver.resolve(module);

    }

    protected List<ParserError> checkTypes(Module module) {
        return TypeChecker.check(module);
    }

    protected List<ParserError> transformTree(Module module) {
        return Collections.emptyList();
    }

    protected void generateCode(Module module, Writer output) {
        CCodeGenerator.generate(module, new PrintWriter(output));
    }

    protected static void printErrorsAndExit(List<ParserError> errors) {
        for (ParserError error: errors) {
            System.err.print(error);
        }
        System.exit(1);
    }
}
