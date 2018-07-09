package yajco.oberon0

import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import yajco.oberon0.model.Assignment
import yajco.oberon0.model.Declaration
import yajco.oberon0.model.l3.DeclarationsWithProcedures
import yajco.oberon0.model.l3.Procedure
import yajco.oberon0.model.l3.ProcedureCall
import yajco.oberon0.model.parser.LALRModuleParser
import yajco.oberon0.model.parser.ParseException
import java.io.PrintWriter
import java.io.StringWriter

class L3Test {
    private var parser: LALRModuleParser? = null

    @Before
    fun createParser() {
        parser = LALRModuleParser()
    }

    @Test
    fun unParametrizedProcedureCall() {
        val module = parser!!.parse("MODULE Test; BEGIN Hello END Test.")
        val call = module.statements[0] as ProcedureCall
        assertThat(call.name, equalTo("Hello"))
    }

    @Test
    fun parametrizedProcedureCall() {
        val module = parser!!.parse("MODULE Test; BEGIN Multiply(2, 4) END Test.")
        val statement = module.statements[0]
        val call = statement as ProcedureCall
        assertThat(call.actualParameters.size, equalTo(2))
    }

    @Test
    fun emptyProcedure() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  PROCEDURE Hello;
                  |  END Hello;
                  |END Test.""".trimMargin())
        val declarations = module.declarations as DeclarationsWithProcedures
        assertThat(declarations.procedures.size, equalTo(1))
        assertThat(declarations.procedures[0].name, equalTo("Hello"))
    }

    @Test
    fun gettingProcedureByName() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  PROCEDURE Hello;
                  |  END Hello;
                  |END Test.""".trimMargin())
        val declarations = module.declarations as DeclarationsWithProcedures
        assertThat(declarations["Hello"], notNullValue())
    }

    @Test
    fun procedureWithStatements() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  PROCEDURE Hello;
                  |  BEGIN
                  |    x := 1;
                  |    y := 2
                  |  END Hello;
                  |END Test.""".trimMargin())
        val declarations = module.declarations as DeclarationsWithProcedures
        val procedure = declarations.procedures[0]
        assertThat(procedure.statements.size, equalTo(2))
    }

    @Test
    fun procedureWithDeclarations() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  PROCEDURE Hello;
                  |    CONST a = 0;
                  |    VAR x, y: INTEGER;
                  |  END Hello;
                  |END Test.""".trimMargin())
        val declarations = module.declarations as DeclarationsWithProcedures
        val procedure = declarations.procedures[0]
        assertThat(procedure.declarations.size, equalTo(3))
    }

    @Test
    fun procedureWithParameters() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  PROCEDURE Multiply (x, y: INTEGER; VAR z: INTEGER);
                  |  BEGIN
                  |    z := x * y
                  |  END Multiply;
                  |END Test.""".trimMargin())
        val declarations = module.declarations as DeclarationsWithProcedures
        val parameters = declarations.procedures[0].parameters
        assertThat(parameters.size, equalTo(3))
        assertThat(parameters[0].name, equalTo("x"))
        assertThat(parameters[0].isVariable, equalTo(false))
        assertThat(parameters[1].name, equalTo("y"))
        assertThat(parameters[1].isVariable, equalTo(false))
        assertThat(parameters[2].name, equalTo("z"))
        assertThat(parameters[2].isVariable, equalTo(true))
    }

    @Test
    fun resolutionOfProcedureName() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  PROCEDURE Hello;
                  |  END Hello;
                  |
                  |BEGIN
                  |  Hello
                  |END Test.""".trimMargin())
        val errors = L3NamesResolver.resolve(module)
        val procedure = module.declarations["Hello"]
        assertThat(procedure?.name, equalTo("Hello"))  // Just to be sure
        val statement = module.statements[0]
        assertThat((statement as ProcedureCall).procedure, equalTo(procedure))
        assertThat(errors, equalTo(emptyList()))
    }

    @Test
    fun nameResolutionInProcedure() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  CONST a = 5;
                  |  VAR b : INTEGER;
                  |  PROCEDURE Multiply (x, y: INTEGER; VAR z: INTEGER);
                  |  BEGIN
                  |    z := x * y
                  |  END Multiply;
                  |BEGIN
                  |  Multiply(a, 10, b)
                  |END Test.""".trimMargin())
        val errors = L3NamesResolver.resolve(module)
        assertThat(errors, equalTo(emptyList()))
        val procedure = module.declarations["Multiply"] as Procedure
        val assignment = procedure.statements[0] as Assignment
        assertThat(assignment.variable, equalTo(procedure.parameters[2] as Declaration))
    }

    @Test
    fun builtinProcedures() {
        val module = parser!!.parse(
                """MODULE Test;
                  |BEGIN
                  |  WriteLn
                  |END Test.""".trimMargin())
        val errors = L3NamesResolver.resolve(module)
        assertThat(errors, equalTo(emptyList()))
    }

    @Test
    fun liftNestedProcedures() {
        val module = parser!!.parse(
                """MODULE Foo;
                  |  PROCEDURE Bar;
                  |    PROCEDURE Baz;
                  |    END Baz;
                  |  BEGIN
                  |    Baz
                  |  END Bar;
                  |BEGIN
                  |  Bar
                  |END Foo.""".trimMargin())
        L3NamesResolver.resolve(module)
        L3Transformation.liftProcedures(module)

        assertThat(module.declarations, hasKey("BarBaz"))
        assertThat((module.declarations["Bar"] as Procedure).declarations, not(hasKey("Baz")))
    }

    @Test
    fun typeCheckProcedure() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  CONST a = 5;
                  |  VAR b : INTEGER;
                  |  PROCEDURE Multiply (x, y: INTEGER; VAR z: INTEGER);
                  |  BEGIN
                  |    z := x * y
                  |  END Multiply;
                  |BEGIN
                  |  Multiply(a, 10, b)
                  |END Test.""".trimMargin())
        L3NamesResolver.resolve(module)
        val errors = L3TypeChecker.check(module)
        assertThat(errors, equalTo(emptyList()))
    }

    @Test
    fun typeCheckInvalidNumberOfParameters() {
        val module = parser!!.parse(
                """MODULE Test;
                  |BEGIN
                  |  WriteLn(10)
                  |END Test.""".trimMargin())
        L3NamesResolver.resolve(module)
        val errors = L3TypeChecker.check(module)
        assertThat(errors, hasSize(1))
    }

    @Test
    fun typeCheckInvalidParameterType() {
        val module = parser!!.parse(
                """MODULE Test;
                  |BEGIN
                  |  Write(TRUE)
                  |END Test.""".trimMargin())
        L3NamesResolver.resolve(module)
        val errors = L3TypeChecker.check(module)
        assertThat(errors, hasSize(1))
    }

    @Test
    fun translateProceduresToC() {
        assertThat(translate(
                """MODULE Test;
                  |  CONST a = 5;
                  |  VAR b, c : INTEGER;
                  |  PROCEDURE Multiply (x, y: INTEGER; VAR z: INTEGER);
                  |  BEGIN
                  |    z := x * y
                  |  END Multiply;
                  |BEGIN
                  |  Read(b);
                  |  Multiply(a, b, c);
                  |  Write(c);
                  |  WriteLn;
                  |  WriteHex(c);
                  |  WriteLn
                  |END Test.""".trimMargin()),
            equalToIgnoringWhiteSpace(
                """#include "oberon.h"
                  |
                  |void Multiply(int x, int y, int *z) {
                  |  *z = (x * y);
                  |}
                  |
                  |void main() {
                  |  const int a = 5;
                  |  int b;
                  |  int c;
                  |  Read(&b);
                  |  Multiply(a, b, &c);
                  |  Write(c);
                  |  WriteLn();
                  |  WriteHex(c);
                  |  WriteLn();
                  |}
                """.trimMargin()))
    }

    @Throws(ParseException::class)
    private fun translate(input: String): String {
        val module = parser!!.parse(input)
        L3NamesResolver.resolve(module)
        L3Transformation.liftProcedures(module)
        val writer = StringWriter()
        L3CodeGenerator.generate(module, PrintWriter(writer))
        return writer.toString()
    }
}
