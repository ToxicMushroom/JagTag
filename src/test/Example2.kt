import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method
import com.jagrosh.jagtag.Parser
import com.jagrosh.jagtag.ParserBuilder

class Example2 {
    init {
        val randIntFunc = { _: Environment -> (Math.random() * 100).toInt().toString() }
        val randDecFunc = { _: Environment -> Math.random().toString().substring(1) }
        val parser: Parser = ParserBuilder()
                .addMethod(Method("randint", randIntFunc))
                .addMethod(Method("randdecimal", randDecFunc))
                .build()
        println(parser.parse("{randint}{randDecimal}"))
    }
}

fun main(args: Array<String>) {
    Example2()
}