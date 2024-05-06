package com.sena.react.kwikemartapp.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.react.kwikemartapp.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer>{

}
