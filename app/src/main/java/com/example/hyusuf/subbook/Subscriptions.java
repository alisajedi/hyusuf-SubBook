package com.example.hyusuf.subbook;

import java.io.Serializable;

/**
 * Created by hyusuf on 2018-01-14.
 */

public class Subscriptions implements Serializable {
    private String subName;
    private String subDate;
    private String subCharge;
    private String subComment;

    public Subscriptions(String subName, String subDate, String subCharge, String subComment) {
        this.subName = subName;
        this.subDate = subDate;
        this.subCharge = subCharge;
        this.subComment = subComment;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubDate() {
        return subDate;
    }

    public void setSubDate(String subDate) {
        this.subDate = subDate;
    }

    public String getSubCharge() {
        return subCharge;
    }

    public void setSubCharge(String subCharge) {
        this.subCharge = subCharge;
    }

    public String getSubComment() {
        return subComment;
    }

    public void setSubComment(String subComment) {
        this.subComment = subComment;
    }

    @Override
    public String toString() {
        return "Subscriptions{" +
                "subName='" + subName + '\'' +
                ", subDate='" + subDate + '\'' +
                ", subCharge='" + subCharge + '\'' +
                ", subComment='" + subComment + '\'' +
                '}';
    }
}
