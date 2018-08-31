package top.bestplayer.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.bestplayer.Utils.MessageUtil;
import top.bestplayer.Utils.SHA1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("wx")
@Scope("prototype")
public class WechatController extends BaseController{

	private String Token = "123456789abcdef";
    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

	@RequestMapping(value = "chat", method ={RequestMethod.GET,RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public String ReseiveMessage() throws IOException{
		response.reset();
		boolean method = request.getMethod().toLowerCase().equals("get");
		if (method) {
			System.out.println("进入get方式");
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
            return access(signature,timestamp,nonce,echostr);
		}else{
            // 进入POST聊天处理
            	System.out.println("enter post");
				String s = MessageUtil.acceptMessage();
                System.out.println(s);
				writer.print(s);
				writer.close();

        }
			return "success";
	}
	private String access(String signature,String timestamp,String nonce,String echostr) throws IOException {
		// 验证URL真实性
		System.out.println("进入验证access");
		List<String> params = new ArrayList<String>();
		params.add(Token);
		params.add(timestamp);
		params.add(nonce);
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
		response.reset();
		if (temp.equals(signature)) {
			writer.write(echostr);
			writer.close();
			System.out.println("成功返回 echostr：" + echostr);
			return echostr;
		}
		System.out.println("失败 认证");
		return null;
	}
}
