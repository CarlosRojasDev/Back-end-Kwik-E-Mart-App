package com.sena.react.kwikemartapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TipoProducto")
public class TipoProducto {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int id_tipo_producto;
	
	@Column(length = 50, nullable = false)
	private String tipo_producto;

    public int getId_tipo_producto() {
        return id_tipo_producto;
    }

    public void setId_tipo_producto(int id_tipo_producto) {
        this.id_tipo_producto = id_tipo_producto;
    }

    public String getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }    
}
