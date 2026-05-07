package fr.istic.taa.jaxrs.filters;

import fr.istic.taa.jaxrs.utils.JwtUtil;
import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Arrays;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * La requête courante nécessite une authentification si elle porte l'annotation @RolesAllowed
     */
    private RolesAllowed getRolesAllowed() {
        RolesAllowed m = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class);
        return m != null ? m : resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        RolesAllowed rolesAllowed = getRolesAllowed();
        if (rolesAllowed == null) {
            return;
        }

        // On récupère l'en-tête HTTP "Authorization"
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Si pas d'en-tête ou pas au bon format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // On renvoie une erreur 401
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();

        // Analyse et validation du token JWT
        JwtUtil.TokenPayload payload = JwtUtil.validateToken(token);

        // Vérifie que l'utilisateur possède au moins un des rôles requis
        boolean hasRole = Arrays.stream(rolesAllowed.value())
                .anyMatch(payload.roles()::contains);
        if (!hasRole) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }

    }
}