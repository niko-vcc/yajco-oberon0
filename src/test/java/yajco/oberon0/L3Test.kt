package yajco.oberon0

import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import yajco.oberon0.model.l3.DeclarationsWithProcedures
import yajco.oberon0.model.l3.ProcedureCall
import yajco.oberon0.model.parser.LALRModuleParser

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
}
