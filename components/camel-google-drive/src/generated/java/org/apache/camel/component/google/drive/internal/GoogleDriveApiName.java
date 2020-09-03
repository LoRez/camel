
/*
 * Camel ApiName Enumeration generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.google.drive.internal;

import java.util.Locale;
import org.apache.camel.support.component.ApiName;
import org.apache.camel.util.StringHelper;

/**
 * Camel {@link ApiName} Enumeration for Component GoogleDrive
 */
public enum GoogleDriveApiName implements ApiName {

    DRIVE_ABOUT("drive-about"),

    DRIVE_APPS("drive-apps"),

    DRIVE_CHANGES("drive-changes"),

    DRIVE_CHANNELS("drive-channels"),

    DRIVE_CHILDREN("drive-children"),

    DRIVE_COMMENTS("drive-comments"),

    DRIVE_FILES("drive-files"),

    DRIVE_PARENTS("drive-parents"),

    DRIVE_PERMISSIONS("drive-permissions"),

    DRIVE_PROPERTIES("drive-properties"),

    DRIVE_REALTIME("drive-realtime"),

    DRIVE_REPLIES("drive-replies"),

    DRIVE_REVISIONS("drive-revisions");


    private static final GoogleDriveApiName[] CACHED_ENUM_VALUES = values();
    
    private final String name;

    private GoogleDriveApiName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static GoogleDriveApiName fromValue(String value) throws IllegalArgumentException {
        value = getEnumConstant(value);
        for (int i = 0; i < CACHED_ENUM_VALUES.length; i++) {
            if (CACHED_ENUM_VALUES[i].toString().equalsIgnoreCase(value)) {
                return CACHED_ENUM_VALUES[i];
            }
        }
        throw new IllegalArgumentException("Invalid value " + value);
    }

    private static String getEnumConstant(String enumValue) {
        if (enumValue == null || enumValue.isEmpty()) {
            return "DEFAULT";
        }
        String value = StringHelper.camelCaseToDash(enumValue);
        // replace dash with underscore and upper case
        value = value.replace('-', '_');
        value = value.toUpperCase(Locale.ENGLISH);
        return value;
    }

}
