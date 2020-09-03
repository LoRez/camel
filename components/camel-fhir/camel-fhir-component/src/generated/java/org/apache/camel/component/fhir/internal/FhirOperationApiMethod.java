
/*
 * Camel ApiMethod Enumeration generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.fhir.internal;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import org.apache.camel.component.fhir.api.FhirOperation;

import org.apache.camel.support.component.ApiMethod;
import org.apache.camel.support.component.ApiMethodArg;
import org.apache.camel.support.component.ApiMethodImpl;
import org.apache.camel.util.StringHelper;

import static org.apache.camel.support.component.ApiMethodArg.arg;

/**
 * Camel {@link ApiMethod} Enumeration for org.apache.camel.component.fhir.api.FhirOperation
 */
public enum FhirOperationApiMethod implements ApiMethod {

    ONINSTANCE(
        org.hl7.fhir.instance.model.api.IBaseResource.class,
        "onInstance",
        arg("id", org.hl7.fhir.instance.model.api.IIdType.class),
        arg("name", String.class),
        arg("parameters", org.hl7.fhir.instance.model.api.IBaseParameters.class),
        arg("outputParameterType", Class.class),
        arg("useHttpGet", boolean.class),
        arg("returnType", Class.class),
        arg("extraParameters", java.util.Map.class)),

    ONINSTANCEVERSION(
        org.hl7.fhir.instance.model.api.IBaseResource.class,
        "onInstanceVersion",
        arg("id", org.hl7.fhir.instance.model.api.IIdType.class),
        arg("name", String.class),
        arg("parameters", org.hl7.fhir.instance.model.api.IBaseParameters.class),
        arg("outputParameterType", Class.class),
        arg("useHttpGet", boolean.class),
        arg("returnType", Class.class),
        arg("extraParameters", java.util.Map.class)),

    ONSERVER(
        org.hl7.fhir.instance.model.api.IBaseResource.class,
        "onServer",
        arg("name", String.class),
        arg("parameters", org.hl7.fhir.instance.model.api.IBaseParameters.class),
        arg("outputParameterType", Class.class),
        arg("useHttpGet", boolean.class),
        arg("returnType", Class.class),
        arg("extraParameters", java.util.Map.class)),

    ONTYPE(
        org.hl7.fhir.instance.model.api.IBaseResource.class,
        "onType",
        arg("resourceType", Class.class),
        arg("name", String.class),
        arg("parameters", org.hl7.fhir.instance.model.api.IBaseParameters.class),
        arg("outputParameterType", Class.class),
        arg("useHttpGet", boolean.class),
        arg("returnType", Class.class),
        arg("extraParameters", java.util.Map.class)),

    PROCESSMESSAGE(
        org.hl7.fhir.instance.model.api.IBaseBundle.class,
        "processMessage",
        arg("respondToUri", String.class),
        arg("msgBundle", org.hl7.fhir.instance.model.api.IBaseBundle.class),
        arg("asynchronous", boolean.class),
        arg("responseClass", Class.class),
        arg("extraParameters", java.util.Map.class));

    private static final FhirOperationApiMethod[] CACHED_ENUM_VALUES = values();

    private final ApiMethod apiMethod;

    private FhirOperationApiMethod(Class<?> resultType, String name, ApiMethodArg... args) {
        this.apiMethod = new ApiMethodImpl(FhirOperation.class, resultType, name, args);
    }

    @Override
    public String getName() { return apiMethod.getName(); }

    public static FhirOperationApiMethod fromValue(String value) throws IllegalArgumentException {
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
