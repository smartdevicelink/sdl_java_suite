{% extends "base_template.java" %}
{% block body %}
public enum {{value}} {
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
    {%- elif kind == "complex" %}
    {{param.name}}({{param.value}}){{ "," if not loop.last }}
    {%- elif kind == "custom" %}
    {{param.name}}({{param.internal}}){{ "," if not loop.last }}
    {%- endif %}
    {%- endfor %};

    {%- if kind == "simple" %}

    /**
     * Convert String to {{value}}
     *
     * @param value String
     * @return {{value}}
     */
    public static {{value}} valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    {%- elif kind == "complex" or kind == "custom" %}
    {%- if return_type == "String" %}

    private final String VALUE;

    /**
     * Private constructor
     */
    private {{value}}(String internalName) {
        this.VALUE = internalName;
    }

    /**
     * Convert String to {{value}}
     *
     * @param value String
     * @return {{value}}
     */
    public static {{value}} valueForString(String value) {
        if (value == null) {
            return null;
        }

        for ({{value}} anEnum : EnumSet.allOf({{value}}.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * Return String value of element
     *
     * @return String
     */
    @Override
    public String toString() {
        return VALUE;
    }
    {%- elif return_type == "int" %}

    private final int VALUE;

    /**
     * Private constructor
     */
    private {{value}} (int value) {
        this.VALUE = value;
    }

    /**
     * Convert int to {{value}}
     *
     * @param value int
     * @return {{value}}
     */
    public static {{value}} valueForInt(int value) {
        for ({{value}} anEnum : EnumSet.allOf({{value}}.class)) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * Return value of element
     *
     * @return int
     */
    public int getValue(){
        return VALUE;
    }
    {%- endif %}
    {%- endif %}
}
{% endblock -%}