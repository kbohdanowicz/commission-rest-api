package kbohdanowicz.restapi.common.reading

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord

fun readCSV(path: String): List<CSVRecord> =
    CSVFormat.Builder
        .create(CSVFormat.DEFAULT)
        .apply {
            setIgnoreSurroundingSpaces(true)
        }
        .build()
        .parse(getResourceAsString(path).reader())
        .drop(1)
