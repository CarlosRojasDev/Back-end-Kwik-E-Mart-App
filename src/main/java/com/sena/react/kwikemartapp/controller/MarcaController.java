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
import com.sena.react.kwikemartapp.models.Marca;
import com.sena.react.kwikemartapp.models.MarcaDTO;
import com.sena.react.kwikemartapp.services.MarcasRepositoty;

@Controller
@RequestMapping("/kwikemart")
@CrossOrigin(origins = "http://localhost:3000")
public class MarcaController {
    @Autowired

    //Repositorio de marcas
    private MarcasRepositoty repo_marcas;

    //Obtener lista de marcas
    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> listaMarcas(){
        List<Marca> listMarcas = repo_marcas.findAll();
        if (listMarcas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listMarcas,HttpStatus.OK);
    }

    //Crear marca
    @PostMapping("/marcas")
    public ResponseEntity<Marca> crear(@RequestBody MarcaDTO marcaDTO){
        try{
            Marca marca = new Marca();
            marca.setNombre_marca(marcaDTO.getNombre());
            repo_marcas.save(marca);
            return new ResponseEntity<>(marca, HttpStatus.CREATED);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener marca a través de su id
    @GetMapping("/marcas/{id}")
    public ResponseEntity<MarcaDTO> getMarca(@PathVariable("id") int id){
        Optional<Marca> opt_marca = repo_marcas.findById(id);
        if (opt_marca.isPresent()) {
            Marca marca = opt_marca.get();
            MarcaDTO marcaDTO = new MarcaDTO();
            marcaDTO.setId(marca.getId_marca());
            marcaDTO.setNombre(marca.getNombre_marca());

            return new ResponseEntity<>(marcaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null);
    }

    //Editar marca
    @PutMapping("/marcas/{id}")
    public ResponseEntity<Marca> editar(@PathVariable("id") int id,@RequestBody MarcaDTO marcaDTO){
        
        Optional<Marca> opt_marca = repo_marcas.findById(id);
        if(opt_marca.isPresent()){
            Marca marca = opt_marca.get();
            marca.setNombre_marca(marcaDTO.getNombre());

            repo_marcas.save(marca);

            return new ResponseEntity<>(marca, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);         
    }

    //Eliminar marca
    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") int id){
        Optional<Marca> opt_marca = repo_marcas.findById(id);
        if (opt_marca.isPresent()) {

            Marca marca = opt_marca.get();
            repo_marcas.deleteById(marca.getId_marca());;

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
