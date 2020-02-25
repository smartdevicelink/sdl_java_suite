     {%- if p.param_doc is not defined %}
     {%- if p.description is defined %}
     {%- for d in p.description %}
     {%- if loop.index == 1 -%}
     * @return {{p.last}} {{d}}
     {%- else -%}
     * {{d}}
     {%- endif -%}{%- endfor -%}
     {%- else %}
     * @return {{p.last}}
     {%- endif -%}
     {%- else -%}
     {%- set l = p.last|length + 8 -%}
     * {% for v in p.param_doc -%}
     {%- if loop.index == 1 -%}
     @return {{p.last}} {{v}}
     {%- else -%}
     * {{v|indent(l,True)}}
     {%- endif -%}{% endfor -%}
     {%- endif -%}
     {%- if p.since is defined %}
     * @since SmartDeviceLink {{p.since}}
     {%- endif %}