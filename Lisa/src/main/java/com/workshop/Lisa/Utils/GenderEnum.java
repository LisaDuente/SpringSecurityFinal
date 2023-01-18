package com.workshop.Lisa.Utils;

public enum GenderEnum {
    MAN("Man"),
    WOMAN("Kvinna"),
    OTHER("Annan/Annat"),
    NO_ANSWER("Vill inte svara");

    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
