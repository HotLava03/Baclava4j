package io.github.hotlava03.baclava4j.commands.basic;

import java.util.Arrays;
import java.util.List;

import io.github.hotlava03.baclava4j.commands.Category;
import io.github.hotlava03.baclava4j.commands.CategoryType;
import io.github.hotlava03.baclava4j.commands.Command;

public class BasicCategory implements Category {

    @Override
    public CategoryType getType() {
        return CategoryType.BASIC;
    }

    @Override
    public List<Command> getCommands() {
        return Arrays.asList(
            
        );
    }
    
}