package com.app.perk99restaurant.faq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FAQModel implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQus() {
        return qus;
    }

    public void setQus(String qus) {
        this.qus = qus;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @SerializedName("qus")
    @Expose
    private String qus;


    @SerializedName("ans")
    @Expose
    private String ans;
}
