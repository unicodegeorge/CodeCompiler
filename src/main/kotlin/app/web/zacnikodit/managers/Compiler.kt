package app.web.zacnikodit.managers

import app.web.zacnikodit.utility.DockerUtility
import java.io.File
import java.io.IOException
@kotlinx.serialization.Serializable



class Compiler(private val code: String) {

    fun compile(inputs: MutableList<String>) {
        createInputFile(inputs)
        DockerUtility().runContainer(inputs)
    }

    private fun validateClassName(): String {
        return code.replace(code.substring(code.indexOf("class") + 6, code.indexOf('{') - 1), "Code")
    }

    private fun createInputFile( inputs: MutableList<String>) {
        println("Creating file...")
        try {
            if(inputs.size > 0) {

                val inputsFileName = "inputs.txt"
                val inputsFile = File("docker/input", inputsFileName)

                val inputsFileContent = StringBuilder()
                for(input in inputs) {
                    inputsFileContent.appendLine(input)
                }
                inputsFile.writeText(inputsFileContent.toString())
            }


            val fileName = "Code.java"
            val javaFile = File("docker/input", fileName);
            val validatedCode = validateClassName();
            println("Filling file...")
            javaFile.writeText(validatedCode);
            println("File created")
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun getOutput(): List<String> {
        return File("docker", "output.txt").readLines()
    }



}