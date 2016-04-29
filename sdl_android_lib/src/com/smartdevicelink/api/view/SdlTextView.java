package com.smartdevicelink.api.view;

import com.smartdevicelink.proxy.rpc.Show;

import java.util.Arrays;

public class SdlTextView extends SdlView{

    private String[] mTextFields = new String[5];

    public SdlTextView(){
        Arrays.fill(mTextFields, "");
    }

    public void setTextField(int index, String text){
        if(index < mTextFields.length){
            mTextFields[index] = text;
        }
    }

    public void setText(String text){
        clear();
        String[] lines = text.split("\\n");
        System.arraycopy(lines, 0, mTextFields, 0, lines.length);
    }

    public String getText(){
        StringBuilder sb = new StringBuilder();
        String line;
        for(int i = 0; i < mTextFields.length; i++){
            line = mTextFields[i];
            if(!line.equals("")){
                sb.append(mTextFields[i]);
                if(i != mTextFields.length - 1){
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public String[] getTextFields(){
        return mTextFields;
    }

    public String getTextField(int index){
        if(index < mTextFields.length){
            return mTextFields[index];
        } else {
            return null;
        }
    }

    @Override
    public void decorate(Show show) {
        show.setMainField1(mTextFields[0]);
        show.setMainField2(mTextFields[1]);
        show.setMainField3(mTextFields[2]);
        show.setMainField4(mTextFields[3]);
        show.setMediaTrack(mTextFields[4]);
    }

    @Override
    void uploadRequiredImages() {
    }

    @Override
    public void clear() {
        Arrays.fill(mTextFields, "");
    }
}
