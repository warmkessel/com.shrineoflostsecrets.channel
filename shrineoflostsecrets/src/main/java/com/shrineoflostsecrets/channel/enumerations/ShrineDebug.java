package com.shrineoflostsecrets.channel.enumerations;

public enum ShrineDebug {
	// Enum constants with id, name, and description
	PROUCTION(false), DEBUG(true), BOTH(false);

	// Fields for the enum properties
	private final boolean state;

	// Constructor for enum
	ShrineDebug(boolean state) {
		this.state = state;
	}

	// Getters for the properties
	public boolean getState() {
		return state;
	}

	// Getters for the properties
	public boolean isEqual(boolean otherState) {
		return (getState() == otherState);
	}
	 // Overridden equals method to compare two ShrineDebug instances
}