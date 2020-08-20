{%- if since is defined and since is not none %}
{%- if begin is defined %}
{{spacing}}{{begin}}
{%- endif %}
{%- if deprecated is defined and deprecated is not none %}
{{spacing}} * @since in SmartDeviceLink {{history[0].since}}
{{spacing}} * @deprecated in SmartDeviceLink {{since}}
{%- elif history is defined and history is not none %}
{{spacing}} * @since in SmartDeviceLink {{history[0].since}}
{%- else %}
{{spacing}} * @since in SmartDeviceLink {{since}}
{%- endif %}
{%- if see is defined %}
{{spacing}} * @see {{see}}
{%- endif %}
{%- if end is defined %}
{{spacing}} {{end}}
{%- endif %}
{%- endif %}