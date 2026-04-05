package com.truckrental.resource;

import com.truckrental.entity.Camion;
import com.truckrental.service.CamionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/camion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CamionResource {
    @Inject
    CamionService camionService;

    @POST
    public Camion save(Camion camion) {
        camion = camionService.save(camion);
        return camion;
    }

    @GET
    public List<Camion> buscar(@QueryParam("capacidad") Double capacidad) {
        return camionService.buscar(capacidad);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Camion camion) {
        try{
            return Response.ok(camionService.update(id, camion)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id){
        try {
            camionService.sofDelete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
