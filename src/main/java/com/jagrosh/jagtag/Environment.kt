package com.jagrosh.jagtag

class Environment : HashMap<String, Any>() {

    inline fun <reified T> getReified(key: String): T? {
        val value = super.get(key)
        return if (value is T?) {
            value
        } else {
            null
        }
    }

    inline fun <reified T> getOrDefaultReified(key: String, defaultValue: T): T {
        val any = super.getOrDefault(key, defaultValue as Any)
        return if (any is T) {
            any
        } else {
            defaultValue
        }
    }
}
