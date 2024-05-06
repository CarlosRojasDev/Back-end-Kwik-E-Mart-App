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

import com.sena.react.kwikemartapp.models.TipoProdDTO;
import com.sena.react.kwikemartapp.models.TipoProducto;
import com.sena.react.kwikemartapp.services.TipoProductoRepository;

@Controller
@RequestMapping("/kwikemart")
@CrossOrigin(origins = "http://localhost:3000")
public class TipoProductoController {
    @Autowired

    //Repositorio de tipos de producto
    private TipoProductoRepository repo_tipo;

    //Obtener lista de tipos de producto
    @GetMapping("/tipoProducto")
    public ResponseEntity<List<TipoProducto>> listaTipos(){
        List<TipoProducto> listTipos = repo_tipo.findAll();
        if (listTipos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listTipos,HttpStatus.OK);
    }

    //Crear tipo de producto
    @PostMapping("/crearTipo")
    public ResponseEntity<TipoProducto> crear (@RequestBody TipoProdDTO tipoProdDTO){
        try {
            TipoProducto tipoProducto = new TipoProducto();
            tipoProducto.setTipo_producto(tipoProdDTO.getTipo());
            repo_tipo.save(tipoProducto);
            return new ResponseEntity<>(tipoProducto,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    //Obtener tipo de producto a través de su id
    @GetMapping("/tipoProducto/{id}")
    public ResponseEntity<TipoProdDTO>getTipo(@PathVariable ("id") int id){       
        Optional<TipoProducto> opt_tipo = repo_tipo.findById(id);
        if(opt_tipo.isPresent()){
            TipoProducto tipoProducto = opt_tipo.get();
            TipoProdDTO tipoProdDTO = new TipoProdDTO();
            tipoProdDTO.setId(tipoProducto.getId_tipo_producto());
            tipoProdDTO.setTipo(tipoProducto.getTipo_producto());

            return new ResponseEntity<>(tipoProdDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(null);              
    }

    //Editar tipo de producto
    @PutMapping("/tipoProducto/{id}")
    public ResponseEntity<TipoProdDTO> editTipo(@PathVariable("id") int id,@RequestBody TipoProdDTO tipoProdDTO){
        Optional<TipoProducto> opt_tipo = repo_tipo.findById(id);
        if(opt_tipo.isPresent()){
            TipoProducto tipoProducto = opt_tipo.get();            
            tipoProducto.setTipo_producto(tipoProdDTO.getTipo());
            repo_tipo.save(tipoProducto);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(null);
    }

    //Eliminar tipo de producto
    @DeleteMapping("/deleteTipo/{id}")
    public ResponseEntity<HttpStatus> deleteTipo(@PathVariable("id") int id){
        Optional<TipoProducto> opt_tipo = repo_tipo.findById(id);
        if(opt_tipo.isPresent()){
            TipoProducto tipoProducto = opt_tipo.get();
            repo_tipo.delete(tipoProducto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
