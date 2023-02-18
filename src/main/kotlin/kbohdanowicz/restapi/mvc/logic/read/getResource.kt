package kbohdanowicz.restapi.mvc.logic.read

fun getResourceAsString(path: String): String =
    object {}.javaClass.getResource("/$path")?.readText()
    ?: throw IllegalArgumentException("File \"$path\" not found")
