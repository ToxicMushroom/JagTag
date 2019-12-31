import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method
import com.jagrosh.jagtag.ParserBuilder
import kotlin.math.max

class Example3 {
    init {
        val parser = ParserBuilder()
            .addMethod(Method("repeat", { env: Environment ->
                env["last"].toString()
            }, { env: Environment, input: Array<String> ->
                val output = StringBuilder(input[0])
                try {
                    output.append(input[0].repeat(max(0, Integer.parseInt(input[1]) - 1)))
                } catch (ignored: NumberFormatException) {
                }

                env["last"] = output.toString()
                output.toString()
            }, arrayOf("|times:")))
            .build()
        println(parser.parse("{repeat:hello |times:4}"))
        println(parser.parse("Repetition!: {repeat}"))
        println(parser.clear().parse("Repetition?: {repeat}"))
    }
}

fun main() {
    Example3()
}