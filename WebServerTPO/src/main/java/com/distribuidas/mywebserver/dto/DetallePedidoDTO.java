package com.distribuidas.mywebserver.dto;


public class DetallePedidoDTO {
	private int id;
	private int productoId;
	private int cantidad;

	
	public DetallePedidoDTO() {};
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
