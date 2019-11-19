package io.github.hotlava03.baclava4j.commands;

import java.util.List;

public interface Category {
    public CategoryType getType();
    public List<Command> getCommands();
}