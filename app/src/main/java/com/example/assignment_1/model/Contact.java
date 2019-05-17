package com.example.assignment_1.model;

public class Contact {

    private String fullName, firstName, lastName, phone, email;
    private String id;

    public Contact(String name, String phone) {
        this.fullName = name;
        this.phone = phone;
    }

    public Contact(String id, String fn, String ph, String e) {
        this.id = id;
        this.fullName = fn;
        this.phone = ph;
        this.email = e;
    }

    public String getID() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFirstName(String n) {
        this.firstName = n;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String n) {
        this.lastName = n;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String ph) {
        this.phone = ph;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public String getEmail() {
        return this.email;
    }
}
