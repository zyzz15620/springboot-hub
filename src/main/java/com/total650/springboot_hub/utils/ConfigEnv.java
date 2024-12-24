package com.total650.springboot_hub.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigEnv {
    private static Dotenv dotenv;

    public static void loadEnv(){
        String env = System.getenv("TEST_ENV");
        if(env==null){
            env = "local.env";
        }
        dotenv = Dotenv
                .configure()
                .directory(Thread.currentThread().getContextClassLoader().getResource("env").getPath())
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
