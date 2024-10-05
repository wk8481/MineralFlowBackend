package be.kdg.prgramming6.landside.domain;

public enum MaterialType {
    GYPSUM,
    IRON_ORE,
    CEMENT,
    PET_COKE,
    SLAG;

    public static MaterialType fromString(String materialType) {
        try {
            return MaterialType.valueOf(materialType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid material type: " + materialType);
        }
    }
}
