package com.jagrosh.jagtag.libraries

import com.jagrosh.jagtag.Method
import java.util.*

/**
 * Miscellaneous Library
 *
 * @author John Grosh (jagrosh)
 */
object Miscellaneous {
    fun getMethods(): Collection<Method> {
        return listOf(
            // gets a uuid
            Method("uuid", { UUID.randomUUID().toString() }),
        )
    }
}