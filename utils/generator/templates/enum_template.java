{% extends "base_template.java" %}
{% block body %}
public enum {{class_name}} {
    {%- for param in params %}
    {%- if param.description is defined or param.since is defined %}
    /**
     {%- if param.description is defined %}
     {%- for d in param.description %}
     * {{d}}
     {%- endfor %}{% endif -%}
     {%- if param.description is defined and (param.since is defined or param.see is defined) %}
     *
     {%- endif %}
     {%- if param.since is defined %}
     * @since SmartDeviceLink {{param.since}}
     {%- endif %}
     {%- if param.see is defined %}
     * @see {{param.see}}
     {%- endif %}
     */
    {%- endif %}
    {%- if param.deprecated is defined %}
    @Deprecated
    {%- endif %}
    {%- if kind == "simple" %}
    {{param.name}}{{ "," if not loop.last }}
    {%- elif kind == "custom" %}
    {{param.name}}({{param.internal}}){{ "," if not loop.last }}
    {%- elif kind == "complex" %}
    {{param.name}}({{param.value}}, {{param.internal}}){{ "," if not loop.last }}
    {%- endif %}
    {%- endfor %};

    {%- if kind == "simple" %}

    /**
     * Convert String to {{class_name}}
     *
     * @param value String
     * @return {{class_name}}
     */
    {%- if valueForString is defined %}
{{valueForString}}
    {%- else %}
    public static {{class_name}} valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    {%- endif %}
    {%- elif kind == "custom" %}
    {%- if return_type == "String" %}

    private final String INTERNAL_NAME;

    private {{class_name}}(String internalName) {
        this.INTERNAL_NAME = internalName;
    }
    {%- if valueForString is defined %}
{{valueForString}}
    {%- else %}

    public static {{class_name}} valueForString(String value) {
        if (value == null) {
            return null;
        }

        for ({{class_name}} anEnum : EnumSet.allOf({{class_name}}.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    {%- endif %}

    @Override
    public String toString() {
        return INTERNAL_NAME;
    }
    {%- elif return_type == "int" %}

    private final int VALUE;
    /**
     * Private constructor
     */
    private {{class_name}} (int value) {
        this.VALUE = value;
    }

    public static {{class_name}} valueForInt(int value) {
        for ({{class_name}} anEnum : EnumSet.allOf({{class_name}}.class)) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

    public int getValue(){
        return VALUE;
    }
    {%- elif return_type == "bool" %}

    boolean IS_QUERYABLE;

    {{class_name}}(boolean isQueryable) {
        this.IS_QUERYABLE = isQueryable;
    }

    public boolean isQueryable() {
        return IS_QUERYABLE;
    }

    public static {{class_name}} valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    {%- endif %}
    {%- elif kind == "complex" %}
    public static final int                 INVALID_ID = -1;

    private static HashMap<String, Integer> functionMap;

    private final int                       ID;
    private final String                    JSON_NAME;

    private {{class_name}}(int id, String jsonName) {
        this.ID = id;
        this.JSON_NAME = jsonName;
    }

    public int getId(){
        return this.ID;
    }

    @Override
    public String toString() {
        return this.JSON_NAME;
    }

    private static void initFunctionMap() {
        functionMap = new HashMap<String, Integer>(values().length);

        for({{class_name}} value : EnumSet.allOf({{class_name}}.class)) {
            functionMap.put(value.toString(), value.getId());
        }
    }

    public static String getFunctionName(int i) {
        if(functionMap == null) {
            initFunctionMap();
        }

        Iterator<Entry<String, Integer>> iterator = functionMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, Integer> thisEntry = iterator.next();
            if(Integer.valueOf(i).equals(thisEntry.getValue())) {
                return thisEntry.getKey();
            }
        }

    return null;
    }

    public static int getFunctionId(String functionName) {
        if(functionMap == null) {
            initFunctionMap();
        }

        Integer result = functionMap.get(functionName);
        return ( result == null ) ? INVALID_ID : result;
    }

    /**
     * This method gives the corresponding {{class_name}} enum value for a string RPC
     *
     * @param name String value represents the name of the RPC
     * @return {{class_name}} represents the equivalent enum value for the provided string
     */
    public static {{class_name}} getEnumForString(String name) {
        for({{class_name}} value : EnumSet.allOf({{class_name}}.class)) {
            if(value.JSON_NAME.equals(name)){
                return value;
            }
        }
        return null;
    }
    {%- endif %}
    {%- if scripts is defined %}
    {%- for s in scripts %}
{{s}}
    {% endfor %}
    {%- endif %}
}
{% endblock -%}