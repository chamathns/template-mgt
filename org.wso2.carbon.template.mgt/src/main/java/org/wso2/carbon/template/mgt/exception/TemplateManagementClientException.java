package org.wso2.carbon.template.mgt.exception;

/**
 * This class is used to define the client side errors which needs to be handled.
 */
public class TemplateManagementClientException extends TemplateManagementException {

    public TemplateManagementClientException() {

    }

    public TemplateManagementClientException(String message, String errorCode) {

        super(message, errorCode);
    }

    public TemplateManagementClientException(String message, String errorCode, Throwable cause) {

        super(message, errorCode, cause);
    }

    public TemplateManagementClientException(Throwable cause) {

        super(cause);
    }

}
