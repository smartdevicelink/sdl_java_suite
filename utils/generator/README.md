# Proxy Library RPC Generator

## Overview

This script provides the possibility to auto-generate Java code based on a given SDL MOBILE_API XML specification.

## Requirements

The script requires **Python 3** pre-installed on the host system. The minimal supported Python 3 version is **3.7.6**. It may work on versions back to 3.5 (the minimal version that has not yet reached [the end-of-life](https://devguide.python.org/devcycle/#end-of-life-branches)), but this is not supported and may break in the future.

Required libraries are described in `requirements.txt` and should be pre-installed by the command:
```shell script
python3 -m pip install -r requirements.txt
```
Please also make sure before usage the 'utils/generator/rpc_spec' Git submodule is successfully initialized, because the script uses the XML parser provided there.
```shell script
git submodule update --init
```

## Usage
```shell script
usage: generator.py [-h] [-v] [-xml SOURCE_XML] [-xsd SOURCE_XSD]
                    [-d OUTPUT_DIRECTORY] [-t [TEMPLATES_DIRECTORY]]
                    [-r REGEX_PATTERN] [--verbose] [-e] [-s] [-m] [-y] [-n]

Proxy Library RPC Generator

optional arguments:
  -h, --help            show this help message and exit
  -v, --version         print the version and exit
  -xml SOURCE_XML, --source-xml SOURCE_XML, --input-file SOURCE_XML
                        should point to MOBILE_API.xml
  -xsd SOURCE_XSD, --source-xsd SOURCE_XSD
  -d OUTPUT_DIRECTORY, --output-directory OUTPUT_DIRECTORY
                        define the place where the generated output should be
                        placed
  -t [TEMPLATES_DIRECTORY], --templates-directory [TEMPLATES_DIRECTORY]
                        path to directory with templates
  -r REGEX_PATTERN, --regex-pattern REGEX_PATTERN
                        only elements matched with defined regex pattern will
                        be parsed and generated
  --verbose             display additional details like logs etc
  -e, --enums           only specified elements will be generated, if present
  -s, --structs         only specified elements will be generated, if present
  -m, -f, --functions   only specified elements will be generated, if present
  -y, --overwrite       force overwriting of existing files in output
                        directory, ignore confirmation message
  -n, --skip            skip overwriting of existing files in output
                        directory, ignore confirmation message
```

# Java Transformation rules

## Overview
These are the general transformation rules for RPC classes of SDL Java Suite Library. The description of base classes, already included in the library, is not provided here, for details please view the source code. 

The JavaDoc is used for inline documentation of generated code. All non-XML values should follow Contributing to SDL Projects [CONTRIBUTING.md](ttps://github.com/smartdevicelink/sdl_android/blob/master/.github/CONTRIBUTING.md)

These rules based on the current `develop` branch state (commit:`7e6a16c027bcdd0fb523a9993dc59b0171167aea`) of [`smartdevicelink/sdl_java_suite`](https://github.com/smartdevicelink/sdl_java_suite) repository.

## Output Directory Structure and Package definitions

The generator script creates corresponding RPC classes for `<enum>`, `<struct>` and `<function>` elements of `MOBILE_API.xml`.
According to existing structure of Java Suite the output directory will contain following folders and files:

* com
    * proxy
        * rpc
            * enums
                * `[- all <enum> classes except FunctionID -]`
            * `[- all <struct> classes -]`
            * `[- all <function> classes -]`

Each Enum class should be stored as a single script file in the folder named `com/smartdevicelink/rpc/enums` and the name of the script file should be equal to the value from the `"name"` attribute of `<enum>` followed by the extension `.java`. FunctionID enum generation is skipped as discussed due to high complexity of structure. 

Example:
```shell script
# <enum name="ImageType" />
com/smartdevicelink/proxy/rpc/enums/ImageType.java
```

Each Enum class should include the package definition:
```java
package com.smartdevicelink.proxy.rpc.enums;
``` 

Each Struct or Function class should be stored as a single script file in the folder named `com/smartdevicelink/proxy/rpc` and the name of the script file should be equal to the value from the `"name"` attribute of `<struct>` or `<function>` (followed by additional suffix `Response` if the `"name"` doesn't end with it and the `"messagetype"` attribute is set to `response`) followed by the extension `.java`.

Example:
```shell script
# <struct name="VehicleDataResult" />
com/smartdevicelink/proxy/rpc/VehicleDataResult.java

# <function name="AddCommand" messagetype="request" />
com/smartdevicelink/proxy/rpc/AddCommand.java
# <function name="AddCommand" messagetype="response" />
com/smartdevicelink/proxy/rpc/AddCommandResponse.java
# <function name="OnLanguageChange" messagetype="notification" />
com/smartdevicelink/proxy/rpc/OnLanguageChange.java
```

The package definition for Struct or Function classes is:
```java
package com.smartdevicelink.proxy.rpc;
``` 

## The License Header

All files should start from the comment with the license information.

```java
/*
 * Copyright (c) 2017 - [year], SmartDeviceLink Consortium, Inc.
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
```
Where `[year]` in the copyright line is the current year.

## `<enum>`

The name of the class is the value from the `"name"` attribute of `<enum>`.

The class should have the next JavaDoc comment:
```java
/**
 * [description]
 *
 * @deprecated
 * @since SmartDeviceLink [since_version]
 * @see [see_reference]
 */
```
Where:
* `[description]` is `<description>` of the current `<enum>`, if exists.
* `@deprecated` indicates the deprecation state if the `"deprecated"` attribute exists and is "true".
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@see` shows the custom reference in `[see_reference]`.

The class should have the `@Deprecated` decorator if the `"deprecated"` attribute of the `<enum>` exists and is "true".

The set of `<element>` should be mapped to the set of Enum constants. Based on the `<element>` attributes, constants could be with or without fields.

The following list are general rules for constant names and its fields:
1. The `"name"` attribute of `<element>` is the default name of the constant.
    * if the name starts from digit, the leading `_` (underscore) symbol should be added.
    * if the name consists of `-` (dash) then it replaced with `_` (underscore)
1. Uses of the "sync" prefix shall be replaced with "sdl" (where it would not break functionality). E.g. `SyncMsgVersion -> SdlMsgVersion`. This applies to member variables and their accessors. The key used when creating the RPC message JSON should match that of the RPC Spec.

The constant definition could have the next JavaDoc comment:
```java
/**
 * [description]
 *
 * @since SmartDeviceLink [since_version]
 * @see [see_reference]
 */
```
Where:
* `[description]` is `<description>` of the current `<element>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@see` shows the custom reference in `[see_reference]`.

The constant definition should have the `@Deprecated` decorator if the `"deprecated"` attribute exists and is "true".

### Constants without field:

This type of enums doesn't require constructor and requires additional method `valueForString` to be defined. It should return the Enum constant based on its string name, or `null` if the constant is not found.
```java
    /**
     * Convert String to [enum_name]
     *
     * @param value String
     * @return [enum_name]
     */
    public static [enum_name] valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
```
Where `[enum_name]` is the `"name"` attribute of `<enum>`

Example:

XML:
```xml
    <enum name="GlobalProperty" since="1.0">
        <description>The different global properties.</description>
        <element name="USER_LOCATION" since="6.0">
            <description>Location of the user's seat of setGlobalProperties</description>
        </element>
        <element name="HELPPROMPT" since="1.0">
            <description>The property helpPrompt of setGlobalProperties</description>
        </element>
        <element name="TIMEOUTPROMPT" since="1.0">
            <description>The property timeoutPrompt of setGlobalProperties</description>
        </element>
        <element name="VRHELPTITLE" since="2.0">
            <description>The property vrHelpTitle of setGlobalProperties</description>
        </element>
        <element name="VRHELPITEMS" since="2.0">
            <description>The property array of vrHelp of setGlobalProperties</description>
        </element>
        <element name="MENUNAME" since="3.0">
            <description>The property in-app menu name of setGlobalProperties</description>
        </element>
        <element name="MENUICON" since="3.0">
            <description>The property in-app menu icon of setGlobalProperties</description>
        </element>
        <element name="KEYBOARDPROPERTIES" since="3.0">
            <description>The on-screen keyboard configuration of setGlobalProperties</description>
        </element>
    </enum>
```

Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc.enums;

/**
 * The different global properties.
 *
 * @since SmartDeviceLink 1.0.0
 */
public enum GlobalProperty {
    /**
     * Location of the user's seat of setGlobalProperties
     *
     * @since SmartDeviceLink 6.0.0
     */
    USER_LOCATION,
    /**
     * The property helpPrompt of setGlobalProperties
     *
     * @since SmartDeviceLink 1.0.0
     */
    HELPPROMPT,
    /**
     * The property timeoutPrompt of setGlobalProperties
     *
     * @since SmartDeviceLink 1.0.0
     */
    TIMEOUTPROMPT,
    /**
     * The property vrHelpTitle of setGlobalProperties
     *
     * @since SmartDeviceLink 2.0.0
     */
    VRHELPTITLE,
    /**
     * The property array of vrHelp of setGlobalProperties
     *
     * @since SmartDeviceLink 2.0.0
     */
    VRHELPITEMS,
    /**
     * The property in-app menu name of setGlobalProperties
     *
     * @since SmartDeviceLink 3.0.0
     */
    MENUNAME,
    /**
     * The property in-app menu icon of setGlobalProperties
     *
     * @since SmartDeviceLink 3.0.0
     */
    MENUICON,
    /**
     * The on-screen keyboard configuration of setGlobalProperties
     *
     * @since SmartDeviceLink 3.0.0
     */
    KEYBOARDPROPERTIES;

    /**
     * Convert String to GlobalProperty
     *
     * @param value String
     * @return GlobalProperty
     */
    public static GlobalProperty valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
```

### Constants with field based on `"value"` attribute

This type of enums has field based on `"value"` attribute of `<element>`. In case if the `"value"` attribute exists, this attribute should be passed as the `int` or `String` constant field.

#### Constants with `int` value type

Constant definition:
```java
    [name]([value])
```
Where `[name]` is the `"name"` attribute of `<element>`, `[value]` is the `"value"` attribute.

Private field:
```java
    private final int VALUE;
```

The private constructor should be defined to accept the value from the constant and and set the private field.
```java
    /**
     * Private constructor
     */
    private [enum_name](int value) {
        this.VALUE = value;
    }
```
Where `[enum_name]` is the `"name"` attribute of `<enum>`.

The `getValue` method should be defined to return the private field value.
```java
    /**
     * Return value of element
     *
     * @return int
     */
    public int getValue(){
        return VALUE;
    }
```

The additional `valueForInt` should be defined. It should return the Enum constant based on the private field above, or `null` if the constant is not found.
```java
    /**
     * Convert int to {{class_name}}
     *
     * @param value int
     * @return {{class_name}}
     */
    public static [enum_name] valueForInt(int value) {
        for ([enum_name] anEnum : EnumSet.allOf([enum_name].class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
```
Where `[enum_name]` is the `"name"` attribute of `<enum>`.

The `valueForInt` method requires the import of `EnumSet` collection:
```java
import java.util.EnumSet;
```

Full example:

XML:
```xml
    <enum name="PredefinedWindows" since="6.0">
        <element name="DEFAULT_WINDOW" value="0">
            <description>The default window is a main window pre-created on behalf of the app.</description>
        </element>
        <element name="PRIMARY_WIDGET" value="1">
            <description>The primary widget of the app.</description>
        </element>
    </enum>
```

Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * @since SmartDeviceLink 6.0.0
 */
public enum PredefinedWindows {
    /**
     * The default window is a main window pre-created on behalf of the app.
     */
    DEFAULT_WINDOW(0),
    /**
     * The primary widget of the app.
     */
    PRIMARY_WIDGET(1);

    private final int VALUE;

    /**
     * Private constructor
     */
    private PredefinedWindows (int value) {
        this.VALUE = value;
    }

    /**
     * Convert int to PredefinedWindows
     *
     * @param value int
     * @return PredefinedWindows
     */
    public static PredefinedWindows valueForInt(int value) {
        for (PredefinedWindows anEnum : EnumSet.allOf(PredefinedWindows.class)) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * Return value of element
     *
     * @return int
     */
    public int getValue(){
        return VALUE;
    }
}
```

#### Constants with `String` value type

Constant definition:
```java
    [name]("[value]")
```
Where `[name]` is the `"name"` attribute of `<element>`, `[value]` is the `"value"` attribute.

Private field:
```java
    private final String VALUE;
```

The private constructor should be defined to accept the value from the constant and and set the private field.
```java
    private [enum_name](String value) {
        this.VALUE = value;
    }
```
Where `[enum_name]` is the `"name"` attribute of `<enum>`.

The `toString` method should be overridden to return the private field instead of the constant name.
```java
    @Override
    public String toString() {
        return VALUE;
    }
```

The additional `valueForString` should be defined. It should return the Enum constant based on the private field above, or `null` if the constant is not found.
```java
    /**
     * Convert String to [enum_name]
     *
     * @param value String
     * @return [enum_name]
     */
    public static [enum_name] valueForString(String value) {
        if (value == null) {
            return null;
        }

        for ([enum_name] anEnum : EnumSet.allOf([enum_name].class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
```
Where `[enum_name]` is the `"name"` attribute of `<enum>`.

The `valueForString` method requires the import of `EnumSet` collection:
```java
import java.util.EnumSet;
```

Full example:

XML:
```xml
    <enum name="Dimension" since="2.0">
        <description>The supported dimensions of the GPS</description>
        <element name="NO_FIX" internal_name="Dimension_NO_FIX">
            <description>No GPS at all</description>
        </element>
        <element name="2D" internal_name="Dimension_2D">
            <description>Longitude and latitude</description>
        </element>
        <element name="3D" internal_name="Dimension_3D">
            <description>Longitude and latitude and altitude</description>
        </element>
    </enum>
```

Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * The supported dimensions of the GPS
 *
 * @since SmartDeviceLink 2.0.0
 */
public enum Dimension {
    /**
     * No GPS at all
     */
    NO_FIX("NO_FIX"),
    /**
     * Longitude and latitude
     */
    _2D("2D"),
    /**
     * Longitude and latitude and altitude
     */
    _3D("3D");

    private final String VALUE;

    /**
     * Private constructor
     */
    private Dimension(String value) {
        this.VALUE = value;
    }

    /**
     * Convert String to Dimension
     *
     * @param value String
     * @return Dimension
     */
    public static Dimension valueForString(String value) {
        if (value == null) {
            return null;
        }

        for (Dimension anEnum : EnumSet.allOf(Dimension.class)) {
            if (anEnum.toString().equals(value)) {
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
    public String toString() {
        return VALUE;
    }
}
```

## `<struct>`

The Struct class should extend the base `RPCStruct` class:
```java
import com.smartdevicelink.proxy.RPCStruct;
```

The name of the class is the value from the `"name"` attribute of `<struct>`. 

The class should have the next JavaDoc comment:
```java
/**
 * [description]
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>[param_name]</td>
 *      <td>[param_type|List<[param_type]>]</td>
 *      <td>[param_description]</td>
 *      <td>[Y|N]</td>
 *      <td>SmartDeviceLink [param_since]</td>
 *  </tr>
 * </table>
 *
 * @deprecated
 * @since SmartDeviceLink [since_version]
 * @see [see_reference]
 */
```
Where:
* `[description]` is `<description>` of the current `<struct>`, if exists.
* `@deprecated` indicates the deprecation state if the `"deprecated"` attribute exists and is "true".
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@see` shows the custom reference in `[see_reference]`.
* The `Parameter List` table should include all set of `<param>`.
* `[param_name]` is `"name"` attribute of the `<param>`.
* `[param_type]` is `"type"` attribute of the `<param>`, `[List<[param_type]>]` applied if `"array"` attribute of `<param>` is "true".
* `[param_description]` is `<description>` of the `<param>`, could be empty if not exists.
* `[Y|N]` means exactly `Y` character, if `"mandatory"` attribute of the `<param>` exists and is "true", `N` character otherwise.
* `[param_since]` should be present, if the `"since"` attribute of the `<param>` exists, and `[since]` is the `Major.Minor.Patch` formatted value of this attribute.

There are all Enum classes that are used in the represented structure should be additionally imported. 

The class should have the `@Deprecated` decorator if the `"deprecated"` attribute of the `<enum>` exists and is "true".

The set of `<param>` should be mapped to the `public static final String` fields of the new class by following rules:

1. The name of the fields is the `SCREAMING_SNAKE_CASE` formatted value of the `"name"` attribute of `<param>` with the `KEY_` prefix.
1. The value of the fields is the value of the `"name"` attribute of `<param>`
1. Uses of the "sync" prefix shall be replaced with "sdl" (where it would not break functionality). E.g. `KEY_SYNC_MSG_VERSION -> KEY_SDL_MSG_VERSION`. This applies to member variables and their accessors. The key used when creating the RPC message JSON should match that of the RPC Spec.

Field definition template:
```java
/**
 * @deprecated
 * @see [see_reference]
 */
@Deprecated
public static final String [normalized_name] = "[name]";
```
Where:
* `[normalized_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`.
* `[name]` is the `"name"` attribute of `<param>`.
* `@deprecated` indicates the deprecation state if the `"deprecated"` attribute exists and is "true".
* `@see` shows the custom reference in `[see_reference]`.

The field definition should have the `@Deprecated` decorator if the `"deprecated"` attribute of the `<param>` exists and is "true".

The Struct class contains 3 different constructors:
* without parameters.
* with `Hashtable` parameter.
* with all required parameters, based on `"mandatory"` attribute of the `<param>`

### Constructor without parameters

Template:
```java
    /**
     * Constructs a new [name] object
     */
    public [name]() { }
```
Where `[name]` is the value from the `"name"` attribute of `<struct>`.

### Constructor with `Hashtable` parameter

This constructor requires the import of `Hashtable` class
```java
import java.util.Hashtable;
```

Template:
```java
    /**
     * Constructs a new [name] object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public [name](Hashtable<String, Object> hash) {
        super(hash);
    }
```
Where `[name]` is the value from the `"name"` attribute of `<struct>`.

### Constructor with all required parameters, based on `"mandatory"` attribute of the `<param>`
This constructor requires the import of `NonNull` annotation
```java
import android.support.annotation.NonNull;
```

The constructor should include all set of `<param>` with the `"mandatory"` attribute is "true". JavaDoc should include all constructor parameters and the constructor should call all corresponding setters inside itself.

Template:
```java
    /**
     * Constructs a new [name] object
     *
     * @param [param_name] [description]
     * [description]
     */
    public [name](@NonNull [param_type|List<[param_type]>] [param_name]) {
        this();
        [setter_name]([param_name]);
    }
```
Where:
* `[name]` is the value from the `"name"` attribute of `<struct>`.
* `[param_name]` is `"name"` attribute of the `<param>`.
* `[param_type]` is `"type"` attribute of the `<param>`, `[List<[param_type]>]` applied if `"array"` attribute of `<param>` is "true".
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[setter_name]` is the name of the corresponding setter method

For each `<param>` the getter and setter methods should be defined in the class:

1. The name of the setter/getter is the `PascalCase` formatted value of the `"name"` attribute with the `get` prefix for the getter, for the setter the prefix should be `set`.
1. Uses of the "sync" prefix shall be replaced with "sdl" (where it would not break functionality). E.g. `SyncMsgVersion -> SdlMsgVersion`. This applies to member variables and their accessors. The key used when creating the RPC message JSON should match that of the RPC Spec.
1. The setter method:
    * Accepts the single parameter with the type defined in the `"type"` attribute and the name defined in the `"name"` attribute of the `<param>`;
    * The parameter should be decorated by `@NonNull` annotation if the `"mandatory"` attribute of the `<param>` is "true";
    * Should call the `setValue` method, where the first parameter is the value of the corresponding static field described above, the second is the value passed into setter;
1. The getter method:
    * If `"type"` attribute of the `<param>` has the one of `Boolean`, `Integer` or `String`
        * the getter should call and return the corresponding `getBoolean`, `getInteger` or `getString` method, the parameter of that method is the value of the corresponding static field described above;
    * If `"type"` attribute of the `<param>` is `Float`:
        * the getter should call the `getValue` method, the parameter of that method is the value of the corresponding static field described above;
        * the getter should return `SdlDataTypeConverter.objectToFloat(object)` where the `object` is the value previously received from `getValue`;
    * If the `<param>` has the `"type"` attribute value as the one of `<enum>` or `<struct>` name:
        * The getter should call and return the result of the `getObject` method, where the first parameter is the corresponding Struct or Enum class, the second is the value of the corresponding static field described above;

Setter template:
```java
    /**
     * Sets the [name].
     *
     * @param [name] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    public void [setter_name]([type|List<[type]>] [name]) {
        setValue([field_name], [name]);
    }
```
Where:
* `[description]` is `<description>` of the `<param>`, if exists.
* `[name]` is `"name"` attribute of the `<param>`.
* `[type]` is `"type"` attribute of the `<param>`, `[List<[type]>]` applied if `"array"` attribute is "true".
* `[setter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `set` prefix.
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.


`Boolean`, `Integer` or `String` type getter template:
```java
     /**
     * Gets the [name].
     *
     * @return [type|List<[type]>] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    @SuppressWarnings("unchecked")
    public [type|List<[type]>] [getter_name]() {
        return get[type]([field_name]);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[getter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `get` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.
* `[type]` is `"type"` attribute of the `<param>`, `[List<[type]>]` applied if `"array"` attribute is "true".
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@SuppressWarnings("unchecked")` applied if `"array"` attribute is "true".


`Float` type getter template:
```java
     /**
     * Gets the [name].
     *
     * @return [Float|List<Float>] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    @SuppressWarnings("unchecked")
    public Float|List<Float> [getter_name]() {
        Object object = getValue([field_name]);
        return SdlDataTypeConverter.objectToFloat(object);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[getter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `get` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.
* `[List<Float>]` applied if `"array"` attribute is "true".
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@SuppressWarnings("unchecked")` applied if `"array"` attribute is "true".

`<enum>` or `<struct>` type getter template:
```java
     /**
     * Gets the [name].
     *
     * @return [type|List<[type]>] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    @SuppressWarnings("unchecked")
    public [type|List<[type]>] [getter_name]() {
        return ([type|List<[type]>]) getObject([type].class, [field_name]);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[type]` is `"type"` attribute of the `<param>`, `[List<[type]>]` applied if `"array"` attribute is "true".
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@SuppressWarnings("unchecked")` applied if `"array"` attribute is "true".
* `[getter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `get` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.

Take a note if some parameters have `"array"` attribute is true, the class requires the `List` collection to be imported:
```java
import java.util.List;
```

### Below is the full example of the Struct class with simple and Enum parameters inside:

XML:
```xml
    <struct name="VehicleDataResult" since="2.0">
        <description>Individual published data request result</description>
        <param name="dataType" type="VehicleDataType" mandatory="true">
            <description>Defined published data element type.</description>
        </param>
        <param name="resultCode" type="VehicleDataResultCode" mandatory="true">
            <description>Published data result code.</description>
        </param>
        <param name="oemCustomDataType" type="String" mandatory="false" since="6.0">
            <description>Type of requested oem specific parameter </description>
        </param>
    </struct>
```

The Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;

import java.util.Hashtable;

/**
 * Individual published data request result
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>dataType</td>
 *      <td>VehicleDataType</td>
 *      <td>Defined published data element type.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>resultCode</td>
 *      <td>VehicleDataResultCode</td>
 *      <td>Published data result code.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>oemCustomDataType</td>
 *      <td>String</td>
 *      <td>Type of requested oem specific parameter</td>
 *      <td>N</td>
 *      <td>SmartDeviceLink 6.0.0</td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 2.0.0
 */
public class VehicleDataResult extends RPCStruct {
    public static final String KEY_DATA_TYPE = "dataType";
    public static final String KEY_RESULT_CODE = "resultCode";
    public static final String KEY_OEM_CUSTOM_DATA_TYPE = "oemCustomDataType";

    /**
     * Constructs a new VehicleDataResult object
     */
    public VehicleDataResult() { }

    /**
     * Constructs a new VehicleDataResult object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public VehicleDataResult(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new VehicleDataResult object
     *
     * @param dataType Defined published data element type.
     * @param resultCode Published data result code.
     */
    public VehicleDataResult(@NonNull VehicleDataType dataType, @NonNull VehicleDataResultCode resultCode) {
        this();
        setDataType(dataType);
        setResultCode(resultCode);
    }

    /**
     * Sets the dataType.
     *
     * @param dataType Defined published data element type.
     */
    public void setDataType(@NonNull VehicleDataType dataType) {
        setValue(KEY_DATA_TYPE, dataType);
    }

    /**
     * Gets the dataType.
     *
     * @return dataType Defined published data element type.
     */
    public VehicleDataType getDataType() {
        return (VehicleDataType) getObject(VehicleDataType.class, KEY_DATA_TYPE);
    }

    /**
     * Sets the resultCode.
     *
     * @param resultCode Published data result code.
     */
    public void setResultCode(@NonNull VehicleDataResultCode resultCode) {
        setValue(KEY_RESULT_CODE, resultCode);
    }

    /**
     * Gets the resultCode.
     *
     * @return resultCode Published data result code.
     */
    public VehicleDataResultCode getResultCode() {
        return (VehicleDataResultCode) getObject(VehicleDataResultCode.class, KEY_RESULT_CODE);
    }

    /**
     * Sets the oemCustomDataType.
     *
     * @param oemCustomDataType Type of requested oem specific parameter
     * @since SmartDeviceLink 6.0.0
     */
    public void setOemCustomDataType(String oemCustomDataType) {
        setValue(KEY_OEM_CUSTOM_DATA_TYPE, oemCustomDataType);
    }

    /**
     * Gets the oemCustomDataType.
     *
     * @return oemCustomDataType Type of requested oem specific parameter
     * @since SmartDeviceLink 6.0.0
     */
    public String getOemCustomDataType() {
        return getString(KEY_OEM_CUSTOM_DATA_TYPE);
    }
    

}
```

### Below is the full example of the Struct class with arrays in parameters:

XML:
```xml
    <struct name="HMIPermissions" since="2.0">
        <param name="allowed" type="HMILevel" minsize="0" maxsize="100" array="true" mandatory="true">
            <description>A set of all HMI levels that are permitted for this given RPC.</description>
        </param>
        <param name="userDisallowed" type="HMILevel" minsize="0" maxsize="100" array="true" mandatory="true">
            <description>A set of all HMI levels that are prohibited for this given RPC.</description>
        </param>
    </struct>

```

Output:

```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.Hashtable;
import java.util.List;

/**
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>allowed</td>
 *      <td>List<HMILevel></td>
 *      <td>A set of all HMI levels that are permitted for this given RPC.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>userDisallowed</td>
 *      <td>List<HMILevel></td>
 *      <td>A set of all HMI levels that are prohibited for this given RPC.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 2.0.0
 */
public class HMIPermissions extends RPCStruct {
    public static final String KEY_ALLOWED = "allowed";
    public static final String KEY_USER_DISALLOWED = "userDisallowed";

    /**
     * Constructs a new HMIPermissions object
     */
    public HMIPermissions() { }

    /**
     * Constructs a new HMIPermissions object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public HMIPermissions(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new HMIPermissions object
     *
     * @param allowed A set of all HMI levels that are permitted for this given RPC.
     * @param userDisallowed A set of all HMI levels that are prohibited for this given RPC.
     */
    public HMIPermissions(@NonNull List<HMILevel> allowed, @NonNull List<HMILevel> userDisallowed) {
        this();
        setAllowed(allowed);
        setUserDisallowed(userDisallowed);
    }

    /**
     * Sets the allowed.
     *
     * @param allowed A set of all HMI levels that are permitted for this given RPC.
     */
    public void setAllowed(@NonNull List<HMILevel> allowed) {
        setValue(KEY_ALLOWED, allowed);
    }

    /**
     * Gets the allowed.
     *
     * @return allowed A set of all HMI levels that are permitted for this given RPC.
     */
    @SuppressWarnings("unchecked")
    public List<HMILevel> getAllowed() {
        return (List<HMILevel>) getObject(HMILevel.class, KEY_ALLOWED);
    }

    /**
     * Sets the userDisallowed.
     *
     * @param userDisallowed A set of all HMI levels that are prohibited for this given RPC.
     */
    public void setUserDisallowed(@NonNull List<HMILevel> userDisallowed) {
        setValue(KEY_USER_DISALLOWED, userDisallowed);
    }

    /**
     * Gets the userDisallowed.
     *
     * @return userDisallowed A set of all HMI levels that are prohibited for this given RPC.
     */
    @SuppressWarnings("unchecked")
    public List<HMILevel> getUserDisallowed() {
        return (List<HMILevel>) getObject(HMILevel.class, KEY_USER_DISALLOWED);
    }
    

}
```

### Below is the full example of the Struct class with Float and Enum parameters:

XML:
```xml
    <struct name="FuelRange" since="5.0">
        <param name="type" type="FuelType" mandatory="false"/>
        <param name="range" type="Float" minvalue="0" maxvalue="10000" mandatory="false">
            <description>
                The estimate range in KM the vehicle can travel based on fuel level and consumption.
            </description>
        </param>
    </struct>
```

Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.FuelType;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

/**
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>type</td>
 *      <td>FuelType</td>
 *      <td></td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>range</td>
 *      <td>Float</td>
 *      <td>The estimate range in KM the vehicle can travel based on fuel level and consumption.</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 5.0.0
 */
public class FuelRange extends RPCStruct {
    public static final String KEY_TYPE = "type";
    public static final String KEY_RANGE = "range";

    /**
     * Constructs a new FuelRange object
     */
    public FuelRange() { }

    /**
     * Constructs a new FuelRange object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public FuelRange(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the type.
     *
     
     * @param type
     */
    public void setType(FuelType type) {
        setValue(KEY_TYPE, type);
    }

    /**
     * Gets the type.
     *
     
     * @return type
     */
    public FuelType getType() {
        return (FuelType) getObject(FuelType.class, KEY_TYPE);
    }

    /**
     * Sets the range.
     *
     * @param range The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    public void setRange(Float range) {
        setValue(KEY_RANGE, range);
    }

    /**
     * Gets the range.
     *
     * @return range The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    public Float getRange() {
        Object object = getValue(KEY_RANGE);
        return SdlDataTypeConverter.objectToFloat(object);
    }
    

}
```

## `<function>`

Based on the value of the `"messagetype"` attribute of `<function>`the Function class should extend the class `RPCRequest`, `RPCResponse` or `RPCNotification`:
```java
import com.smartdevicelink.proxy.RPCRequest;
// or
import com.smartdevicelink.proxy.RPCResponse;
// or
import com.smartdevicelink.proxy.RPCNotification;
```

The name of the class is the value from the `"name"` attribute of `<function>` (followed by additional suffix `Response` if the `"name"` doesn't end with it and `"messagetype"` attribute is set to `response`.

The script should import `FunctionID` Enum class to get the `functionID` hex value of the current RPC function. The key of the required `<element>` of `FunctionID` enum is the value of the `"functionID"` attribute of `<function>`.
```java
import com.smartdevicelink.protocol.enums.FunctionID;
```

The class should have the next JavaDoc comment:
```java
/**
 * [description]
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>[param_name]</td>
 *      <td>[param_type|List<[param_type]>]</td>
 *      <td>[param_description]</td>
 *      <td>[Y|N]</td>
 *      <td>SmartDeviceLink [param_since]</td>
 *  </tr>
 * </table>
 *
 * @deprecated
 * @since SmartDeviceLink [since_version]
 * @see [see_reference]
 */
```
Where:
* `[description]` is `<description>` of the current `<struct>`, if exists.
* `@deprecated` indicates the deprecation state if the `"deprecated"` attribute exists and is "true".
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `@see` shows the custom reference in `[see_reference]`.
* The `Parameter List` table should include all set of `<param>`.
* `[param_name]` is `"name"` attribute of the `<param>`.
* `[param_type]` is `"type"` attribute of the `<param>`, `[List<[param_type]>]` applied if `"array"` attribute of `<param>` is "true".
* `[param_description]` is `<description>` of the `<param>`, could be empty if not exists.
* `[Y|N]` means exactly `Y` character, if `"mandatory"` attribute of the `<param>` exists and is "true", `N` character otherwise.
* `[param_since]` should be present, if the `"since"` attribute of the `<param>` exists, and `[since]` is the `Major.Minor.Patch` formatted value of this attribute.

There are all Enum classes that are used in the represented structure should be additionally imported. 

The class should have the `@Deprecated` decorator if the `"deprecated"` attribute of the `<enum>` exists and is "true".

The set of `<param>` should be mapped to the `public static final String` fields of the new class by following rules:

1. The name of the fields is the `SCREAMING_SNAKE_CASE` formatted value of the `"name"` attribute of `<param>` with the `KEY_` prefix.
1. The value of the fields is the value of the `"name"` attribute of `<param>`
1. Uses of the "sync" prefix shall be replaced with "sdl" (where it would not break functionality). E.g. `KEY_SYNC_MSG_VERSION -> KEY_SDL_MSG_VERSION`. This applies to member variables and their accessors. The key used when creating the RPC message JSON should match that of the RPC Spec.
1. The exclusion are `<param>` with name `success`, `resultCode` and `info` of `<function>` with the attribute `messagetype="response"`, in this case they should be omitted because they are already predefined in the parent class.

Field definition template:
```java
/**
 * @deprecated
 * @see [see_reference]
 */
@Deprecated
public static final String [normalized_name] = "[name]";
```
Where:
* `[normalized_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`.
* `[name]` is the `"name"` attribute of `<param>`.
* `@deprecated` indicates the deprecation state if the `"deprecated"` attribute exists and is "true".
* `@see` shows the custom reference in `[see_reference]`.

The field definition should have the `@Deprecated` decorator if the `"deprecated"` attribute of the `<param>` exists and is "true".

The Function class contains 3 different constructors:
* without parameters.
* with `Hashtable` parameter.
* with all required parameters, based on `"mandatory"` attribute of the `<param>`

The `response` Function class has additional constructor with `success` and `resultCode` mandatory parameters. Order of this parameters is dependent of order in XML. 

### Constructor without parameters

This constructor should pass the corresponding stringified constant value of the `FunctionID` Enum class into parent class.

Template:
```java
    /**
     * Constructs a new [name] object
     */
    public [name]() {
        super(FunctionID.[normalized_name].toString());
    }
```
Where:
* `[name]` is `"name"` attribute of the `<function>`.
* `[normalized_name]` is the `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of the `<function>`.

### Constructor with `Hashtable` parameter

This constructor requires the import of `Hashtable` class
```java
import java.util.Hashtable;
```

Template:
```java
    /**
     * Constructs a new [name] object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public [name](Hashtable<String, Object> hash) {
        super(hash);
    }
```
Where `[name]` is the value from the `"name"` attribute of `<function>`.

### Constructor with all required parameters, based on `"mandatory"` attribute of the `<param>`
This constructor requires the import of `NonNull` annotation
```java
import android.support.annotation.NonNull;
```

The constructor should include all set of `<param>` with the `"mandatory"` attribute is "true". JavaDoc should include all constructor parameters and the constructor should call all corresponding setters inside itself.

Template:
```java
    /**
     * Constructs a new [name] object
     *
     * @param [param_name] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    public [name](@NonNull [param_type|List<[param_type]>] [param_name]) {
        this();
        [setter_name]([param_name]);
    }
```
Where:
* `[name]` is the value from the `"name"` attribute of `<function>`.
* `[param_name]` is `"name"` attribute of the `<param>`.
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[param_type]` is `"type"` attribute of the `<param>`, `[List<[param_type]>]` applied if `"array"` attribute of `<param>` is "true".
* `[setter_name]` is the name of the corresponding setter method

For each `<param>` the getter and setter methods should be defined in the class:

1. The name of the setter/getter is the `PascalCase` formatted value of the `"name"` attribute with the `get` prefix for the getter, for the setter the prefix should be `set`.
1. Uses of the "sync" prefix shall be replaced with "sdl" (where it would not break functionality). E.g. `SyncMsgVersion -> SdlMsgVersion`. This applies to member variables and their accessors. The key used when creating the RPC message JSON should match that of the RPC Spec.
1. The setter method:
    * Accepts the single parameter with the type defined in the `"type"` attribute and the name defined in the `"name"` attribute of the `<param>`;
    * The parameter should be decorated by `@NonNull` annotation if the `"mandatory"` attribute of the `<param>` is "true";
    * Should call the `setParameters` method, where the first parameter is the value of the corresponding static field described above, the second is the value passed into setter;
1. The getter method:
    * If `"type"` attribute of the `<param>` has the one of `Boolean`, `Integer` or `String`
        * the getter should call and return the corresponding `getBoolean`, `getInteger` or `getString` method, the parameter of that method is the value of the corresponding static field described above;
    * If `"type"` attribute of the `<param>` is `Float`:
        * the getter should call the `getValue` method, the parameter of that method is the value of the corresponding static field described above;
        * the getter should return `SdlDataTypeConverter.objectToFloat(object)` where the `object` is the value previously received from `getValue`;
    * If the `<param>` has the `"type"` attribute value as the one of `<enum>` or `<struct>` name:
        * The getter should call and return the result of the `getObject` method, where the first parameter is the corresponding Struct or Enum class, the second is the value of the corresponding static field described above;
1. The exclusion are `<param>` with name `success`, `resultCode` and `info` of `<function>` with the attribute `messagetype="response"`, in this case they should be omitted because they are already predefined in the parent class.

Setter template:
```java
    /**
     * Sets the [name].
     *
     * @param [name] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    public void [setter_name]([type|List<[type]>] [name]) {
        setParameters([field_name], [name]);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[type]` is `"type"` attribute of the `<param>`, `[List<[type]>]` applied if `"array"` attribute is "true".
* `[setter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `set` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.


`Boolean`, `Integer` or `String` type getter template:
```java
     /**
     * Gets the [name].
     *
     * @return [type|List<[type]>] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    @SuppressWarnings("unchecked")
    public [type|List<[type]>] [getter_name]() {
        return get[type]([field_name]);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[getter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `get` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.
* `[type]` is `"type"` attribute of the `<param>`, `[List<[type]>]` applied if `"array"` attribute is "true".
* `@SuppressWarnings("unchecked")` applied if `"array"` attribute is "true".


`Float` type getter template:
```java
     /**
     * Gets the [name].
     *
     * @return [Float|List<Float>] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    @SuppressWarnings("unchecked")
    public Float|List<Float> [getter_name]() {
        Object object = getValue([field_name]);
        return SdlDataTypeConverter.objectToFloat(object);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[getter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `get` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.
* `[List<Float>]` applied if `"array"` attribute is "true".
* `@SuppressWarnings("unchecked")` applied if `"array"` attribute is "true".

`<enum>` or `<struct>` type getter template:
```java
     /**
     * Gets the [name].
     *
     * @return [type|List<[type]>] [description]
     * [description]
     * @since SmartDeviceLink [since_version]
     */
    @SuppressWarnings("unchecked")
    public [type|List<[type]>] [getter_name]() {
        return ([type|List<[type]>]) getObject([type].class, [field_name]);
    }
```
Where:
* `[name]` is `"name"` attribute of the `<param>`.
* `[description]` is `<description>` of the `<param>`, if exists.
* `@since` should be present, if the `"since"` attribute exists, and `[since_version]` is the `Major.Minor.Patch` formatted value of this attribute.
* `[type]` is `"type"` attribute of the `<param>`, `[List<[type]>]` applied if `"array"` attribute is "true".
* `@SuppressWarnings("unchecked")` applied if `"array"` attribute is "true".
* `[getter_name]` is the `PascalCase` formatted `"name"` attribute of the `<param>` with the `get` prefix.
* `[field_name]` is the normalized and `SCREAMING_SNAKE_CASE` formatted `"name"` attribute of `<param>`, `[name]` is the `"name"` attribute.

Take a note if some parameters have `"array"` attribute is true, the class requires the `List` collection to be imported:
```java
import java.util.List;
```

### Below are full examples for Request, Response and Notification.
#### Request Example:

XML:
```xml
<function name="AddCommand" functionID="AddCommandID" messagetype="request" since="1.0">
    <description>
        Adds a command to the in application menu.
        Either menuParams or vrCommands must be provided.
    </description>
     
    <param name="cmdID" type="Integer" minvalue="0" maxvalue="2000000000" mandatory="true">
        <description>unique ID of the command to add.</description>
    </param>
     
    <param name="menuParams" type="MenuParams" mandatory="false">
        <description>Optional sub value containing menu parameters</description>
    </param>
     
    <param name="vrCommands" type="String" minsize="1" maxsize="100" maxlength="99" array="true" mandatory="false">
        <description>
            An array of strings to be used as VR synonyms for this command.
            If this array is provided, it may not be empty.
        </description>
    </param>
     
    <param name="cmdIcon" type="Image" mandatory="false" since="2.0">
        <description>
            Image struct determining whether static or dynamic icon.
            If omitted on supported displays, no (or the default if applicable) icon shall be displayed.
        </description>
    </param>
     
</function>
```

Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

/**
 * Adds a command to the in application menu. Either menuParams or vrCommands must be
 * provided.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>cmdID</td>
 *      <td>Integer</td>
 *      <td>unique ID of the command to add.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>menuParams</td>
 *      <td>MenuParams</td>
 *      <td>Optional sub value containing menu parameters</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>vrCommands</td>
 *      <td>List<String></td>
 *      <td>An array of strings to be used as VR synonyms for this command. If this array is provided,it may not be empty.</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>cmdIcon</td>
 *      <td>Image</td>
 *      <td>Image struct determining whether static or dynamic icon. If omitted on supported displays,no (or the default if applicable) icon shall be displayed.</td>
 *      <td>N</td>
 *      <td>SmartDeviceLink 2.0.0</td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 1.0.0
 */
public class AddCommand extends RPCRequest {
    public static final String KEY_CMD_ID = "cmdID";
    public static final String KEY_MENU_PARAMS = "menuParams";
    public static final String KEY_VR_COMMANDS = "vrCommands";
    public static final String KEY_CMD_ICON = "cmdIcon";

    /**
     * Constructs a new AddCommand object
     */
    public AddCommand() {
        super(FunctionID.ADD_COMMAND.toString());
    }

    /**
     * Constructs a new AddCommand object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public AddCommand(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new AddCommand object
     *
     * @param cmdID unique ID of the command to add.
     */
    public AddCommand(@NonNull Integer cmdID) {
        this();
        setCmdID(cmdID);
    }

    /**
     * Sets the cmdID.
     *
     * @param cmdID unique ID of the command to add.
     */
    public void setCmdID(@NonNull Integer cmdID) {
        setParameters(KEY_CMD_ID, cmdID);
    }

    /**
     * Gets the cmdID.
     *
     * @return Integer unique ID of the command to add.
     */
    public Integer getCmdID() {
        return getInteger(KEY_CMD_ID);
    }

    /**
     * Sets the menuParams.
     *
     * @param menuParams Optional sub value containing menu parameters
     */
    public void setMenuParams(MenuParams menuParams) {
        setParameters(KEY_MENU_PARAMS, menuParams);
    }

    /**
     * Gets the menuParams.
     *
     * @return MenuParams Optional sub value containing menu parameters
     */
    public MenuParams getMenuParams() {
        return (MenuParams) getObject(MenuParams.class, KEY_MENU_PARAMS);
    }

    /**
     * Sets the vrCommands.
     *
     * @param vrCommands An array of strings to be used as VR synonyms for this command. If this array is provided,
     * it may not be empty.
     */
    public void setVrCommands(List<String> vrCommands) {
        setParameters(KEY_VR_COMMANDS, vrCommands);
    }

    /**
     * Gets the vrCommands.
     *
     * @return List<String> An array of strings to be used as VR synonyms for this command. If this array is provided,
     * it may not be empty.
     */
    @SuppressWarnings("unchecked")
    public List<String> getVrCommands() {
        return (List<String>) getObject(String.class, KEY_VR_COMMANDS);
    }

    /**
     * Sets the cmdIcon.
     *
     * @param cmdIcon Image struct determining whether static or dynamic icon. If omitted on supported displays,
     * no (or the default if applicable) icon shall be displayed.
     * @since SmartDeviceLink 2.0.0
     */
    public void setCmdIcon(Image cmdIcon) {
        setParameters(KEY_CMD_ICON, cmdIcon);
    }

    /**
     * Gets the cmdIcon.
     *
     * @return Image Image struct determining whether static or dynamic icon. If omitted on supported displays,
     * no (or the default if applicable) icon shall be displayed.
     * @since SmartDeviceLink 2.0.0
     */
    public Image getCmdIcon() {
        return (Image) getObject(Image.class, KEY_CMD_ICON);
    }

}
```

#### Response Example:

> Please pay attention that no other parameters for this example except "info", "success" and "resultCode", thus they were omitted and only the constructor and other parameters are present)

XML:
```xml
<function name="PerformInteraction" functionID="PerformInteractionID" messagetype="response" since="1.0">
    <param name="success" type="Boolean" platform="documentation" mandatory="true">
        <description> true if successful; false, if failed </description>
    </param>
    
    <param name="resultCode" type="Result" platform="documentation" mandatory="true">
        <description>See Result</description>
        <element name="SUCCESS"/>
        <element name="INVALID_DATA"/>
        <element name="OUT_OF_MEMORY"/>
        <element name="TOO_MANY_PENDING_REQUESTS"/>
        <element name="APPLICATION_NOT_REGISTERED"/>
        <element name="GENERIC_ERROR"/>
        <element name="REJECTED"/>
        <element name="INVALID_ID"/>
        <element name="DUPLICATE_NAME"/>
        <element name="TIMED_OUT"/>
        <element name="ABORTED"/>
        <element name="UNSUPPORTED_RESOURCE"/>
        <element name="WARNINGS"/>
    </param>
    
    <param name="info" type="String" maxlength="1000" mandatory="false" platform="documentation">
        <description>Provides additional human readable info regarding the result.</description>
    </param>
    
    <param name="choiceID" type="Integer" minvalue="0" maxvalue="2000000000" mandatory="false">
        <description>
            ID of the choice that was selected in response to PerformInteraction.
            Only is valid if general result is "success:true".
        </description>
    </param>
    
    <param name="manualTextEntry" type="String" maxlength="500" mandatory="false" since="3.0">
        <description>
            Manually entered text selection, e.g. through keyboard
            Can be returned in lieu of choiceID, depending on trigger source
        </description>
    </param>
    
    <param name="triggerSource" type="TriggerSource" mandatory="false">
        <description>
            See TriggerSource
            Only is valid if resultCode is SUCCESS.
        </description>
    </param>
    
</function>
```

The Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

import java.util.Hashtable;

/**
 * @since SmartDeviceLink 1.0.0
 */
public class PerformInteractionResponse extends RPCResponse {

    /**
     * Constructs a new PerformInteractionResponse object
     */
    public PerformInteractionResponse() {
        super(FunctionID.PERFORM_INTERACTION.toString());
    }

    /**
     * Constructs a new PerformInteractionResponse object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public PerformInteractionResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new PerformInteractionResponse object
     *
     * @param success whether the request is successfully processed
     * @param resultCode additional information about a response returning a failed outcome
     */
    public PerformInteractionResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

}
```

#### Notification Example:

XML:
```xml
<function name="OnLanguageChange" functionID="OnLanguageChangeID" messagetype="notification" since="2.0">
    <param name="language" type="Language" mandatory="true">
        <description>Current SDL voice engine (VR+TTS) language</description>
    </param>
    <param name="hmiDisplayLanguage" type="Language" mandatory="true">
        <description>Current display language</description>
    </param>
</function>
```

The Output:
```java
/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.Language;

import java.util.Hashtable;

/**
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>language</td>
 *      <td>Language</td>
 *      <td>Current SDL voice engine (VR+TTS) language</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>hmiDisplayLanguage</td>
 *      <td>Language</td>
 *      <td>Current display language</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 2.0.0
 */
public class OnLanguageChange extends RPCNotification {
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";

    /**
     * Constructs a new OnLanguageChange object
     */
    public OnLanguageChange() {
        super(FunctionID.ON_LANGUAGE_CHANGE.toString());
    }

    /**
     * Constructs a new OnLanguageChange object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public OnLanguageChange(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnLanguageChange object
     *
     * @param language Current SDL voice engine (VR+TTS) language
     * @param hmiDisplayLanguage Current display language
     */
    public OnLanguageChange(@NonNull Language language, @NonNull Language hmiDisplayLanguage) {
        this();
        setLanguage(language);
        setHmiDisplayLanguage(hmiDisplayLanguage);
    }

    /**
     * Sets the language.
     *
     * @param language Current SDL voice engine (VR+TTS) language
     */
    public void setLanguage(@NonNull Language language) {
        setParameters(KEY_LANGUAGE, language);
    }

    /**
     * Gets the language.
     *
     * @return Language Current SDL voice engine (VR+TTS) language
     */
    public Language getLanguage() {
        return (Language) getObject(Language.class, KEY_LANGUAGE);
    }

    /**
     * Sets the hmiDisplayLanguage.
     *
     * @param hmiDisplayLanguage Current display language
     */
    public void setHmiDisplayLanguage(@NonNull Language hmiDisplayLanguage) {
        setParameters(KEY_HMI_DISPLAY_LANGUAGE, hmiDisplayLanguage);
    }

    /**
     * Gets the hmiDisplayLanguage.
     *
     * @return Language Current display language
     */
    public Language getHmiDisplayLanguage() {
        return (Language) getObject(Language.class, KEY_HMI_DISPLAY_LANGUAGE);
    }

}
```