package com.truckrental.resource;

import com.truckrental.dto.ReservaDTO;
import com.truckrental.entity.Camion;
import com.truckrental.entity.Reserva;
import com.truckrental.mapper.ReservaMapper;
import com.truckrental.service.ReservaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Path("/reserva")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {
    @Inject
    ReservaService reservaService;

    @POST
    public Reserva save(Reserva reserva) {
        reserva = reservaService.save(reserva);
        return reserva;
    }

    @GET
    public Response buscar(@QueryParam("origen") String origen,
                           @QueryParam("destino") String destino) {
        List<Reserva> reservas = reservaService.buscar(origen, destino);
        List<ReservaDTO> resp = reservas.stream()
                .map(ReservaMapper::toDTO)
                .toList();
        return Response.ok(resp).build();
    }

    @PUT
    @Path("{id}/cancelar")
    public Response cancelar(@PathParam("id") Integer id) {
        try {
            return Response.ok(reservaService.cancelar(id)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/disponibilidad")
    public List<Camion> disponibilidad(@QueryParam("desde") LocalDate desde,
                                       @QueryParam("hasta") LocalDate hasta,
                                       @QueryParam("volumen") Double volumen) {
        return reservaService.buscarDisponibles(desde, hasta);
    }

}
