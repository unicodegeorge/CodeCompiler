package app.web.zacnikodit.utility

class InputGroup(private val sentence: String, private val reader: String)

class CodeScanner(private val code: String) {
    fun getAmountOfInputs(): Int {
        val keywords = listOf(
            "nextLine()",
            "next()",
            "nextInt()",
            "nextDouble()",
            "nextFloat()",
            "nextLong()"
        )
        val rx = Regex("\\b(?:${keywords.joinToString(separator="|")})\\b")
        val splitCode = code.split(' ')
        return splitCode.filter { word -> rx.containsMatchIn(word)}.size
    }


}