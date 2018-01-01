package com.example.dell.slowchat.HttpReqeust;

import java.util.List;

/**
 * Created by dell on 2017/12/27.
 */

public class TestData {
    private String image;
    private String sex;
    private String name;
    private List<city> city;
    private List<language> language;
    private int id;

    public TestData(String image, String sex, String name, List<city> cities, List<language> languages, int id) {
        this.image = image;
        this.sex = sex;
        this.name = name;
        this.city = cities;
        this.language = languages;
        this.id = id;
    }

    public static class city{
        private int id;
        private String data;

        public city(String data) {
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class language{
        private int id;
    }

    public List<city> getCities() {
        return city;
    }

    public void setCities(List<city> cities) {
        this.city = cities;
    }
}


//public class TestData {
//    private int id;
//    private String data;
//
//    public TestData(int id,String data) {
//        this.id=id;
//        this.data = data;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//}
