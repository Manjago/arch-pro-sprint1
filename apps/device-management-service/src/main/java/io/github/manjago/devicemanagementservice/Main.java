package io.github.manjago.devicemanagementservice;

import io.github.manjago.devicemanagementservice.dto.ProblemJson;
import io.github.manjago.devicemanagementservice.exception.ValidationException;
import io.github.manjago.devicemanagementservice.handler.PostDevicesHandler;
import io.github.manjago.devicemanagementservice.util.JsonUtil;

import static spark.Spark.exception;
import static spark.Spark.post;

public class Main {

    public static void main(String[] args) {

        exception(ValidationException.class, (e, req, res) -> {
            res.status(400);
            res.type("application/problem+json");
            final ProblemJson problem = ProblemJson.badRequest(e.getMessage(), req.pathInfo());
            res.body(JsonUtil.toJson(problem));
        });

        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.type("application/problem+json");
            final ProblemJson problem = ProblemJson.internalError(
                    "Internal server error occurred",
                    req.pathInfo()
            );
            res.body(JsonUtil.toJson(problem));
            e.printStackTrace(); // логов пока нет
        });

        post("/devices", new PostDevicesHandler());
    }

}
