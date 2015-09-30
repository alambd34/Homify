package com.android.homify.model;

public class PreferenceBuilder {
    private long id;
    private String code;//required
    private String description;//optional
    private String name;//required
    private Boolean checked;


    public PreferenceBuilder(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public PreferenceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }


    public PreferenceBuilder setChecked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public Preference build() {
        return new Preference(this);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }


    public Boolean getChecked() {
        return checked;
    }
}