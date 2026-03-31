package com.truckrental.resource;

import com.truckrental.model.Reserva;
import com.truckrental.service.ReservaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
        reserva =  reservaService.save(reserva);
        return reserva;
    }

    @GET
    public List<Reserva> buscar(@QueryParam("origen") String origen,
                                @QueryParam("destino") String destino) {
        return reservaService.buscar(origen, destino);
    }

    @PUT
    @Path("{id}/cancelar")
    public Response cancelar(@PathParam("id") Integer id) {
        try{
            return Response.ok(reservaService.cancelar(id)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

}
