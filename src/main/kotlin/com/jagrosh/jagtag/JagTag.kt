package com.jagrosh.jagtag

import com.jagrosh.jagtag.libraries.*

const val VERSION = "0.6"
const val REPOSITORY_URL = "https://github.com/toxicmushroom/JagTag"

/**
 * @author John Grosh (jagrosh)
 */
class JagTag {

    fun newDefaultBuilder(): ParserBuilder {
        return ParserBuilder()
            .addMethods(Arguments.getMethods())
            .addMethods(Functional.getMethods())
            .addMethods(Strings.getMethods())
//            .addMethods(Variables.getMethods())
            .addMethods(Time.getMethods())
            .addMethods(Miscellaneous.getMethods())
    }
}
