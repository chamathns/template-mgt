package org.wso2.carbon.template.mgt.exception;

public class TemplateManagementRuntimeException extends RuntimeException {
    private String errorCode;

    public TemplateManagementRuntimeException() {

        super();
    }

    public TemplateManagementRuntimeException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }

    public TemplateManagementRuntimeException(String message, String errorCode, Throwable cause) {

        super(message, cause);
        this.errorCode = errorCode;
    }

    public TemplateManagementRuntimeException(Throwable cause) {

        super(cause);
    }

    protected String getErrorCode() {

        return errorCode;
    }

    protected void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }
}
