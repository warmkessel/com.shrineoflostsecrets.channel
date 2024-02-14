package com.shrineoflostsecrets.channel.enumerations;

public enum BotEnum {
	// Enum constants with id, name, and description
	COMMAND("!"), VOTE("!vote"), RESPOND("!respond");

	// Fields for the enum properties
	private final String command;

	// Constructor for enum
	BotEnum(String command) {
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
    public static BotEnum findById(String id) {
        if (id != null) {
            String idLower = id.toLowerCase();
            for (BotEnum value : values()) {
                if (value.getCommand().equals(idLower)) {
                    return value;
                }
            }
        }
        // Default if no match is found
        return COMMAND;
    } 
}