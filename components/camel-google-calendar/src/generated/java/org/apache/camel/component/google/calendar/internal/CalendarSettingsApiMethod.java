
/*
 * Camel ApiMethod Enumeration generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.google.calendar.internal;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import com.google.api.services.calendar.Calendar.Settings;

import org.apache.camel.support.component.ApiMethod;
import org.apache.camel.support.component.ApiMethodArg;
import org.apache.camel.support.component.ApiMethodImpl;
import org.apache.camel.util.StringHelper;

import static org.apache.camel.support.component.ApiMethodArg.arg;

/**
 * Camel {@link ApiMethod} Enumeration for com.google.api.services.calendar.Calendar$Settings
 */
public enum CalendarSettingsApiMethod implements ApiMethod {

    GET(
        com.google.api.services.calendar.Calendar.Settings.Get.class,
        "get",
        arg("setting", String.class)),

    LIST(
        com.google.api.services.calendar.Calendar.Settings.List.class,
        "list"),

    WATCH(
        com.google.api.services.calendar.Calendar.Settings.Watch.class,
        "watch",
        arg("contentChannel", com.google.api.services.calendar.model.Channel.class));

    private static final CalendarSettingsApiMethod[] CACHED_ENUM_VALUES = values();

    private final ApiMethod apiMethod;

    private CalendarSettingsApiMethod(Class<?> resultType, String name, ApiMethodArg... args) {
        this.apiMethod = new ApiMethodImpl(Settings.class, resultType, name, args);
    }

    @Override
    public String getName() { return apiMethod.getName(); }

    public static CalendarSettingsApiMethod fromValue(String value) throws IllegalArgumentException {
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

    @Override
    public Class<?> getResultType() { return apiMethod.getResultType(); }

    @Override
    public List<String> getArgNames() { return apiMethod.getArgNames(); }

    @Override
    public List<Class<?>> getArgTypes() { return apiMethod.getArgTypes(); }

    @Override
    public Method getMethod() { return apiMethod.getMethod(); }
}
