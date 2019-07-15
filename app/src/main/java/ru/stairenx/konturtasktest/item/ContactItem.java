package ru.stairenx.konturtasktest.item;

public class ContactItem {

    Long _id;
    String id;
    String name;
    String phone;
    float height;
    String biography;
    Temperament temperament;
    EducationPeriod educationPeriod;

    public ContactItem(String id, String name, String phone, float height, String biography, Temperament temperament, EducationPeriod educationPeriod) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.height = height;
        this.biography = biography;
        this.temperament = temperament;
        this.educationPeriod = educationPeriod;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public float getHeight() {
        return height;
    }

    public String getBiography() {
        return biography;
    }

    public Temperament getTemperament() {
        return temperament;
    }

    public EducationPeriod getEducationPeriod() {
        return educationPeriod;
    }

    @Override
    public String toString() {
        return "ContactItem{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", height=" + height +
                ", biography='" + biography + '\'' +
                ", temperament=" + temperament +
                ", educationPeriod=" + educationPeriod +
                '}';
    }
}
