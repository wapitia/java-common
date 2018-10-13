package com.wapitia.datamodel;

/** Common Persistence Definitions */
public class DataModelDefinitions {

    // Persistence common database column names --------------------------------------
    public static final String CodeCol = "code";
    public static final String IdCol = "id";
    public static final String NameCol = "name";
    public static final String DescriptionCol = "description";
    public static final String ChangedTimeCol = "changed_time";
    public static final String ChangedByCol = "changed_by";
    public static final String CreatedTimeCol = "created_time";
    public static final String CreatedByCol = "created_by";
    public static final String ActiveCol = "active";

    // Persistence common database column lengths ------------------------------------
    public static final int CodeLength = 6;
    public static final int NameLength = 45;
    public static final int DescriptionLength = 4096;

    // Persistence common database types ---------------------------------------
    static final String BooleanColType = "numeric_boolean";
    /** Number of decimal digits in an Entity `id`, which is 11. */
    public static final int IdLength = 11;

    private DataModelDefinitions() {}
}
