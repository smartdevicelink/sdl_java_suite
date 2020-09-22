{%- if since is defined and since is not none %}
{%- if begin is defined %}
{{spacing}}{{begin}}
{%- endif %}
{%- if deprecated is defined and deprecated is not none %}
{{spacing}}{{prefix}}@since SmartDeviceLink {{history[0].since}}
{{spacing}}{{prefix}}@deprecated in SmartDeviceLink {{since}}
{%- elif history is defined and history is not none %}
{{spacing}}{{prefix}}@since SmartDeviceLink {{history[0].since}}
{%- else %}
{{spacing}}{{prefix}}@since SmartDeviceLink {{since}}
{%- endif %}
{%- if see is defined %}
{{spacing}}{{prefix}}@see {{see}}
{%- endif %}
{%- if end is defined %}
{{spacing}}{{end}}
{%- endif %}
{%- endif %}