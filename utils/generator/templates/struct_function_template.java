{% extends "base_template.java" %}

{% block body %}
public class {{class_name}} extends {{extends_class}} {
    {%- if params is defined and ((kind is defined and kind != "response") or kind is not defined) %}
    {%- for p in params %}
    {%- if p.see is defined or p.deprecated is not none %}
    /**
     {%- if p.deprecated is not none %}
     * @deprecated
     {%- endif %}
     {%- if p.see is defined %}
     * @see {{p.see}}
     {%- endif %}
     */
    {%- endif %}
    {%- if p.deprecated is not none %}
    @Deprecated
    {%- endif %}
    public static final String {{p.key}} = "{{p.origin}}";
    {%- endfor %}
    {%- endif %}

    /**
     * Constructs a new {{class_name}} object
     */
    {%- block constructor_simple %}
    {% endblock %}

    /**
     * Constructs a new {{class_name}} object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public {{class_name}}(Hashtable<String, Object> hash) {
        super(hash);
    }
    {%- if params is defined %}
    {%- set constructor = [] %}
    {%- for p in params|selectattr('mandatory') %}{{ constructor.append('@NonNull ' + p.return_type + ' ' + p.last) or '' }}
    {%- endfor %}
    {%- if constructor|length > 0 %}

    /**
     * Constructs a new {{class_name}} object
     *
     {%- for p in params|selectattr('mandatory') %}
     {%- include "javadoc_template.java" %}
     {%- endfor %}
     */
    public {{class_name}}({{ constructor|join(', ') }}) {
        this();
        {%- for p in params|selectattr('mandatory') %}
        set{{p.title}}({{p.last}});
        {%- endfor %}
    }
    {%- endif %}
    {%- endif %}

    {%- block setter %}
    {%- endblock%}
}
{% endblock -%}
