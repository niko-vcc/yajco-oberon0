package yajco.oberon0

import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import yajco.oberon0.model.*
import yajco.oberon0.model.Number
import yajco.oberon0.model.l3.ProcedureCall
import yajco.oberon0.model.l4.*
import yajco.oberon0.model.parser.LALRModuleParser
import java.io.PrintWriter
import java.io.StringWriter

class L4Test {
    private var parser: LALRModuleParser? = null

    @Before
    fun createParser() {
        parser = LALRModuleParser()
    }

    @Test
    fun arrayDefinition() {
        val module = parser!!.parse("MODULE Test; VAR a: ARRAY 32 OF INTEGER; END Test.")
        val arrayVar = module.declarations["a"] as Variable
        assertThat(arrayVar.type, instanceOf(ArrayType::class.java))
        val arrayType = arrayVar.type as ArrayType
        assertThat(arrayType.size, instanceOf(Number::class.java))
        assertThat((arrayType.elementType as TypeReference).name, equalTo("INTEGER"))
    }

    @Test
    fun recordDefinition() {
        val module = parser!!.parse("MODULE Test; VAR r: RECORD a, b: INTEGER; c: BOOLEAN END; END Test.")
        val recordVar = module.declarations["r"] as Variable
        assertThat(recordVar.type, instanceOf(RecordType::class.java))
        val recordType = recordVar.type as RecordType
        assertThat(recordType.fields.size, equalTo(3))
    }

    @Test
    fun arrayIndexingInReference() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR a: ARRAY 32 OF INTEGER;
                  |BEGIN
                  |  Write(a[0])
                  |END Test.""".trimMargin())
        val procedureCall = module.statements[0] as ProcedureCall
        assertThat(procedureCall.actualParameters[0], instanceOf(ReferenceWithSelector::class.java))
        val reference = procedureCall.actualParameters[0] as ReferenceWithSelector
        assertThat(reference.selectors[0], instanceOf(IndexSelector::class.java))
    }

    @Test
    fun fieldSelectorInReference() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR r: RECORD a, b: INTEGER; c: BOOLEAN END;
                  |BEGIN
                  |  Write(r.a)
                  |END Test.""".trimMargin())
        val procedureCall = module.statements[0] as ProcedureCall
        assertThat(procedureCall.actualParameters[0], instanceOf(ReferenceWithSelector::class.java))
        val reference = procedureCall.actualParameters[0] as ReferenceWithSelector
        assertThat(reference.selectors[0], instanceOf(FieldSelector::class.java))
    }

    @Test
    fun arrayIndexingInAssignment() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR a: ARRAY 32 OF INTEGER;
                  |BEGIN
                  |  a[0] := 1
                  |END Test.""".trimMargin())
        assertThat(module.statements[0], instanceOf(AssignmentWithSelector::class.java))
        val assignment = module.statements[0] as AssignmentWithSelector
        assertThat(assignment.selectors[0], instanceOf(IndexSelector::class.java))
    }

    @Test
    fun fieldSelectorInAssignment() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR r: RECORD a, b: INTEGER; c: BOOLEAN END;
                  |BEGIN
                  |  r.a := 1
                  |END Test.""".trimMargin())
        assertThat(module.statements[0], instanceOf(AssignmentWithSelector::class.java))
        val assignment = module.statements[0] as AssignmentWithSelector
        assertThat(assignment.selectors[0], instanceOf(FieldSelector::class.java))
    }
}
