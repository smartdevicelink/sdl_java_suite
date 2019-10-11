package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Used to set an alternate template layout to a window.
 * @since 6.0
 */
public class TemplateConfiguration extends RPCStruct {
    public static final String KEY_TEMPLATE = "template";
    public static final String KEY_DAY_COLOR_SCHEME = "dayColorScheme";
    public static final String KEY_NIGHT_COLOR_SCHEME = "nightColorScheme";

    /**
     * Constructs a new TemplateConfiguration object
     * @param template Predefined or dynamically created window template.
     *         Currently only predefined window template layouts are defined.
     */
    public TemplateConfiguration(@NonNull String template) {
        this();
        setTemplate(template);
    }

    /**
     * Constructs a new TemplateConfiguration object indicated by the Hashtable
     * parameter
     *
     * @param hash The Hashtable to use
     */
    public TemplateConfiguration(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new TemplateConfiguration object
     */
    public TemplateConfiguration() {
    }

    /**
     * Gets the template.
     *
     * @return String -an Predefined or dynamically created window template. Currently only predefined window template layouts are defined.
     */
    public String getTemplate() {
        return getString(KEY_TEMPLATE);
    }

    /**
     * Sets the template. It can be Predefined or dynamically created window template. Currently only predefined window template layouts are defined.
     *
     * @param template Predefined or dynamically created window template. Currently only predefined window template layouts are defined.
     */
    public void setTemplate(@NonNull String template) {
        setValue(KEY_TEMPLATE, template);
    }

    /**
     * Gets the dayColorScheme.
     *
     * @return TemplateColorScheme
     */
    @SuppressWarnings("unchecked")
    public TemplateColorScheme getDayColorScheme() {
        return (TemplateColorScheme) getObject(TemplateColorScheme.class, KEY_DAY_COLOR_SCHEME);
    }

    /**
     * Sets the dayColorScheme.
     *
     * @param dayColorScheme TemplateColorScheme for the day
     */
    public void setDayColorScheme(TemplateColorScheme dayColorScheme) {
        setValue(KEY_DAY_COLOR_SCHEME, dayColorScheme);
    }

    /**
     * Gets the nightColorScheme.
     *
     * @return TemplateColorScheme
     */
    @SuppressWarnings("unchecked")
    public TemplateColorScheme getNightColorScheme() {
        return (TemplateColorScheme) getObject(TemplateColorScheme.class, KEY_NIGHT_COLOR_SCHEME);
    }

    /**
     * Sets the nightColorScheme.
     *
     * @param nightColorScheme TemplateColorScheme for the night
     */
    public void setNightColorScheme(TemplateColorScheme nightColorScheme) {
        setValue(KEY_NIGHT_COLOR_SCHEME, nightColorScheme);
    }
}
