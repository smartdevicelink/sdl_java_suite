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

    /**
     * Sets the ps portion of the RdsData class
     *
     * @param ps
     * Program Service Name.
     */
    public void setPs(String ps) {
        setValue(KEY_PS, ps);
    }

    /**
     * Gets the ps portion of the RdsData class
     *
     * @return String - Program Service Name.
     */
    public String getPs() {
        return getString(KEY_PS);
    }

    /**
     * Sets the rt portion of the RdsData class
     *
     * @param rt
     * Radio Text.
     */
    public void setRt(String rt) {
        setValue(KEY_RT, rt);
    }

    /**
     * Gets the rt portion of the RdsData class
     *
     * @return String - Radio Text.
     */
    public String getRt() {
        return getString(KEY_RT);
    }

    /**
     * Sets the ct portion of the RdsData class
     *
     * @param ct
     * The clock text in UTC format as YYYY-MM-DDThh:mm:ss.sTZD.
     */
    public void setCt(String ct) {
        setValue(KEY_CT, ct);
    }

    /**
     * Gets the ct portion of the RdsData class
     *
     * @return String - The clock text in UTC format as YYYY-MM-DDThh:mm:ss.sTZD.
     */
    public String getCt() {
        return getString(KEY_CT);
    }

    /**
     * Sets the pi portion of the RdsData class
     *
     * @param pi
     * Program Identification - the call sign for the radio station.
     */
    public void setPi(String pi) {
        setValue(KEY_PI, pi);
    }

    /**
     * Gets the pi portion of the RdsData class
     *
     * @return String - Program Identification - the call sign for the radio station.
     */
    public String getPi() {
        return getString(KEY_PI);
    }

    /**
     * Sets the reg portion of the RdsData class
     *
     * @param reg
     * Region.
     */
    public void setReg(String reg) {
        setValue(KEY_REG, reg);
    }

    /**
     * Gets the reg portion of the RdsData class
     *
     * @return String - Region.
     */
    public String getReg() {
        return getString(KEY_REG);
    }

    /**
     * Sets the tp portion of the RdsData class
     *
     * @param tp
     * Traffic Program Identification - Identifies a station that offers traffic.
     */
    public void setTp(Boolean tp) {
        setValue(KEY_TP, tp);
    }

    /**
     * Gets the tp portion of the RdsData class
     *
     * @return Boolean - Traffic Program Identification - Identifies a station that offers traffic.
     */
    public Boolean getTp() {
        return getBoolean(KEY_TP);
    }

    /**
     * Sets the ta portion of the RdsData class
     *
     * @param ta
     * Traffic Announcement Identification - Indicates an ongoing traffic announcement.
     */
    public void setTa(Boolean ta) {
        setValue(KEY_TA, ta);
    }

    /**
     * Gets the ta portion of the RdsData class
     *
     * @return Boolean - Traffic Announcement Identification - Indicates an ongoing traffic announcement.
     */
    public Boolean getTa() {
        return getBoolean(KEY_TA);
    }

    /**
     * Sets the pty portion of the RdsData class
     *
     * @param pty
     * The program type - The region should be used to differentiate between EU and North America program types.
     */
    public void setPty(Integer pty) {
        setValue(KEY_PTY, pty);
    }

    /**
     * Gets the pty portion of the RdsData class
     *
     * @return Integer - The program type.
     * The region should be used to differentiate between EU and North America program types.
     */
    public Integer getPty() {
        return getInteger(KEY_PTY);
    }
}
