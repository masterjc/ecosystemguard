package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "educationLevelType")
@XmlEnum
public enum EducationLevelType {

    @XmlEnumValue("under 6")
    UNDER_6("under 6"),

    @XmlEnumValue("6")
    SIX("6"),

    @XmlEnumValue("7")
    SEVEN("7"),

    @XmlEnumValue("8")
    EIGHT("8"),

    @XmlEnumValue("9")
    NINE("9"),

    @XmlEnumValue("10")
    TEN("10"),

    @XmlEnumValue("11")
    ELEVEN("11"),

    @XmlEnumValue("12")
    TWELVE("12"),

    @XmlEnumValue("post secondary")
    POST_SECONDARY("post secondary"),

    @XmlEnumValue("college")
    COLLEGE("college"),
    @XmlEnumValue("")

    BLANK("");
    private final String value;

    EducationLevelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EducationLevelType fromValue(String v) {
        for (EducationLevelType c: EducationLevelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}