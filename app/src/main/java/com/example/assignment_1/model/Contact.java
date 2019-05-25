package com.example.assignment_1.model;

public class Contact {

    private String id, fullName, phone, email;

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

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if(!(obj instanceof  Contact))
            return false;
        Contact other = (Contact) obj;
        return id == null ? false : id.equals(other.id);
    }

    public String getID() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
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
