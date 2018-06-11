package yajco.oberon0

import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import yajco.oberon0.model.*
import yajco.oberon0.model.Number
import yajco.oberon0.model.l4.ArrayType
import yajco.oberon0.model.l4.RecordType
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


    private fun translate(input: String): String {
        val module = parser!!.parse(input)
        L3NamesResolver.resolve(module)
        L3Transformation.liftProcedures(module)
        val writer = StringWriter()
        L3CodeGenerator.generate(module, PrintWriter(writer))
        return writer.toString()
    }
}
