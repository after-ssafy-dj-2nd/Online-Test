package com.onlinetest.backend.interceptor;
public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = -2238030302650813813L;
	
	public UnauthorizedException() {
		super("로그인을 해주세요.");
	}
}