package io.github.hotlava03.baclava4j.commands;

import discord4j.core.object.util.Permission;

public class Command {

    private String name;
    private String[] aliases;
    private String description;
    private String usage;
    private Permission[] permissions;
    private Category category;
    private CommandExecutor executor;

    public Command(String name, String[] aliases, String description, String usage, Permission[] permissions,
            Category category, CommandExecutor executor) {
        this.setName(name);
        this.setAliases(aliases);
        this.setDescription(description);
        this.setUsage(usage);
        this.setPermissions(permissions);
        this.setCategory(category);
        this.setExecutor(executor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }
}