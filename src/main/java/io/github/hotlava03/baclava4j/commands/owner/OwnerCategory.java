package io.github.hotlava03.baclava4j.commands.owner;

import java.util.Arrays;
import java.util.List;

import discord4j.core.object.util.Permission;
import io.github.hotlava03.baclava4j.commands.Category;
import io.github.hotlava03.baclava4j.commands.CategoryType;
import io.github.hotlava03.baclava4j.commands.Command;

public class OwnerCategory implements Category {

    @Override
    public CategoryType getType() {
        return CategoryType.OWNER;
    }

    @Override
    public List<Command> getCommands() {
        return Arrays.asList(
            new Command(
                "jeval", // name
                new String[] {}, // aliases
                "Java quality eval only for lava", // description
                ">>jeval <code>", // usage
                new Permission[] {}, // permissions to use
                this, // Category
                new JEval() // the executor
        ));
    }

}