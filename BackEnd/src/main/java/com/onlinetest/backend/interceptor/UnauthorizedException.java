package com.onlinetest.backend.interceptor;

import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = -2238030302650813813L;
	
	@Override
	public void printStackTrace(PrintWriter arg0) {
		arg0.print(super.fillInStackTrace());
	}

	public UnauthorizedException() {
		super("로그인을 해주세요.");
	}
}