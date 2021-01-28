package subway.exception;

public class RequestPermissionDeniedException extends IllegalArgumentException{
    private static final String REQUEST_PERMISSION_DENIED_MESSAGE = "해당 요청에 대한 권한이 없습니다";

    public RequestPermissionDeniedException(){
        super(REQUEST_PERMISSION_DENIED_MESSAGE);
    }

}
