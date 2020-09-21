/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
package com.smartdevicelink.util;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

public abstract class ByteEnumer {

    protected ByteEnumer(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    private final byte value;
    private final String name;

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean equals(ByteEnumer other) {
        return Objects.equals(name, other.getName());
    }

    public boolean eq(ByteEnumer other) {
        return equals(other);
    }

    public byte value() {
        return value;
    }

    public static ByteEnumer get(Vector<?> theList, byte value) {
        Enumeration<?> enumer = theList.elements();
        while (enumer.hasMoreElements()) {
            try {
                ByteEnumer current = (ByteEnumer) enumer.nextElement();
                if (current.getValue() == value) {
                    return current;
                }
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    public static ByteEnumer get(Vector<?> theList, String name) {
        Enumeration<?> enumer = theList.elements();
        while (enumer.hasMoreElements()) {
            try {
                ByteEnumer current = (ByteEnumer) enumer.nextElement();
                if (current.getName().equals(name)) {
                    return current;
                }
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }
}