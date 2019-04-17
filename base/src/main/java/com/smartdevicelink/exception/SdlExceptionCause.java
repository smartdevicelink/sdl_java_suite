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

public enum SdlExceptionCause {
	BLUETOOTH_ADAPTER_NULL,
	BLUETOOTH_DISABLED,
	BLUETOOTH_SOCKET_UNAVAILABLE,
	HEARTBEAT_PAST_DUE,
	INCORRECT_LIFECYCLE_MODEL,
	INVALID_ARGUMENT,
	INVALID_RPC_PARAMETER,
	PERMISSION_DENIED,
	RESERVED_CORRELATION_ID,
	SDL_CONNECTION_FAILED,
	SDL_PROXY_CYCLED,
	SDL_PROXY_DISPOSED,
	SDL_PROXY_OBSOLETE,
	SDL_REGISTRATION_ERROR,
	SDL_UNAVAILABLE,
	INVALID_HEADER,
	DATA_BUFFER_NULL,
	SDL_USB_DETACHED,
	SDL_USB_PERMISSION_DENIED,
	LOCK_SCREEN_ICON_NOT_SUPPORTED,
	;
}
