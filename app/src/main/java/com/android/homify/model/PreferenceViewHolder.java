package com.android.homify.model;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by nickyska on 9/30/2015.
 */
public class PreferenceViewHolder {

    private CheckBox checkBox;
    private TextView textView;

    public PreferenceViewHolder() {
    }

    public PreferenceViewHolder(TextView textView, CheckBox checkBox) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
