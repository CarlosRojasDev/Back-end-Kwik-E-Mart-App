package com.sena.react.kwikemartapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.react.kwikemartapp.models.Categoria;
import com.sena.react.kwikemartapp.models.CategoriaDTO;
import com.sena.react.kwikemartapp.services.CategoriasRepositoty;

@Controller
@RequestMapping("/kwikemart")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriasController {
    @Autowired

    //Repositorio de categorias
    private CategoriasRepositoty repo_categoria;

    //Obtener lista de categorías
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listCategorias(){
        List<Categoria> listCategorias = repo_categoria.findAll();
        if (listCategorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCategorias,HttpStatus.ACCEPTED);
    }

    //Crear categoría
    @PostMapping("/crearCategoria")
    public ResponseEntity<Categoria> crear (@RequestBody CategoriaDTO categoriaDTO){
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre_categoria(categoriaDTO.getCategoria());            
            repo_categoria.save(categoria);
            return new ResponseEntity<>(categoria,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    //Obtener categoría a través de su id
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO>getCategoria(@PathVariable ("id") int id){       
        Optional<Categoria> opt_categoria = repo_categoria.findById(id);
        if(opt_categoria.isPresent()){
            Categoria categoria = opt_categoria.get();
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId(categoria.getId_categoria());
            categoriaDTO.setCategoria(categoria.getNombre_categoria());

            return new ResponseEntity<>(categoriaDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(null);              
    }

    //Editar categoría
    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> editCategoria(@PathVariable("id") int id,@RequestBody CategoriaDTO categoriaDTO){
        Optional<Categoria> opt_categoria = repo_categoria.findById(id);
        if(opt_categoria.isPresent()){
            Categoria categoria = opt_categoria.get();            
            categoria.setNombre_categoria(categoriaDTO.getCategoria());
            repo_categoria.save(categoria);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(null);
    }

    //Eliminar categoría
    @DeleteMapping("/deleteCategoria/{id}")
    public ResponseEntity<HttpStatus> deleteCategoria(@PathVariable("id") int id){
        Optional<Categoria> opt_categoria = repo_categoria.findById(id);
        if(opt_categoria.isPresent()){
            Categoria categoria = opt_categoria.get();
            repo_categoria.delete(categoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}