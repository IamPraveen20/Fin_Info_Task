package com.unit.converter.fin_info_task;

public class Item_model {
    private String Email;
    private String Mobile_num;

    public Item_model(String email, String mobile) {
        Email = email;
        Mobile_num = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobile_num() {
        return Mobile_num;
    }


}
