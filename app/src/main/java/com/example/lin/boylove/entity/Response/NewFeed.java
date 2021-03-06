package com.example.lin.boylove.entity.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewFeed {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

//    int likes, comments, propic, postpic;
//    String name, time, status;

//    public NewFeed(int id, int likes, int comments, int propic, int postpic, String name, String time, String status) {
//        this.id = id;
//        this.likes = likes;
//        this.comments = comments;
//        this.propic = propic;
//        this.postpic = postpic;
//        this.name = name;
//        this.time = time;
//        this.status = status;
//    }

//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }
//
//    public int getComments() {
//        return comments;
//    }
//
//    public void setComments(int comments) {
//        this.comments = comments;
//    }
//
//    public int getPropic() {
//        return propic;
//    }
//
//    public void setPropic(int propic) {
//        this.propic = propic;
//    }
//
//    public int getPostpic() {
//        return postpic;
//    }
//
//    public void setPostpic(int postpic) {
//        this.postpic = postpic;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
