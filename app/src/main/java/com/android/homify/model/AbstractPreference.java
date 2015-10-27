package com.android.homify.model;

/**
 * Created by nickyska on 9/29/2015.
 */
public abstract class AbstractPreference {

    private long id;

//    private String code;

    private String description;

    private String name;

    private Boolean checked;

    private String type;

    private Unit unit;



    public AbstractPreference(String description, String name, Boolean checked, String type) {
//        this.code = code;
        this.description = description;
        this.name = name;
        this.checked = checked;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
