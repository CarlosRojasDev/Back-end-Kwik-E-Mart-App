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

import com.sena.react.kwikemartapp.models.UnMedidaDTO;
import com.sena.react.kwikemartapp.models.UnidadMedida;
import com.sena.react.kwikemartapp.services.UnidadMedidaRepository;

@Controller
@RequestMapping("/kwikemart")
@CrossOrigin(origins = "http://localhost:3000")
public class UnidadMedidaController {
    @Autowired

    //Repositorio de unidades de medida
    private UnidadMedidaRepository repo_medida;

    //Obtener lista de unidades de medida
    @GetMapping("/unidadMedida")
    public ResponseEntity<List<UnidadMedida>> listMedidas(){
        List<UnidadMedida> listMedidas = repo_medida.findAll();
        if (listMedidas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listMedidas,HttpStatus.ACCEPTED);
    }
    
    //Crear unidad de medida
    @PostMapping("/crearUnidadMedida")
    public ResponseEntity<UnidadMedida> crear (@RequestBody UnMedidaDTO unMedidaDTO){
        try {
            UnidadMedida unidadMedida = new UnidadMedida();
            unidadMedida.setUnidadMedida(unMedidaDTO.getMedida());            
            repo_medida.save(unidadMedida);
            return new ResponseEntity<>(unidadMedida,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    //Obtener unidad de medida a través de su id
    @GetMapping("/unidadMedida/{id}")
    public ResponseEntity<UnMedidaDTO>getMedida(@PathVariable ("id") int id){       
        Optional<UnidadMedida> opt_medida = repo_medida.findById(id);
        if(opt_medida.isPresent()){
            UnidadMedida unidadMedida = opt_medida.get();
            UnMedidaDTO unMedidaDTO = new UnMedidaDTO();
            unMedidaDTO.setId(unidadMedida.getId_unidadMedida());
            unMedidaDTO.setMedida(unidadMedida.getUnidadMedida());

            return new ResponseEntity<>(unMedidaDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(null);              
    }

    //Editar unidad de medida
    @PutMapping("/unidadMedida/{id}")
    public ResponseEntity<UnMedidaDTO> editMedida(@PathVariable("id") int id,@RequestBody UnMedidaDTO unMedidaDTO){
        Optional<UnidadMedida> opt_medida = repo_medida.findById(id);
        if(opt_medida.isPresent()){
            UnidadMedida unidadMedida = opt_medida.get();            
            unidadMedida.setUnidadMedida(unMedidaDTO.getMedida());;
            repo_medida.save(unidadMedida);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(null);
    }

    //Eliminar unidad de medida
    @DeleteMapping("/deleteUnidadMedida/{id}")
    public ResponseEntity<HttpStatus> deleteMedida(@PathVariable("id") int id){
        Optional<UnidadMedida> opt_medida = repo_medida.findById(id);
        if(opt_medida.isPresent()){
            UnidadMedida unidadMedida  = opt_medida.get();
            repo_medida.delete(unidadMedida);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}