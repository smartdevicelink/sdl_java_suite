{%- if (see is defined and see is not none) or (deprecated is defined and deprecated is not none) or (since is defined and since is not none) %}
{%- if begin is defined %}
{{spacing}}{{begin}}
{%- endif %}
{%- if deprecated is defined and deprecated is not none and since is defined and since is not none %}
{{spacing}} * @deprecated in SmartDeviceLink {{since}}
{%- elif deprecated is defined and deprecated is not none %}
{{spacing}} * @deprecated
{%- elif since is defined and since is not none %}
{{spacing}} * @since SmartDeviceLink {{since}}
{%- endif %}
{%- if see is defined %}
{{spacing}} * @see {{see}}
{%- endif %}
{%- if end is defined %}
{{spacing}} {{end}}
{%- endif %}
{%- endif %}