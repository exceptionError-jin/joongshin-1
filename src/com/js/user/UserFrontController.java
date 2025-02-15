/*
 * */
package com.js.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.js.Result;
import com.js.user.controller.CheckEmailOkController;
import com.js.user.controller.JoinOkController;
import com.js.user.controller.LoginController;
import com.js.user.controller.LoginOkController;
import com.js.user.controller.LogoutController;

public class UserFrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String target = req.getRequestURI().replace(req.getContextPath() + "/", "").split("\\.")[0];
		
		System.out.println(target);
		Result result = null;
		
		//대상에 따라 적절한 컨트롤러 결정
		if(target.equals("checkEmailOk")) {
			result = new CheckEmailOkController().execute(req, resp);
		} else if(target.equals("join")){
			result = new Result();
			result.setPath("templates/makepage-hsw/join.jsp");
			
		} else if(target.equals("joinOk")){
			result = new JoinOkController().execute(req, resp);
			
		} else if(target.equals("login")){
			result = new LoginController().execute(req, resp);
			
		} else if(target.equals("loginOk")) {
			result = new LoginOkController().execute(req, resp);
			
		} else if(target.equals("logout")) {
			result = new LogoutController().execute(req, resp);
		} 
		 else if(target.equals("findPassword")) {
			result = new Result();
			result.setPath("templates/makepage-hsw/findPassword.jsp");
		}
		
		if(result != null) {
			if(result.isRedirect()) {
				resp.sendRedirect(result.getPath());
			}else {
				req.getRequestDispatcher(result.getPath()).forward(req, resp);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
