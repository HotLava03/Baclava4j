package io.github.hotlava03.baclava4j.commands.fun;

import java.util.Arrays;
import java.util.List;

import io.github.hotlava03.baclava4j.commands.Category;
import io.github.hotlava03.baclava4j.commands.CategoryType;
import io.github.hotlava03.baclava4j.commands.Command;

public class FunCategory implements Category {

    @Override
    public CategoryType getType() {
        return CategoryType.FUN;
    }

    @Override
    public List<Command> getCommands() {
        return Arrays.asList(
            
        );
    }

}