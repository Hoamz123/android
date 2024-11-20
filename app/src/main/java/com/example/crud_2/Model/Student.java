package com.example.crud_2.Model;

public class Student {
    public static String male = "MALE",female = "FEMALE";
    private String name;
    private String idStudent;
    private String gentleStudent;
    public Student()
    {}

    public Student(String name, String idStudent, String gentleStudent) {
        this.name = name;
        this.idStudent = idStudent;
        this.gentleStudent = gentleStudent;
    }

    public String getName() {
        return name;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public String getGentleStudent() {
        return gentleStudent;
    }
}
