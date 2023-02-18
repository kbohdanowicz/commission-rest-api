package kbohdanowicz.restapi.common.reading

fun getResourceAsString(path: String): String =
    object {}.javaClass.getResource("/$path")?.readText()
    ?: throw IllegalArgumentException("File \"$path\" not found")
