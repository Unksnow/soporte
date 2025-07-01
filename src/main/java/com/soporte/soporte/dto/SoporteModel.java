package com.soporte.soporte.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoporteModel extends RepresentationModel<SoporteModel> {

    private Integer ticket;
    private String  mensajes;
    private String  comentarios;
    private Boolean  esCritico;
    private Boolean activo;


}

