package io.github.hotlava03.baclava4j.commands.basic

import io.github.hotlava03.baclava4j.commands.Category
import io.github.hotlava03.baclava4j.commands.CategoryType
import io.github.hotlava03.baclava4j.commands.Command

class BasicCategory : Category {
    override val type: CategoryType
        get() = CategoryType.BASIC

    override val commands: List<Command>
        get() = listOf()
}