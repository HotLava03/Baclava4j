package io.github.hotlava03.baclava4j.commands.owner

import io.github.hotlava03.baclava4j.commands.Category
import io.github.hotlava03.baclava4j.commands.CategoryType
import io.github.hotlava03.baclava4j.commands.Command

class OwnerCategory : Category {
    override val type: CategoryType
        get() = CategoryType.OWNER

    override val commands: List<Command>
        get() = listOf(
                Command(
                        "jeval", arrayOf(),  // aliases
                        "Java quality eval only for lava",  // description
                        ">>jeval <code>", arrayOf(),  // permissions to use
                        this,  // Category
                        JEval() // the executor
                ),
                Command(
                        "keval", arrayOf(),
                        "Kotlin quality eval only for lava",
                        ">>keval <code>", arrayOf(),
                        this,
                        KEval()
                )
        )
}