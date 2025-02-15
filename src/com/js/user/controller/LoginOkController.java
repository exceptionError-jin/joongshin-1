package com.js.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.js.Action;
import com.js.Result;
import com.js.juniorUser.dao.JuniorUserDAO;
import com.js.user.dao.UserDAO;

public class LoginOkController implements Action {

	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	    UserDAO userDAO = new UserDAO();
	    String userEmail = req.getParameter("userEmail");
	    String userPassword = req.getParameter("userPassword");

	    Long userId = 0L;
	    HttpSession session = req.getSession();
	    Result result = new Result();
	    result.setRedirect(true);

	    // userEmail이 null인 경우, 쿠키를 검사하여 저장된 이메일과 비밀번호를 사용
	    if(userEmail == null) {
	        if(req.getHeader("Cookie") != null){
	            Cookie[] cookies = req.getCookies();

	            for(Cookie cookie: cookies){
	                if(cookie.getName().equals("userEmail")) {
	                    userEmail = cookie.getValue();
	                }
	                if(cookie.getName().equals("userPassword")) {
	                    userPassword = cookie.getValue();
	                }
	            }
	        }
	    }

	    // 이메일과 비밀번호를 사용하여 로그인
	    userId = userDAO.login(userEmail, userPassword);
	    System.out.println(userId);
	    if(userId == null) {
	        // 로그인 실패
	        System.out.println("일반 유저 로그인 실패");
	        result.setPath(req.getContextPath() + "/login.user?login=false");
	    } else {
	        // 로그인 성공
	        session.setAttribute("userId", userId);
	        
	        // userEmail, userPassword가 "8888"인 경우, 관리자 페이지로 이동
	        if (userEmail.equals("8888") && userPassword.equals("8888")) {
	            result.setPath(req.getContextPath() + "/listBoardOk.admin");
	        } 
//	        아니면 메인페이지 이동
	        else {
	            result.setPath(req.getContextPath() + "/listOk.board");
	        }
	    }
	    return result;
	}


}

