package com.randstad.nasaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NasaImage {
    public static final String ID = "identifier";
    public static final String CAPTION = "caption";
    public static final String IMAGE = "image";
    public static final String VERSION = "version";

    @Expose
    @SerializedName(ID)
    public String id;

    @Expose
    @SerializedName(CAPTION)
    public String caption;

    @Expose
    @SerializedName(IMAGE)
    public String image;

    @Expose
    @SerializedName(VERSION)
    public String version;
}
