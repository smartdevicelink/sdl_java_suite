/*
 * Copyright (c) 2017 - {{year}}, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package {{package_name}};
{% for i in imports %}
{%- if i != '' %}
import {{i}};{{ '\n' if loop.last }}
{%- else %}
{{''}}
{%- endif %}
{%- endfor %}
{%- if description is defined or since is defined or see is defined or deprecated is defined %}
/**
 {%- if description is defined %}
 {%- for d in description %}
 * {{d}}
 {%- endfor %}{%- endif %}
 {%- if params is defined and ((kind is defined and kind not in ["response", "simple", "custom"]) or kind is not defined) %}
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 {%- for param in params %}
 *  <tr>
 *      <td>{{param.origin}}</td>
 *      <td>{{param.return_type}}</td>
 *      <td>{%- for d in param.description %}{{d}}{%- endfor %}</td>
 *      <td>{%- if param.mandatory is eq true %}Y{%- else %}N{%- endif %}</td>
 *      <td>{%- for k in param.values %}{{ '{' if loop.first}}"{{k}}": {{param.values[k]}}{{ ', ' if not loop.last else  '}'}}{%- endfor %}</td>
 *      <td>{%- if param.since is defined %}SmartDeviceLink {{param.since}}{%- endif %}</td>
 *  </tr>
 {%- endfor %}
 * </table>
 {%- endif %}
 {%- if description is defined and (see is defined or since is defined) %}
 *
 {%- endif %}{% set prefix = ' * ' %}
 {%- include "javadoc_version_info.java" %}
 */
{%- endif %}
{%- if deprecated is not none %}
@Deprecated
{%- endif %}
{%- block body %}
{% endblock -%}
