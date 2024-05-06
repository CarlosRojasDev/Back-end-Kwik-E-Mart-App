package com.sena.react.kwikemartapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="unidadMedida")
public class UnidadMedida {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_unidadMedida;
    
    private String unidadMedida;

    public int getId_unidadMedida() {
        return id_unidadMedida;
    }

    public void setId_unidadMedida(int id_unidadMedida) {
        this.id_unidadMedida = id_unidadMedida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }    
}
