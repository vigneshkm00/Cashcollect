package com.example.nivetha_zuch508.cashcollect;

public class Admin_det {
    String name;
    long password;
    int access;
    public Admin_det(){

    }


    public int getAccess() {
        return access;
    }

    public Admin_det(String name, long password, int access){
        this.name = name;
        this.password = password;
        this.access = access;
    }
    public String getName() {
        return name;
    }

    public long getPassword() {
        return password;
    }
}
