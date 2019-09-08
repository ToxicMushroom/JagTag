import com.jagrosh.jagtag.Parser
import com.jagrosh.jagtag.ParserBuilder
import com.jagrosh.jagtag.libraries.Functional
import com.jagrosh.jagtag.libraries.Strings
import com.jagrosh.jagtag.libraries.Variables
import javax.swing.JOptionPane

class Example1 {
    init {
        val parser: Parser = ParserBuilder()
                .addMethods(Functional.getMethods())
                .addMethods(Strings.getMethods())
                .addMethods(Variables.getMethods())
                .build()

        var input: String
        while (true) {
            input = JOptionPane.showInputDialog("Enter JagTag to parse:")
            if (input == null) break
            JOptionPane.showMessageDialog(null, parser.clear().parse(input))
        }
    }
}

fun main(args: Array<String>) {
    Example1()
}