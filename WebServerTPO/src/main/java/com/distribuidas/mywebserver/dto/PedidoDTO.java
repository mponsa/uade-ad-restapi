package com.distribuidas.mywebserver.dto;

import java.util.ArrayList;
import java.util.Date;



public class PedidoDTO {
	private int numeroPedido;
	private String cuit;
	
	public PedidoDTO() {}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
}
