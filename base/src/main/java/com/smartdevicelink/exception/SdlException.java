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
package com.smartdevicelink.exception;


public class SdlException extends Exception {

    private static final long serialVersionUID = 5922492291870772815L;

    protected Throwable detail = null;
    private SdlExceptionCause _sdlExceptionCause = null;

    public SdlException(String msg, SdlExceptionCause exceptionCause) {
        super(msg);
        _sdlExceptionCause = exceptionCause;
    }

    public SdlException(String msg, Throwable ex, SdlExceptionCause exceptionCause) {
        super(msg + " --- Check inner exception for diagnostic details");
        detail = ex;
        _sdlExceptionCause = exceptionCause;
    }

    public SdlException(Throwable ex) {
        super(ex.getMessage());
        detail = ex;
    }

    public SdlExceptionCause getSdlExceptionCause() {
        return _sdlExceptionCause;
    }

    public Throwable getInnerException() {
        return detail;
    }

    public String toString() {
        String ret = this.getClass().getName();
        ret += ": " + this.getMessage();
        if (this.getSdlExceptionCause() != null) {
            ret += "\nSdlExceptionCause: " + this.getSdlExceptionCause().name();
        }
        if (detail != null) {
            ret += "\nnested: " + detail.toString();
            detail.printStackTrace();
        }
        return ret;
    }
}
