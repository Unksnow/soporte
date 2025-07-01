package com.soporte.soporte.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.soporte.soporte.controller.SoporteController;
import com.soporte.soporte.dto.Soporte;
import com.soporte.soporte.dto.SoporteModel;

@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, SoporteModel> {

    @Override
    public SoporteModel toModel(Soporte soporte) {
        SoporteModel model = new SoporteModel();
        model.setTicket(soporte.getTicket());
        model.setMensajes(soporte.getMensajes());
        model.setComentarios(soporte.getComentarios());
        model.setEsCritico(soporte.isEsCritico());
        model.setActivo(soporte.isActivo());

        model.add(linkTo(methodOn(SoporteController.class).buscarSoporte(soporte.getTicket())).withSelfRel());
        model.add(linkTo(methodOn(SoporteController.class).obtenerSoporte()).withRel("Soporte"));
        model.add(linkTo(methodOn(SoporteController.class).eliminarProgreso(soporte.getTicket())).withRel("Eliminar"));
        model.add(linkTo(methodOn(SoporteController.class).actualizarSoporte(soporte.getTicket(), soporte)).withRel("Actualizar"));

        return model;
    }

}
