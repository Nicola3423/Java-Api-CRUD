package org.example.resources;
import org.example.entites.Usuario;
import org.example.repositories.UsuarioRepository;
import org.example.services.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("usuario")
public class UsuarioResource {

    public UsuarioRepository usuarioRepository;

    public UsuarioService usuarioService;



    public UsuarioResource() {
        usuarioRepository = new UsuarioRepository();
        usuarioService = new UsuarioService();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(
            @QueryParam("orderby") String orderBy,
            @QueryParam("direction") String direction,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset,
            @QueryParam("NOME_COMPLETO") String NOME_COMPLETO,
            @QueryParam("CARGO") String CARGO,
            @QueryParam("EMAIL_CORPORATIVO") String EMAIL_CORPORATIVO,
            @QueryParam("CONTATO") int CONTATO,
            @QueryParam("EMPRESA") String EMPRESA,
            @QueryParam("PAIS") String PAIS
    ) {
        return Response.ok(UsuarioService.getAll(NOME_COMPLETO, CARGO, EMAIL_CORPORATIVO, CONTATO, EMPRESA, PAIS,
                orderBy, direction, limit, offset)).build();
    }


    @GET
    @Path("usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByCategoria(@PathParam("id") int id) {
        return Response.ok(usuarioRepository.getAllByCategoria(id)).build();
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var usuario = usuarioRepository.get(id);
        return usuario.isPresent() ?
                Response.ok(usuario.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Usuario usuario) {
        try {
            usuarioService.create(usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Usuario usuario) {
        try {
            usuarioService.update(id, usuario);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            usuarioService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
