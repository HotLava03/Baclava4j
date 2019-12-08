package io.github.hotlava03.baclava4j.commands

abstract class CommandExecutor {
    abstract fun onCommand(e: CommandEvent?)
}