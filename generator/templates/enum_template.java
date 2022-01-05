{%extends"base_template.java"%}
        {%block body%}
public enum {{class_name}}{
        {%-for param in params%}
        {%-if param.description is defined or param.since is defined%}
        /**
         {%- if param.description is defined %}
         {%- for d in param.description %}
         * {{d}}
         {%- endfor %}{% endif -%}
         {%- if param.description is defined and (param.since is defined or param.see is defined) %}
         *
         {%- endif %}{% set see, deprecated, since, history, spacing, prefix = param.see, param.deprecated, param.since, param.history, '    ', ' * ' %}
         {%- include "javadoc_version_info.java" %}
         */
        {%-endif%}
        {%-if param.deprecated is defined%}
@Deprecated
    {%-endif%}
            {%-if kind=="simple"%}
            {{param.name}}{{","if not loop.last}}
            {%-elif kind=="custom"%}
            {{param.name}}({{param.internal}}){{","if not loop.last}}
            {%-endif%}
            {%-endfor%};

            {%-if kind=="simple"%}

/**
 * Convert String to {{class_name}}
 *
 * @param value String
 * @return {{class_name}}
 */
public static {{class_name}}valueForString(String value){
        try{
        return valueOf(value);
        }catch(Exception e){
        return null;
        }
        }
        {%-elif kind=="custom"%}

private final String VALUE;

/**
 * Private constructor
 */
private {{class_name}}(String value){
        this.VALUE=value;
        }

/**
 * Convert String to {{class_name}}
 *
 * @param value String
 * @return {{class_name}}
 */
public static {{class_name}}valueForString(String value){
        if(value==null){
        return null;
        }

        for({{class_name}}anEnum:EnumSet.allOf({{class_name}}.class)){
        if(anEnum.toString().equals(value)){
        return anEnum;
        }
        }
        return null;
        }

/**
 * Return String value of element
 *
 * @return String
 */
@Override
public String toString(){
        return VALUE;
        }
        {%-endif%}
        }
        {%endblock-%}