import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method
import com.jagrosh.jagtag.ParseException
import com.jagrosh.jagtag.ParserBuilder

class Example4 {
    init {
        val parser = ParserBuilder()
            .addMethod(Method("check", complex = { _: Environment, input: Array<String> ->
                if (input[0] == "throw") try {
                    throw ParseException("No throwing!")
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                input[0]
            }))
            .build()
        println(parser.parse("{check:this and {check:that and {check:something else}}}"))
        println(parser.parse("{check:this and {check:that and {check:throw}}}"))
    }
}

fun main() {
    Example4()
}