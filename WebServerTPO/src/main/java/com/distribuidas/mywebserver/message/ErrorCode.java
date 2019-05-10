package com.distribuidas.mywebserver.message;

public enum ErrorCode {
		
	ERROR_CAMBIOPASSWORD(10),
	ERROR_CLIENTE(20),
	ERROR_LOGIN(30),
	ERROR_PEDIDO(40),
	ERROR_PRODUCTO(50),
	ERROR_RUBRO(60),
	ERROR_SUBRUBRO(70),
	ERROR_USUARIO(80),
	ERROR_IO(90);
	
	
	private final int id;
	ErrorCode(int id) { this.id = id; }
    public int getValue() { return id; }
}
