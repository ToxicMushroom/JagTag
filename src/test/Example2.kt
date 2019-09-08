import com.jagrosh.jagtag.Method
import com.jagrosh.jagtag.Parser
import com.jagrosh.jagtag.ParserBuilder

class Example2 {
    init {
        val parser: Parser = ParserBuilder()
                .addMethod(Method("randint", { (Math.random() * 100).toInt().toString() }))
                .addMethod(Method("randDecimal", { Math.random().toString().substring(1) }))
                .build()
        println(parser.parse("{randint}{randDecimal}"))
    }
}

fun main() {
    Example2()
}