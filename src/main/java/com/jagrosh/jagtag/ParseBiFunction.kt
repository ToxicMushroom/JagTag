package com.jagrosh.jagtag

typealias ParseBiFunction = suspend (env: Environment, input: Array<String>) -> String