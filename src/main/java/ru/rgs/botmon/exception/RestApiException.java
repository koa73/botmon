package ru.rgs.botmon.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;


/**
 *
 * Created by OAKutsenko on 13.12.2019.
 */

public class RestApiException extends Exception {


    @JsonIgnoreProperties(value =
            {"cause", "stackTrace", "localizedMessage", "suppressed", "message", "status"},
            ignoreUnknown=true)


    private String error;

    private String cause;

    public RestApiException(String errMsg, String causeReason){
        this.error = errMsg;
        this.cause = causeReason;
    }

    @Override
    public String toString() {
        return "{" +
                "\"error\":\"" + error + '\"' +
                ", \"cause\":\"" + cause + '\"' +
                '}';
    }
}
