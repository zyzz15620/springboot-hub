package com.total650.springboot_hub.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigUtils {
    private static Dotenv dotenv;

    public static void loadEnv(){
        String env = System.getenv("test.env");
        if(env==null){
            env = "local.env";
        }
        dotenv = Dotenv
                .configure()
                .directory("env")
                .filename(env)
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }
//
//    public static void main(String[] args) {
//        System.out.println(ConfigUtils.getDotenv().get("DB_URL"));
//    }
}
