package com.sena.react.kwikemartapp.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.react.kwikemartapp.models.Categoria;

public interface CategoriasRepositoty extends JpaRepository<Categoria,Integer> {

}
