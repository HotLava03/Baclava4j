package io.github.hotlava03.baclava4j.commands.`fun`

import io.github.hotlava03.baclava4j.commands.Category
import io.github.hotlava03.baclava4j.commands.CategoryType
import io.github.hotlava03.baclava4j.commands.Command

class FunCategory : Category {
    override val type: CategoryType
        get() = CategoryType.FUN

    override val commands: List<Command>
        get() = listOf()
}