package top.bestplayer.Utils;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import top.bestplayer.Beans.Message;
import top.bestplayer.controller.BaseController;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil extends BaseController {

    public static String  acceptMessage()throws IOException {
        String str=null;
        try {
            Map<String, String> map = readXml();
            String ToUserName=map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");
            String event = map.get("Event");
            if(MsgType.equals(Constant.MESSAGE_TEXT)){
                if (Content.equals("1")){
                    StringBuffer sb = new StringBuffer();
                    sb.append("对啊我也是这么觉得的,你真是太有眼光了\n");
                    sb.append("恭喜你获得了使用以下功能的权利\n");
                    sb.append("a.大屏时钟\n");
                    sb.append("b.全球天气可视化预测\n");
                    sb.append("c.王思聪微博生成\n");
                    sb.append("d.挑战10秒\n");
                    sb.append("回复字母即可使用该功能");
                    return sendMessage(FromUserName,ToUserName,sb.toString());
                } else if (Content.equals("2")){
                    return  sendMessage(FromUserName,ToUserName,"怎么回事?年纪轻轻眼睛就瞎了?");
                } else if (Content.equals("?")){
                    return   sendMessage(FromUserName,ToUserName,menuMessage());
                } else if (Content.equals("老司机福利专属")){
                    return  sendMessage(FromUserName,ToUserName,"http://www.6865U.com");
                } else if (Content.equals("a")){
                    return  sendMessage(FromUserName,ToUserName,"http://www.nicetool.net/embed/screen_clock.html");
                } else if (Content.equals("b")){
                    return  sendMessage(FromUserName,ToUserName,"https://earth.nullschool.net");
                }else if (Content.equals("c")){
                    return  sendMessage(FromUserName,ToUserName,"http://www.nicetool.net/embed/wangsicong.html");
                }else if (Content.equals("d")){
                    return  sendMessage(FromUserName,ToUserName,"http://www.nicetool.net/embed/ten_secs.html");
                }else{
                    return  sendMessage(FromUserName,ToUserName,"鹦鹉学舌:"+Content);
                }
               /* message.setContent("您好"+FromUserName+"\n我是开发者:"+ToUserName
                        +"\n您发送的消息类型为:"+MsgType+"\n您发送的时间是:"+CreateTime
                        +"\n我回复的时间是:"+message.getCreateTime()+"\n您发送的内容是:"+Content+"\n消息ID:"+MsgId);*/

            }else if(MsgType.equals(Constant.MESSAGE_EVENT)){
                if(event.equals(Constant.MESSAGE_SUBSCRIBE)){
                    return  sendMessage(FromUserName,ToUserName,menuMessage());
                }
                if(event.equals(Constant.MESSAGE_UNSUBSCRIBE)){
                    return  sendMessage(FromUserName,ToUserName,"再见了我的小可爱!我会等你回来的");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
        //初始化消息回复
        public  static String  sendMessage(String ToUserName,String FromUserName,String Content){
            Message message =new Message();
            message.setFromUserName(FromUserName);
            message.setToUserName(ToUserName);
            message.setCreateTime(DateUtil.dateFormate(new Date()));
            message.setMsgType(Constant.MESSAGE_TEXT);
            message.setContent(Content);
            return objectToXml(message);
        }
        //初始菜单
        public static String menuMessage(){
            StringBuilder  sb = new StringBuilder();
            sb.append("欢迎关注最帅公众号,请问你觉得易东辉帅吗?\n");
            sb.append("请回复数字\n");
            sb.append("1.帅    2.不帅\n");
            sb.append("回复?调出菜单");
            return sb.toString();

        }
    /**
     * @Description: 解析微信端发送过来的XML消息并封装到Map中
     * @Param:
     * @return: xml解析数据封装的map
     * @Author: Yi
     * @Date: 2018.08.21
     */
    public static Map<String,String> readXml(){
        Map<String,String> map =new HashMap<String,String>();
        SAXReader saxReader=new SAXReader();
        try {
            ServletInputStream inputStream = request.getInputStream();//获取输入流
            Document document = saxReader.read(inputStream);//使用saxread读取输入XML流文件
            Element root = document.getRootElement();//获取根节点元素
            List<Element> list = root.elements();//获取根节点下的元素列表
            for (Element e : list) {
                map.put(e.getName(),e.getText());

            }
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  map;
    }
    /**
     * 将文本消息对象转换为XML格式
     */
    public static String objectToXml(Message message){
        XStream xs = new XStream();
        xs.alias("xml",message.getClass());
        return xs.toXML(message);
    }
}
