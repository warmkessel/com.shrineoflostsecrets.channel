package com.shrineoflostsecrets.channel.enumerations;

public enum ShrineServiceTypeEnum {
    // Enum constants
    FULL("full", false, false),
    SAFE("safe", true, false),
    LIMITED("limited", false, false),
    BAN("ban", false, true);

    // Field for the enum property
    private final String name;
    private final boolean ban;
    private final boolean safe;

    // Constructor for enum
    ShrineServiceTypeEnum(String name,  boolean safe, boolean ban) {
        this.name = name;
        this.safe = safe;
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
    // Getter for the name
    public boolean getSafe() {
        return safe;
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
