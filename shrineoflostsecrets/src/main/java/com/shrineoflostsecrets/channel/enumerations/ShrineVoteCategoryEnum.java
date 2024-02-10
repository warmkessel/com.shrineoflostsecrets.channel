package com.shrineoflostsecrets.channel.enumerations;

public enum ShrineVoteCategoryEnum {
	 // Enum constants with id, name, and description
    CATEGORY_ONE(1, "Lazzies", "Best Troll"),
    CATEGORY_TWO(2, "Category Two", "Description for Category Two"),
    CATEGORY_THREE(3, "Category Three", "Description for Category Three");

    // Fields for the enum properties
    private final int id;
    private final String name;
    private final String description;

    // Constructor for enum
    ShrineVoteCategoryEnum(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters for the properties
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Optional: Override toString() if you want to print the enum constants in a custom format
    @Override
    public String toString() {
        return String.format("%d - %s: %s", id, name, description);
    }
    public static ShrineVoteCategoryEnum findById(String id) {
        try {
            int intId = Integer.parseInt(id);
            return findById(intId);
        } catch (NumberFormatException e) {
            // Log the error, handle it, or return a default value
            // Assuming CATEGORY_ONE as a default for an invalid id.
            return ShrineVoteCategoryEnum.CATEGORY_ONE;
        }
    }
   
    public static ShrineVoteCategoryEnum findById(int id) {
        for (ShrineVoteCategoryEnum category : ShrineVoteCategoryEnum.values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        return CATEGORY_ONE;
       
    }
}