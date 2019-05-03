package com.ibnsaad.thedcc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRespons {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private String user;

    public class User {

        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("gender")
        @Expose
        private String gender;

        @SerializedName("age")
        @Expose
        private String age;

        @SerializedName("dateOfBirth")
        @Expose
        private String dateOfBirth;

        @SerializedName("knownAs")
        @Expose
        private String knownAs;

        @SerializedName("created")
        @Expose
        private String created;

        @SerializedName("lastActive")
        @Expose
        private String lastActive;

        @SerializedName("city")
        @Expose
        private String city;

        @SerializedName("country")
        @Expose
        private String country;

        @SerializedName("photoUrl")
        @Expose
        private String photoUrl;

        @SerializedName("userType")
        @Expose
        private String userType;

        @SerializedName("likerCount")
        @Expose
        private int likerCount;

        public User() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getKnownAs() {
            return knownAs;
        }

        public void setKnownAs(String knownAs) {
            this.knownAs = knownAs;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLastActive() {
            return lastActive;
        }

        public void setLastActive(String lastActive) {
            this.lastActive = lastActive;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public int getLikerCount() {
            return likerCount;
        }

        public void setLikerCount(int likerCount) {
            this.likerCount = likerCount;
        }
    }


}
