package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class RdsData extends RPCStruct{
    public static final String KEY_PS= "PS";
    public static final String KEY_RT= "RT";
    public static final String KEY_CT= "CT";
    public static final String KEY_PI= "PI";
    public static final String KEY_PTY= "PTY";
    public static final String KEY_TP= "TP";
    public static final String KEY_TA= "TA";
    public static final String KEY_REG= "REG";

    public RdsData() {
    }

    public RdsData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setPs(String ps) {
        setValue(KEY_PS, ps);
    }
    public String getPs() {
        return getString(KEY_PS);
    }

    public void setRt(String rt) {
        setValue(KEY_RT, rt);
    }
    public String getRt() {
        return getString(KEY_RT);
    }

    public void setCt(String ct) {
        setValue(KEY_CT, ct);
    }
    public String getCt() {
        return getString(KEY_CT);
    }

    public void setPi(String pi) {
        setValue(KEY_PI, pi);
    }
    public String getPi() {
        return getString(KEY_PI);
    }

    public void setReg(String reg) {
        setValue(KEY_REG, reg);
    }
    public String getReg() {
        return getString(KEY_REG);
    }

    public void setTp(Boolean tp) {
        setValue(KEY_TP, tp);
    }

    public Boolean getTp() {
        return getBoolean(KEY_TP);
    }

    public void setTa(Boolean ta) {
        setValue(KEY_TA, ta);
    }

    public Boolean getTa() {
        return getBoolean(KEY_TA);
    }

    public void setPty(Integer pty) {
        setValue(KEY_PTY, pty);
    }

    public Integer getPty() {
        return getInteger(KEY_PTY);
    }
}
