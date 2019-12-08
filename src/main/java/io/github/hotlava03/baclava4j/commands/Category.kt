package io.github.hotlava03.baclava4j.commands

interface Category {
    val type: CategoryType
    val commands: List<Command>
}