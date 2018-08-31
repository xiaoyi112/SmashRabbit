package top.bestplayer.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController {
	
	public static String CHAR_ENCODE = "utf-8";
	protected static HttpServletRequest request;
	protected static HttpServletResponse response;
	protected HttpSession session;
	protected PrintWriter writer;
	
	//初始化
	@ModelAttribute
	public void SetResAndRep(HttpServletRequest request,HttpServletResponse response) throws IOException {
			this.request=request;
			this.response=response;
		    request.setCharacterEncoding(CHAR_ENCODE);
		    response.setContentType("text/html;charset="+CHAR_ENCODE);
			this.session=request.getSession();
			this.writer=response.getWriter();

		}
}
