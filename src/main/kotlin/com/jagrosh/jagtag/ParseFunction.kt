package com.jagrosh.jagtag

/**
 * A replacement for the Function class which allows throwing a ParseException
 *
 * @author John Grosh (jagrosh)
 */
typealias ParseFunction = suspend (env: Environment) -> String