package life.keke.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    Question_NOT_FOUND(2001,"您要找的问题不存在，请换个问题试试"),
    TARGET_PARAM_NOT_FOUND(2002,"您未选中任何问题进行评论或评论已被删除"),
    NOT_LOGIN(2003,"未登录"),
    SYSTEM_ERROR(2004,"请您稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    COMMENT_CONTENT_NOT_FOUND(2007,"未输入回复内容")
    ;


    private  String message;
    private Integer code;

    public Integer code() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
