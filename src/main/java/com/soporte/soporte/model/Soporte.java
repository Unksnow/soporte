package com.soporte.soporte.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "soporte")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Soporte {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int ticket;

    @Column(nullable = false)
    private String mensajes;

    @Column(nullable = false)
    private String comentarios;

    @Column(nullable = false)
    public boolean esCritico;


}
