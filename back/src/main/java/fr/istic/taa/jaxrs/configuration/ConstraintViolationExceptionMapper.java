package fr.istic.taa.jaxrs.configuration;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Cette classe permet de configurer le serveur pour afficher les détails
 * des erreurs de validation au format JSON
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(
                       exception.getConstraintViolations().stream().map(Violation::new).collect(Collectors.toList())
                )
                .build();
    }

    static class Violation {
        public final String message;
        public final String field;

        public Violation(ConstraintViolation<?> constraintViolation) {
            this.message = constraintViolation.getMessage();
            this.field = getPath(constraintViolation.getPropertyPath());
        }
    }

    static String getPath(Path path) {
        return StreamSupport.stream(path.spliterator(), false)
                .filter(n -> n.getKind() == ElementKind.PROPERTY)
                .map(Path.Node::getName)
                .collect(Collectors.joining("."));
    }
}
