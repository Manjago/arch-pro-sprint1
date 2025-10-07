package io.github.manjago.devicemanagementservice;

import static spark.Spark.post;

public class Main {

    public static void main(String[] args) {
        post("/devices", new PostDevicesHandler());
    }

}
