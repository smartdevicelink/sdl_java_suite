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
     {%- if p.since is defined %}
     * @since SmartDeviceLink {{p.since}}
     {%- endif %}