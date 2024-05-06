package com.sena.react.kwikemartapp.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.react.kwikemartapp.models.Marca;

public interface MarcasRepositoty extends JpaRepository<Marca,Integer> {

}
