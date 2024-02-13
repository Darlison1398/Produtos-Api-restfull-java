package com.example.springboot.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PRODUCTS")
public class ProdutoModel extends RepresentationModel<ProdutoModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduto;
    private String name;
    private BigDecimal value;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(UUID idProduto) {
        this.idProduto = idProduto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    
    
}
