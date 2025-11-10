package model;

public enum Subject {
    COA, DSA, PDC, POM, WEBDEV, DMS;

    public static Subject fromString(String s) {
        if (s == null) return null;
        switch (s.toUpperCase()) {
            case "COA": return COA;
            case "DSA": return DSA;
            case "PDC": return PDC;
            case "POM": return POM;
            case "WEB-DEV":
            case "WEBDEV": return WEBDEV;
            case "DMS": return DMS;
            default: return null;
        }
    }
}
