package com.total650.springboot_hub;

public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        student.setId(1);
        student.setName("DUC");

        System.out.println(student.getId());
        System.out.println(student.getName());
    }
}
