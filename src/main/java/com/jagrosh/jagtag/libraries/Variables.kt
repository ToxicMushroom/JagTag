package com.jagrosh.jagtag.libraries

import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method

object Variables {

    fun getMethods(): Collection<Method> {
        return listOf(
                // creates a variable if it doesn't exist, and sets it to a value
                Method("set", complex = { env: Environment, input: Array<String> ->
                    var vars: HashMap<String, String>? = env.getReified("vars")
                    if (vars == null) {
                        vars = HashMap()
                        env["vars"] = vars
                    }
                    vars[input[0]] = input[1]
                    ""
                }, splitter = arrayOf("|")),

                // gets the value of a variable
                Method("get", complex = { env: Environment, input: Array<String> ->
                    val vars: HashMap<String, String> = env.getOrDefaultReified("vars", HashMap())
                    vars.getOrDefault(input[0], "")
                })
        )
    }
}
