package com.smartdevicelink.test;

import com.smartdevicelink.proxy.rpc.enums.FileType;

import java.net.URI;

public class TestValues {
	public static final boolean                        GENERAL_BOOLEAN                        = true;
	public static final byte[]                         GENERAL_BYTE_ARRAY                     = new byte[0];
	public static final String                         GENERAL_STRING                         = "test";
	public static final FileType                       GENERAL_FILETYPE                       = FileType.BINARY;
	public static final URI                            GENERAL_URI   	                      = URI.create("http://www.google.com");
}
