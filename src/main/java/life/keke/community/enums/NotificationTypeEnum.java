package life.keke.community.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(1,"回复了评论")

    ;


    private  int type;
    private  String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for (NotificationTypeEnum notyficationTypeEnum : NotificationTypeEnum.values()) {
            if(notyficationTypeEnum.getType() == type){
                return notyficationTypeEnum.getName();
            }
        }
        return "";
    }

}
