package yajco.oberon0

import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import yajco.oberon0.model.*
import yajco.oberon0.model.Number
import yajco.oberon0.model.l3.ProcedureCall
import yajco.oberon0.model.l4.*
import yajco.oberon0.model.parser.LALRModuleParser

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
                  |  Write(a[1])
                  |END Test.""".trimMargin())
        val procedureCall = module.statements[0] as ProcedureCall
        assertThat(procedureCall.actualParameters[0], instanceOf(IndexSelector::class.java))
        val reference = procedureCall.actualParameters[0] as IndexSelector
        assertThat(reference.base.name, equalTo("a"))
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
        assertThat(procedureCall.actualParameters[0], instanceOf(FieldSelector::class.java))
        val reference = procedureCall.actualParameters[0] as FieldSelector
        assertThat(reference.base.name, equalTo("r"))
        assertThat(reference.fieldName, equalTo("a"))
    }

    @Test
    fun arrayIndexingInAssignment() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR a: ARRAY 32 OF INTEGER;
                  |BEGIN
                  |  a[1] := 1
                  |END Test.""".trimMargin())
        val assignment = module.statements[0] as Assignment
        assertThat(assignment.reference, instanceOf(IndexSelector::class.java))
    }

    @Test
    fun fieldSelectorInAssignment() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR r: RECORD a, b: INTEGER; c: BOOLEAN END;
                  |BEGIN
                  |  r.a := 1
                  |END Test.""".trimMargin())
        val assignment = module.statements[0] as Assignment
        assertThat(assignment.reference, instanceOf(FieldSelector::class.java))
    }

    @Test
    fun fieldNameResolution() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR r: RECORD a, b: INTEGER END;
                  |BEGIN
                  |  r.a := 1
                  |END Test.""".trimMargin())
        L3NamesResolver.resolve(module)
        val record = module.declarations["r"].type as RecordType
        val assignment = module.statements[0] as Assignment
        assertThat((assignment.reference as FieldSelector).field, equalTo(record.fields["a"]))
    }

    @Test
    fun complexFieldNameResolution() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR r: RECORD
                  |    a: ARRAY 10 OF RECORD
                  |      b: INTEGER
                  |    END
                  |  END;
                  |BEGIN
                  |  r.a[1].b := 1
                  |END Test.""".trimMargin())
        L3NamesResolver.resolve(module)
        val record1 = module.declarations["r"].type as RecordType
        val record2 = (record1.fields["a"]!!.type as ArrayType).elementType as RecordType
        val fieldSelector2 = (module.statements[0] as Assignment).reference as FieldSelector
        assertThat(fieldSelector2.field, equalTo(record2.fields["b"]))
        val indexSelector = fieldSelector2.base as IndexSelector
        assertThat((indexSelector.base as FieldSelector).field, equalTo(record1.fields["a"]))
    }

    @Test
    fun complexFieldReferenceNameResolution() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR r: RECORD
                  |    a: ARRAY 10 OF RECORD
                  |      b: INTEGER
                  |    END
                  |  END;
                  |BEGIN
                  |  Write(r.a[1].b)
                  |END Test.""".trimMargin())
        L3NamesResolver.resolve(module)
        val record1 = module.declarations["r"].type as RecordType
        val record2 = (record1.fields["a"]!!.type as ArrayType).elementType as RecordType
        val writeCall = module.statements[0] as ProcedureCall
        val fieldSelector = writeCall.actualParameters[0] as FieldSelector
        assertThat(fieldSelector.field, equalTo(record2.fields["b"]))
        assertThat(((fieldSelector.base as IndexSelector).base as FieldSelector).field, equalTo(record1.fields["a"]))
    }

    @Test
    fun correctTypes() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR a: ARRAY 10 OF INTEGER;
                  |      r: RECORD a, b: INTEGER END;
                  |BEGIN
                  |  a[1] := 1;
                  |  r.a := 2
                  |END Test.""".trimMargin())
        assertThat(L3NamesResolver.resolve(module), equalTo(emptyList()))
        val errors = L4TypeChecker.check(module)
        assertThat(errors, equalTo(emptyList()))
    }

    @Test
    fun incorrectTypes() {
        val module = parser!!.parse(
                """MODULE Test;
                  |  VAR a: ARRAY 10 OF INTEGER;
                  |      r: RECORD a, b: INTEGER END;
                  |BEGIN
                  |  a[1] := TRUE;
                  |  r.a := FALSE
                  |END Test.""".trimMargin())
        assertThat(L3NamesResolver.resolve(module), equalTo(emptyList()))
        val errors = L4TypeChecker.check(module)
        assertThat(errors.size, equalTo(2))
    }
}
