package com.smartdevicelink.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * {@code Localization} is a class to create Resources objects for
 * a specific language and/or region if resources of a language/region
 * other than the default language/region is required.
 */
public class Localization {
    private String language;
    private String region;
    private Locale locale;
    private Resources resources;

    /**
     * Return the language string passed to this instance.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Return the region string used for this instance.
     */
    public String getRegion() {
        return this.region;
    }

    /**
     * Return the locale object used by the Resource object of this instance.
     */
    private Locale getLocale() {
        return this.locale;
    }

    /**
     * Return the resource object created by this instance.
     */
    public Resources getResources() {
        return this.resources;
    }

    /**
     * Creates an instance with a resource object using default locale.
     * @param context A context required to create a new resource object.
     */
    public Localization(@NonNull Context context) {
        this(context, Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
    }

    /**
     * Creates an instance with a resource object using the specified language code.
     * @param context A context required to create a new resource object.
     * @param language The language code used for the locale object.
     */
    public Localization(@NonNull Context context, @NonNull String language) {
        this(context, language, "");
    }

    /**
     * Creates an instance with a resource object using the specified language and region code.
     * @param context A context required to create a new resource object.
     * @param language The language code used for the locale object.
     * @param region The region code used for the locale object.
     */
    public Localization(@NonNull Context context, @NonNull String language, @NonNull String region) {
        Resources resources = context.getResources();
        AssetManager assets = resources.getAssets();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration config = new Configuration(resources.getConfiguration());

        Locale locale = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale.Builder builder = new Locale.Builder();
            builder.setLanguage(language.toLowerCase());
            builder.setRegion(region.toUpperCase());
            locale = builder.build();
        } else {
            locale = new Locale(language.toLowerCase(), region.toUpperCase());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        this.language = language;
        this.region = region;
        this.locale = locale;
        this.resources = new Resources(assets, metrics, config);
    }
}
