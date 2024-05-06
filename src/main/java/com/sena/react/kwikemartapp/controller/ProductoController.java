package com.sena.react.kwikemartapp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sena.react.kwikemartapp.models.Marca;
import com.sena.react.kwikemartapp.models.Producto;
import com.sena.react.kwikemartapp.models.ProductoDTO;
import com.sena.react.kwikemartapp.models.TipoProducto;
import com.sena.react.kwikemartapp.models.UnidadMedida;
import com.sena.react.kwikemartapp.services.CategoriasRepositoty;
import com.sena.react.kwikemartapp.services.MarcasRepositoty;
import com.sena.react.kwikemartapp.services.ProductoRepository;
import com.sena.react.kwikemartapp.services.TipoProductoRepository;
import com.sena.react.kwikemartapp.services.UnidadMedidaRepository;

@Controller
@RequestMapping("/kwikemart")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductoController {
    @Autowired
    private ProductoRepository repo_producto; //Repositorio de productos
    @Autowired
    private TipoProductoRepository repo_tipo; //Repositorio de tipos de producto
    @Autowired
    private CategoriasRepositoty repo_categoria; // Repositorio de categorías
    @Autowired
    private MarcasRepositoty repo_marcas; // Repositorio de marcas
    @Autowired
    private UnidadMedidaRepository repo_medida; //Respositorio de unidades de medida

    //Biblioteca slf4j para el manejo de eventos en java
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listProductos(){
        List<Producto> listProductos = repo_producto.findAll();
        if (listProductos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProductos,HttpStatus.OK);
    }

    //Crear producto
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO){        
        try {
            Producto producto = new Producto();

            //Convertir un objeto ProductoDTO a Producto
            producto = convertirAProducto(productoDTO, producto);
            repo_producto.save(producto);
            return new ResponseEntity<>(productoDTO,HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al crear el producto", e);
            return new ResponseEntity<>(productoDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener producto a través de su id
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable("id") int id){
        Optional<Producto> opt_producto = repo_producto.findById(id);
        if (opt_producto.isPresent()) {
            Producto producto = opt_producto.get();
            ProductoDTO productoDTO = convertirAProductoDTO(producto);
            return new ResponseEntity<>(productoDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(null);
    }

    //Editar producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> editar(@PathVariable("id") int id, @RequestBody ProductoDTO productoDTO){
        Optional<Producto> opt_producto = repo_producto.findById(id);
        if (opt_producto.isPresent()) {
            Producto producto = opt_producto.get();
            producto = convertirAProducto(productoDTO, producto);
            repo_producto.save(producto);

            return new ResponseEntity<>(producto,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Eliminar producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> eliminarProducto(@PathVariable("id") int id){
        Optional<Producto> opt_producto = repo_producto.findById(id);
        if (opt_producto.isPresent()) {
            Producto producto = opt_producto.get();            
            repo_producto.delete(producto);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //Convertir un objeto Producto a ProductoDTO 
    private ProductoDTO convertirAProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId_producto());
        productoDTO.setEstado(producto.getEstado());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setTipo(producto.getTipoProducto().getId_tipo_producto());
        productoDTO.setCategoria(producto.getCategoria().getId_categoria());
        productoDTO.setMarca(producto.getMarca().getId_marca());
        productoDTO.setMedida(producto.getUnidadMedida().getId_unidadMedida());
    
    return productoDTO; 
    }
    
    //Convertir un objeto ProductoDTO a Producto
    private Producto convertirAProducto(ProductoDTO productoDTO, Producto producto) {
        
        Marca marca = repo_marcas.findById(productoDTO.getMarca()).orElse(null);
	    TipoProducto tipoProducto = repo_tipo.findById(productoDTO.getTipo()).orElse(null);
	    Categoria categoria = repo_categoria.findById(productoDTO.getCategoria()).orElse(null);
	    UnidadMedida unidadMedida = repo_medida.findById(productoDTO.getMedida()).orElse(null);

        producto.setEstado(productoDTO.getEstado());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setVendidos(productoDTO.getVendidos());
        producto.setNombre(productoDTO.getNombre());
        producto.setMarca(marca);
		producto.setCategoria(categoria);
		producto.setTipoProducto(tipoProducto);
		producto.setUnidadMedida(unidadMedida);
        return producto; 
    }
}
