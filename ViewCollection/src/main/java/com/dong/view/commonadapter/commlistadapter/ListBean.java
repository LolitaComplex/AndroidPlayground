package com.dong.view.commonadapter.commlistadapter;

/**
 * Created by Doing on 2016/9/12.
 *
 */
public class ListBean {

    private String name;
    private String sex;
    private int age;
    private String hobby;

    public ListBean(String name, String sex, int age, String hobby) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
