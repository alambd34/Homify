package com.android.homify.model;

public class PreferenceBuilder {
    private long id;
    // private String code;//required
    private String description;//optional
    private String name;//required
    private Boolean checked;
    private String type;


    public PreferenceBuilder(String name, String type) {
        //this.code = code;
        this.name = name;
        this.type = type;
    }

    public Preference build() {
        return new Preference(this);
    }

    public String getDescription() {
        return description;
    }

    public PreferenceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

//    public String getCode() {
//        return code;
//    }


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public PreferenceBuilder setChecked(Boolean checked) {
        this.checked = checked;
        return this;
    }
}