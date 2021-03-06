package com.example.lin.boylove.entity.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ryne on 20/09/2017.
 */

public class Online {
    @SerializedName("users")
    @Expose
    private List<User> lstUser;

    @SerializedName("meta")
    @Expose
    private Pagination pagination;

    public List<User> getLstUser() {
        return lstUser;
    }

    public void setLstUser(List<User> lstUser) {
        this.lstUser = lstUser;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
