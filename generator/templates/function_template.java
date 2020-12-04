{% extends "struct_function_template.java" %}

    {%- block params %}
    {%- if params is defined %}
    {%- for p in params %}
    {%- if p.origin not in ('success', 'resultCode', 'info') or kind != "response" %}{% set see, deprecated, since, history, spacing, begin, end, prefix = p.see, p.deprecated, p.since, p.history, '    ', '/**', ' */', ' * ' %}
    {%- include "javadoc_version_info.java" %}
    {%- if p.deprecated is not none %}
    @Deprecated
    {%- endif %}
    public static final String {{p.key}} = "{{p.origin}}";
    {%- endif %}
    {%- endfor %}
    {%- endif %}
    {%- endblock %}

    {%- block constructor_simple %}
    public {{class_name}}() {
        super(FunctionID.{{function_id}}.toString());
    }{% endblock -%}

    {%- block setter %}
    {%- for p in params|rejectattr('name') %}
    {%- if p.origin not in ('success', 'resultCode', 'info') or kind != "response" %}

    /**
     * Sets the {{p.origin}}.
     *
     {%- include "javadoc_template.java" %}
     */
    {%- if p.deprecated is defined and p.deprecated is not none %}
    @Deprecated
    {%- endif %}
    public {{class_name}} set{{p.title}}({% if p.mandatory %}@NonNull {% endif %}{{p.return_type}} {{p.last}}) {
        setParameters({{p.key}}, {{p.last}});
        return this;
    }

    /**
     * Gets the {{p.origin}}.
     *
     {%- include "javadoc_return.java" %}
     */
    {%- if p.SuppressWarnings is defined %}
    @SuppressWarnings("{{p.SuppressWarnings}}")
    {%- endif %}
    {%- if p.deprecated is defined and p.deprecated is not none %}
    @Deprecated
    {%- endif %}
    public {{p.return_type}} get{{p.title}}() {
        {%- if p.return_type in ['String', 'Boolean', 'Integer'] %}
        return get{{p.return_type}}({{p.key}});
        {%- elif p.return_type in ['Float'] %}
        Object object = getParameters({{p.key}});
        return SdlDataTypeConverter.objectToFloat(object);
        {%- elif p.return_type in ['Double'] %}
        Object object = getParameters({{p.key}});
        return SdlDataTypeConverter.objectToDouble(object);
        {%- else %}
        {%- set clazz = p.return_type %}
        {%- if p.return_type.startswith('List')%}{%set clazz = p.return_type[5:-1]%}{% endif %}
        return ({{p.return_type}}) getObject({{clazz}}.class, {{p.key}});
        {%- endif %}
    }

    {%- endif %}
    {%- endfor %}
    {%- endblock %}