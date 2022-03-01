package app.web.zacnikodit.utility

import app.web.zacnikodit.plugins.getStdInput
import com.jaredrummler.ktsh.Shell
import com.lordcodes.turtle.ShellLocation
import com.lordcodes.turtle.shellRun
import java.io.File

class DockerUtility {
    private val pathToContainerID = File("/Users/unicode/Educanet/Dzk_prs/codecompiler/docker/id.txt")
     private fun getContainerID(): String = File(pathToContainerID.absolutePath).readLines()[0]

    private fun handleInputs(inputs: MutableList<String>, shell: Shell) {
        for(input in inputs) {
            println("Once and once")
            shell.run("echo '$input'")
        }
    }

    fun runContainer(inputs: MutableList<String>) {
        val shell = Shell("sh")
        val dockerDir = ShellLocation.CURRENT_WORKING.resolve("docker")
        println("Building image from dockerfile...")
        shellRun("docker", listOf("build", "-t", "compiler", "."), dockerDir)
        println("Image created...")
        println("Running docker image...")

        println(shell.run("cd docker"))
        println(shell.run("touch id.txt").output)

        //

        val runningImage = shell.run("docker run -i -d --name javacode compiler >> id.txt")
        if (runningImage.isSuccess) {
            println("It ran smoothly - ${getStdInput()}")
        } else {

        }


        val containerId = getContainerID()

        // Completely disgusting amount of shell script runs
        // TODO: Make it more clean.

        println("Id retrieved successfully...")
        println("Copying files from container to host...")
        shellRun("docker", listOf("cp", "${containerId}:/zacnikoditAppTests/input/output.txt", "."), dockerDir)
        println("Files copied successfully...")

        println("Killing container - ${containerId}!")
        shellRun("docker", listOf("rm", "-f", containerId), dockerDir)
        println("Container removed successfully!")
        shell.run("rm id.txt")

    }

}