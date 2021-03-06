package my.video.control;

public class PCSResponse {

    private boolean satisfied;
    private String message;

    public PCSResponse(boolean satisfied, String message) {
        this.satisfied = satisfied;
        this.message = message;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public String getMessage() {
        return message;
    }
}
