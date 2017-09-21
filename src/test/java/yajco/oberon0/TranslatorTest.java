package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.Module;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

public class TranslatorTest {
    private LALRModuleParser parser;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
    }

    @Test
    public void emptyModule() throws ParseException {
        assertThat(translate("MODULE Empty; END Empty."),
                equalToIgnoringWhiteSpace("void main() { }"));
    }

    @Test
    public void oneVariable() throws ParseException {
        assertThat(translate("MODULE Test; VAR x: INTEGER; END Test."),
                equalToIgnoringWhiteSpace("void main() { int x; }"));
    }

    @Test
    public void oneAssignment() throws ParseException {
        assertThat(translate("MODULE Test; VAR x: INTEGER; BEGIN x := 1 END Test."),
                equalToIgnoringWhiteSpace("void main() { int x; x = 1; }"));
    }

    @Test
    public void simpleExpression() throws ParseException {
        assertThat(translate("MODULE Test; VAR x: INTEGER; BEGIN x := x * (5 + 3) END Test."),
                equalToIgnoringWhiteSpace("void main() { int x; x = (x * (5 + 3)); }"));
    }

    @Test
    public void twoVariables() throws ParseException {
        assertThat(translate("MODULE Test; VAR x, y: INTEGER; BEGIN\n"
                        + "  x := 6 DIV 3;\n"
                        + "  y := 10 MOD x\n"
                        + "END Test."),
                equalToIgnoringWhiteSpace("void main() { int x; int y;"
                        + "  x = (6 / 3); y = (10 % x); }"));
    }

    @Test
    public void smallProgram() throws ParseException {
        assertThat(translate("MODULE Multiply;\n"
                        + "  VAR x, y, z: INTEGER;\n"
                        + "BEGIN x := 5; y := 7; z := 0;\n"
                        + "  WHILE x > 0 DO\n"
                        + "    IF x MOD 2 = 1 THEN z := z + y END ;\n"
                        + "    y := 2*y; x := x DIV 2\n"
                        + "  END\n"
                        + "END Multiply."),
                equalToIgnoringWhiteSpace("void main() {\n"
                        + "  int x; int y; int z;\n"
                        + "  x = 5; y = 7; z = 0;\n"
                        + "  while ((x > 0)) {\n"
                        + "    if (((x % 2) == 1)) { z = (z + y); }\n"
                        + "    y = (2 * y); x = (x / 2);\n"
                        + "  }\n"
                        + "}\n"));
    }

    private String translate(String input) throws ParseException {
        Module module = parser.parse(input);
        NamesResolver.resolve(module);
        StringWriter writer = new StringWriter();
        CCodeGenerator.generate(module, new PrintWriter(writer));
        return writer.toString();
    }
}
