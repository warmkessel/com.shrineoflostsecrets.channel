package com.shrineoflostsecrets.channel.enumerations;

public enum ShrineServiceTypeEnum {
    // Enum constants
    FULL("full", true),
    SAVE("safe", false),
    LIMITED("limited", false);

    // Field for the enum property
    private final String name;
    private final boolean ban;

    // Constructor for enum
    ShrineServiceTypeEnum(String name, boolean ban) {
        this.name = name;
        this.ban = ban;
    }

    // Getter for the name
    public String getName() {
        return name;
    }
    // Getter for the name
    public boolean getBan() {
        return ban;
    }

    // Method to find enum by name
    public static ShrineServiceTypeEnum findById(String id) {
        for (ShrineServiceTypeEnum value : ShrineServiceTypeEnum.values()) {
            if (value.getName().equalsIgnoreCase(id)) {
                return value;
            }
        }
        // Default if no match is found
        return LIMITED;
    }
}
