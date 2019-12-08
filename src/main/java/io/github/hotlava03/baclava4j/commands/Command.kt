package io.github.hotlava03.baclava4j.commands

import discord4j.core.`object`.util.Permission

class Command(val name: String, val aliases: Array<String>?, val description: String?, val usage: String?, val permissions: Array<Permission>?,
              val category: Category, val executor: CommandExecutor)