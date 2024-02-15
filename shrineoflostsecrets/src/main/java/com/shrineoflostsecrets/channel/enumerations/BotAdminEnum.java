package com.shrineoflostsecrets.channel.enumerations;

public enum BotAdminEnum {
	// Enum constants with id, name, and description
	COMMAND("!"), DISPLAYVOTE("!vote");

	// Fields for the enum properties
	private final String command;

	// Constructor for enum
	BotAdminEnum(String command) {
		this.command = command;
	}

	// Getters for the properties
	public String getCommand() {
		return command;
	}

	// Getters for the properties
	public boolean isEqual(String otherCommand) {
		return (getCommand().equals(otherCommand));
	}
	// Getters for the properties
	public boolean startsWith(String otherCommand) {
		return otherCommand.startsWith(COMMAND.getCommand());
	}
	// Method to find enum by name, comparing in lowercase
    public static BotAdminEnum findById(String id) {
        if (id != null) {
            String idLower = id.toLowerCase();
            for (BotAdminEnum value : values()) {
                if (value.getCommand().equals(idLower)) {
                    return value;
                }
            }
        }
        // Default if no match is found
        return COMMAND;
    } 
}