 /*
  * Copyright (C) 2006 The Android Open Source Project
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  *
  * Note: This file has been modified from its original form.
  */

package android.util;

@Deprecated
public class Log {


    public static int i(String tag, String message){
        System.out.print("\r\nINFO: " + tag+ " - " + message);
        return 10;


    }
    public static int v(String tag, String message){
        System.out.print("\r\nVERBOSE: " + tag+ " - " + message);
        return 10;


    }
    public static int d(String tag, String message){
        System.out.print("\r\nDEBUG: " + tag+ " - " + message);
        return 10;


    }
    public static int w(String tag, String message){
        System.out.print("\r\nWARN: " + tag+ " - " + message);
        return 10;


    }
    public static int e(String tag, String message){
        System.out.print("\r\nERROR: " + tag+ " - " + message);
        return 10;

    }
    public static int e(String tag, String message, Exception e){
        if(e != null){
            System.out.print("\r\nERROR: " + tag+ " - " + message + " - " + e.getMessage());
        }else{
            System.out.print("\r\nERROR: " + tag+ " - " + message);
        }
        return 10;
    }
    public static int e(String tag, String message, Throwable t){
        if(t != null){
            System.out.print("\r\nERROR: " + tag+ " - " + message + " - " + t.getMessage());
        }else{
            System.out.print("\r\nERROR: " + tag+ " - " + message);
        }
        return 10;
    }

}
