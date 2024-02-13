package com.shrineoflostsecrets.channel.enumerations;

public enum ShrineServiceTypeEnum {
    // Enum constants
    FULL("full", false, false),
    SAFE("safe", true, false),
    LIMITED("limited", false, false),
    BAN("ban", false, true);

    // Fields for the enum properties
    private final String name;
    private final boolean safe;
    private final boolean ban;

    // Constructor for enum
    ShrineServiceTypeEnum(String name, boolean safe, boolean ban) {
        // Ensure name is stored in lowercase
        this.name = name.toLowerCase();
        this.safe = safe;
        this.ban = ban;
    }

    // Getter for the name
    public String getName() {
        return name;
    }

    // Getter for the safe flag
    public boolean isSafe() {
        return safe;
    }

    // Getter for the ban flag
    public boolean isBan() {
        return ban;
    }

    // Method to find enum by name, comparing in lowercase
    public static ShrineServiceTypeEnum findById(String id) {
        if (id != null) {
            String idLower = id.toLowerCase();
            for (ShrineServiceTypeEnum value : values()) {
                if (value.getName().equals(idLower)) {
                    return value;
                }
            }
        }
        // Default if no match is found
        return LIMITED;
    }
}