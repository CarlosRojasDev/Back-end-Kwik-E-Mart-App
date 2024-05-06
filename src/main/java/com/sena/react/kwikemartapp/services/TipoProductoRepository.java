package com.sena.react.kwikemartapp.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.react.kwikemartapp.models.TipoProducto;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {

}
