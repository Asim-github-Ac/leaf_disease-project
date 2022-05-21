package com.example.saadi1.leafdetection;

public class Model {
    String name,ins1,ins2,ins3;

    public Model(String name, String ins1, String ins2, String ins3) {
        this.name = name;
        this.ins1 = ins1;
        this.ins2 = ins2;
        this.ins3 = ins3;
    }

    public Model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIns1() {
        return ins1;
    }

    public void setIns1(String ins1) {
        this.ins1 = ins1;
    }

    public String getIns2() {
        return ins2;
    }

    public void setIns2(String ins2) {
        this.ins2 = ins2;
    }

    public String getIns3() {
        return ins3;
    }

    public void setIns3(String ins3) {
        this.ins3 = ins3;
    }
}
