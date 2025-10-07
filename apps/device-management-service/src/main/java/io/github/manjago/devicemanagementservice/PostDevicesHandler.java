
package io.github.manjago.devicemanagementservice;

import spark.Request;
import spark.Response;
import spark.Route;

public class PostDevicesHandler implements Route {

    @Override
    public Object handle(Request req, Response res) throws Exception {
        return "Hello World";
    }

    
}
