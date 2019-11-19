package com.example.mysql.models;

public class Person {

    private String user_name;
    private String user_gender;
    private String user_street;
    private String user_state;
    private String user_postcode;

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_street() {
        return user_street;
    }

    public void setUser_street(String user_street) {
        this.user_street = user_street;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_postcode() {
        return user_postcode;
    }

    public void setUser_postcode(String user_postcode) {
        this.user_postcode = user_postcode;
    }

    public Person(String user_name, String user_gender, String user_street, String user_state, String user_postcode) {
        this.user_name = user_name;
        this.user_gender = user_gender;
        this.user_street = user_street;
        this.user_state = user_state;
        this.user_postcode = user_postcode;
    }

    public int get(int position) {
        return position;
    }

    @Override
    public String toString() {
        return "Person{" +
                "user_name='" + user_name + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_street='" + user_street + '\'' +
                ", user_state='" + user_state + '\'' +
                ", user_postcode=" + user_postcode +
                '}';
    }
}