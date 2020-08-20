     {%- if p.description is defined %}
     {%- for d in p.description %}
     {%- if loop.index == 1 %}
     * @return {{p.return_type}} {{d}}
     {%- else %}
     * {{d}}
     {%- endif %}{%- endfor %}
     {%- else %}
     * @return {{p.return_type}}
     {%- endif %}
     {%- if p.values is defined and p.values %}
     * {%- for k in p.values %}{{ ' {' if loop.first}}"{{k}}": {{p.values[k]}}{{ ', ' if not loop.last else  '}'}}{%- endfor %}
     {%- endif %}{% set see, deprecated, since, spacing = p.see, p.deprecated, p.since, '    ' %}
     {%- include "javadoc_added_deprecated.java" %}