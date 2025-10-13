package io.github.manjago.devicemanagementservice.dto;

public class ProblemJson {
    private final String type;
    private final String title;
    private final int status;
    private final String detail;
    private final String instance;

    public ProblemJson(String type, String title, int status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    public static ProblemJson badRequest(String detail, String instance) {
        return new ProblemJson(
                "about:blank",
                "Bad Request",
                400,
                detail,
                instance
        );
    }

    public static ProblemJson internalError(String detail, String instance) {
        return new ProblemJson(
                "about:blank",
                "Internal Server Error",
                500,
                detail,
                instance
        );
    }
}