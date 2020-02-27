{% extends "base_template.java" %}
{% block body %}
public enum {{class_name}} {
    {%- for param in params %}
    {%- if param.description is defined or param.since is defined %}
    /**
     {%- if param.description is defined %}
     {%- for d in param.description %}
     * {{d}}
     {%- endfor %}{% endif -%}
     {%- if param.description is defined and (param.since is defined or param.see is defined) %}
     *
     {%- endif %}
     {%- if param.since is defined %}
     * @since SmartDeviceLink {{param.since}}
     {%- endif %}
     {%- if param.see is defined %}
     * @see {{param.see}}
     {%- endif %}
     */
    {%- endif %}
    {%- if param.deprecated is defined %}
    @Deprecated
    {%- endif %}
    {%- if kind == "simple" %}
    {{param.name}}{{ "," if not loop.last }}
    {%- elif kind == "custom" %}
    {{param.name}}({{param.internal}}){{ "," if not loop.last }}
    {%- endif %}
    {%- endfor %};

    {%- if kind == "simple" %}

    /**
     * Convert String to {{class_name}}
     *
     * @param value String
     * @return {{class_name}}
     */
    public static {{class_name}} valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    {%- elif kind == "custom" %}
    {%- if return_type == "String" %}

    private final String INTERNAL_NAME;

    private {{class_name}}(String internalName) {
        this.INTERNAL_NAME = internalName;
    }

    /**
     * Convert String to {{class_name}}
     *
     * @param value String
     * @return {{class_name}}
     */
    public static {{class_name}} valueForString(String value) {
        if (value == null) {
            return null;
        }

        for ({{class_name}} anEnum : EnumSet.allOf({{class_name}}.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return INTERNAL_NAME;
    }
    {%- elif return_type == "int" %}

    private final int VALUE;
    /**
     * Private constructor
     */
    private {{class_name}} (int value) {
        this.VALUE = value;
    }

    public static {{class_name}} valueForInt(int value) {
        for ({{class_name}} anEnum : EnumSet.allOf({{class_name}}.class)) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

    public int getValue(){
        return VALUE;
    }
    {%- elif return_type == "bool" %}

    boolean IS_QUERYABLE;

    {{class_name}}(boolean isQueryable) {
        this.IS_QUERYABLE = isQueryable;
    }

    public boolean isQueryable() {
        return IS_QUERYABLE;
    }

    public static {{class_name}} valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    {%- endif %}
    {%- endif %}
}
{% endblock -%}