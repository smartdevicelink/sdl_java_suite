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

package android.os;

@Deprecated
public class Parcel {

    public void writeInt(int data){

    }

    public void writeByteArray(byte[] bytes){

    }

    public void writeString(String data){

    }

    public void writeParcelable(Parcelable p, int flags){

    }

    public int readInt(){
        return 0;
    }

    public String readString(){
        return "hello";
    }

    public byte[] readByteArray(byte[] array){
        return array;
    }

    public Parcelable readParcelable(ClassLoader loader){
        return  null;
    }

    public int dataAvail(){
        return 0;
    }
}
