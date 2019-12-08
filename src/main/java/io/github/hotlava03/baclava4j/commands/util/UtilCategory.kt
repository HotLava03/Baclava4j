package io.github.hotlava03.baclava4j.commands.util

import io.github.hotlava03.baclava4j.commands.Category
import io.github.hotlava03.baclava4j.commands.CategoryType
import io.github.hotlava03.baclava4j.commands.Command

class UtilCategory : Category {
    override val type: CategoryType
        get() = CategoryType.UTIL

    override val commands: List<Command>
        get() = listOf()
}