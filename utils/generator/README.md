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
    * if the name contains a `-` (dash) then it should be replaced with `_` (underscore)
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

### Constants with field:

In case of at least one name of constant was modified according the rules described below (by adding `_` before digit, replacing `-` with `_` and replacing `sync`) then original name is passed as parameter to constant.

Constant definition:
```java
    [name]("[original_name]")
```
Where `[name]` is the `"name"` attribute of `<element>`, `[original_name]` is the original name of attribute before modification.

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
