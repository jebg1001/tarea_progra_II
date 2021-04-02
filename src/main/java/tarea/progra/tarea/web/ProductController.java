package tarea.progra.tarea.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.*;
import tarea.progra.tarea.domain.Product;
import tarea.progra.tarea.repository.*;

@RestController
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    

    @GetMapping(value="/products", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> products(){
        return new ResponseEntity<List<Product>>(
            productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value="/product", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@RequestBody Product e){
        productRepository.save(e);
        productRepository.flush();
        return new ResponseEntity<Product>(HttpStatus.CREATED);
    }

    @GetMapping(value="/product/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> products(@PathVariable int id){
        Optional<Product> optionalEntity= productRepository.findById(id);
        if(optionalEntity.isPresent())
            return new ResponseEntity<Product>(
                optionalEntity.get(), HttpStatus.OK);
        else
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value="/product/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable int id){
        productRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value="/product", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> update(@RequestBody Product e){
        create(e);
        return new ResponseEntity<Product>(e, HttpStatus.OK);
    }

}
