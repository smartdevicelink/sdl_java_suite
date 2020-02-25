{% extends "base_template.java" %}

{% block body %}
public class {{class_name}} extends {{extends_class}} {
    {%- if params is defined %}
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
    {%- if p.name is defined %}
    private {% if p.modifier is defined %}{{p.modifier}} {% endif %}{{p.return_type}} {{p.name}}{% if p.value is defined %} = {{p.value}}{% endif %};
    {%- else %}
    public static final String {{p.key}} = "{{p.origin}}";
    {%- endif %}
    {%- endfor %}
    {%- endif %}

    {% if remove_constructor is not defined %}
    /**
     * Constructs a new {{class_name}} object
     */
    {%- block constructor_simple %}
    {% endblock %}
    {%- endif %}

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
     {% include "javadoc_template.java" %}
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

    {%- block constructor_complex %}
    {% endblock -%}

    {%- if params is defined %}
    {%- block setter %}
    {% endblock -%}
    {%- endif %}

    {%- if scripts is defined %}
    {%- for s in scripts %}
{{s}}
    {% endfor %}
    {%- endif %}
}
{% endblock -%}
