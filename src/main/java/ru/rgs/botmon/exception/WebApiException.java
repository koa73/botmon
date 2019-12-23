package ru.rgs.botmon.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * Created by OAKutsenko on 13.12.2019.
 */

public class WebApiException extends Exception {


    @JsonIgnoreProperties(value =
            {"cause", "stackTrace", "localizedMessage", "suppressed", "message", "status"},
            ignoreUnknown=true)


    private String errMsg;

    private String reason;

    public WebApiException(String errMsg, String causeReason){
        this.errMsg = errMsg;
        this.reason = causeReason;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getReason() {
        return reason;
    }
}
