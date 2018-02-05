package com.example.hyusuf.subbook;

import java.io.Serializable;

/**
 * Created by hyusuf on 2018-01-14.
 *
 * This class deals with making a Subscription.
 *
 */

public class Subscriptions implements Serializable {
    private String subName;
    private String subDate;
    private String subCharge;
    private String subComment;

    /**
     * This function makes an instance of a subscription given a Name, Date,Charge,and Comment.
     * @param subName
     * @param subDate
     * @param subCharge
     * @param subComment
     */

    public Subscriptions(String subName, String subDate, String subCharge, String subComment) {
        this.subName = subName;
        this.subDate = subDate;
        this.subCharge = subCharge;
        this.subComment = subComment;
    }

    /**
     * This function returns the subscription's name.
     * @return subName
     */
    public String getSubName() {
        return subName;
    }


    /**
     * This function returns a subscription's date.
     * @return subDate
     */

    public String getSubDate() {
        return subDate;
    }


    /**
     * This function returns a subscription's charge.
     * @return subCharge
     */

    public String getSubCharge() {
        return subCharge;
    }


    /**
     * This function returns a subscription's comment.
     * @return
     */
    public String getSubComment() {
        return subComment;
    }


}
