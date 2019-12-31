package com.jagrosh.jagtag

class Environment : HashMap<String, Any>() {

    /** returns the value of the reified type from key or null **/
    inline fun <reified T> getReified(key: String): T? {
        val value = super.get(key)
        return if (value is T?) {
            value
        } else {
            null
        }
    }

    /** returns the value of the reified type from key or the default value **/
    inline fun <reified T> getOrDefaultReified(key: String, defaultValue: T): T {
        val any = super.getOrDefault(key, defaultValue as Any)
        return if (any is T) {
            any
        } else {
            defaultValue
        }
    }

    /** returns the value of the reified type from key or throws an exception **/
    inline fun <reified T> getReifiedX(key: String): T {
        val value = super.get(key)
        return if (value is T) {
            value
        } else {
            throw IllegalArgumentException("value is not the correct type, value: $value")
        }
    }
}
