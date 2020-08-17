     {%- if p.description is defined %}
     {%- for d in p.description %}
     {%- if loop.index == 1 %}
     * @param {{p.last}} {{d}}
     {%- else %}
     * {{d}}
     {%- endif %}{%- endfor %}
     {%- else %}
     * @param {{p.last}}
     {%- endif %}
     {%- if p.values is defined and p.values %}
     * {%- for k in p.values %}{{ ' {' if loop.first}}"{{k}}": {{p.values[k]}}{{ ', ' if not loop.last else  '}'}}{%- endfor %}
     {%- endif %}
     {%- if p.deprecated is defined and p.deprecated is not none and p.since is defined and p.since is not none %}
     * @deprecated in SmartDeviceLink {{p.since}}
     {%- elif p.deprecated is defined and p.deprecated is not none %}
     * @deprecated
     {%- elif p.since is defined and p.since is not none %}
     * @since SmartDeviceLink {{p.since}}
     {%- endif %}