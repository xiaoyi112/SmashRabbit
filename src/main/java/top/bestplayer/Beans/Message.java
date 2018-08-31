package top.bestplayer.Beans;

import lombok.Data;

@Data
public class Message {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
    private String Event;
}
