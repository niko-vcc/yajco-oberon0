package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;
import yajco.oberon0.model.printer.Printer;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PrinterTest {
    private LALRModuleParser parser;
    private Printer printer;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
    }

    @Before
    public void createPrinter() {
        printer = new Printer();
    }

    @Test
    public void smallProgram() throws ParseException {
        String source = "MODULE Multiply;\n"
                        + "  VAR x, y, z: INTEGER;\n"
                        + "BEGIN x := 5; y := 7; z := 0;\n"
                        + "  WHILE x > 0 DO\n"
                        + "    IF x MOD 2 = 1 THEN z := z + y END ;\n"
                        + "    y := 2*y; x := x DIV 2\n"
                        + "  END\n"
                        + "END Multiply.";
        String target = "MODULE Multiply ; VAR x, y, z : INTEGER ; BEGIN"
                + " x :=( 5); y :=( 7); z :=( 0);"
                + " WHILE(( x) >( 0)) DO"
                + " IF((( x) MOD( 2)) =( 1)) THEN z :=(( z) +( y)) END;"
                + " y :=(( 2) *( y)); x :=(( x) DIV( 2))"
                + " END END Multiply .\n";
        StringWriter writer = new StringWriter();
        printer.print(parser.parse(source), new PrintWriter(writer));
        assertThat(writer.toString(), is(target));
    }
}
