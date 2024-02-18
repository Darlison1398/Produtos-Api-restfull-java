package com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.ProdutoRecordDTO;
import com.example.springboot.models.ProdutoModel;
import com.example.springboot.repository.ProdutoRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;




@RestController
//@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository productRepository;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/produtos")
    public ResponseEntity < ProdutoModel > saveProduto(@RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoRecordDTO, produtoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(produtoModel));
    }
    
    @GetMapping("/listaProdutos")
    public ResponseEntity <List <ProdutoModel>> getAllProdutos() {

        // aqui estamos usando o heteoas
        List<ProdutoModel> produtoList = productRepository.findAll();
        if (!produtoList.isEmpty()) {
            for (ProdutoModel produto : produtoList) {
                UUID id = produto.getIdProduto();
                produto.add(linkTo(methodOn(ProdutoController.class).getOneProduto(id)).withSelfRel());
            }
            
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity <Object> getOneProduto(@PathVariable(value="id") UUID id) {
        
        Optional<ProdutoModel> produto = productRepository.findById(id);
        
        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        produto.get().add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(produto.get());
    } 

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("editarProduto/{id}")
    public ResponseEntity<Object> updateProduto(@PathVariable(value="id") UUID id, @RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        
        Optional<ProdutoModel> produto = productRepository.findById(id);

        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto not found.");
        }

        var produtoModel = produto.get();
        BeanUtils.copyProperties(produtoRecordDTO, produtoModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(produtoModel));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletarProduto/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable(value = "id") UUID id) {
        
        Optional<ProdutoModel> produto = productRepository.findById(id);

        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto not found");
        }

        productRepository.delete(produto.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

    
}
