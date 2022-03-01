package app.web.zacnikodit.utility

class CodeScanner(private val code: String) {
    private val keywords = listOf(
        "nextLine()",
        "next()",
        "nextInt()",
        "nextDouble()",
        "nextFloat()",
        "nextLong()"
    )
    private val rx = Regex("\\b(?:${keywords.joinToString(separator="|")})\\b")
    private val splitCode = code.split(' ')
    fun getAmountOfInputs(): Int {
        return splitCode.filter { word -> rx.containsMatchIn(word)}.size
    }

    fun getInputLines(): List<String> {
        
        val rx = Regex("\\b(?:${keywords.joinToString(separator="|")})\\b")
        val splitCode = code.split(' ')
        return splitCode.filter { word -> rx.containsMatchIn(word)}
    }



}