package fr.istic.taa.jaxrs.configuration;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * Cette classe permet de configurer le serveur
 * pour que les erreurs héritant de ClientErrorException soit affichées
 * au format JSON.
 */
public class ClientErrorExceptionMapper
        implements ExceptionMapper<ClientErrorException> {
    @Override
    public Response toResponse(ClientErrorException exception) {
        return Response.status(exception.getResponse().getStatus())
                .entity(new ErrorMessage(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    record ErrorMessage(String message) {
    }
}