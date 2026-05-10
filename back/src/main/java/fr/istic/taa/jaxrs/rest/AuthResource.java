package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.dto.LoginDTO;
import fr.istic.taa.jaxrs.utils.JwtUtil;
import fr.istic.taa.jaxrs.utils.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Set;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthResource {

    private final UtilisateurDao utilisateurDao = new UtilisateurDao();

    record LoginResponse(String token, String role, Long userId) {}

    @POST
    @Path("/login")
    @Operation(summary = "Login", description = "Authenticate user and return JWT token")
    public Response login(@Valid LoginDTO loginDTO) {
        Utilisateur utilisateur = utilisateurDao.findByEmail(loginDTO.getEmail());

        if (utilisateur == null || !PasswordUtil.verify(loginDTO.getPassword(), utilisateur.getHashedPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String roleName = utilisateur.getRole().name().toLowerCase();
        Set<String> roles = Set.of(roleName);
        String token = JwtUtil.generateToken(utilisateur.getEmail(), roles);

        return Response.ok(new LoginResponse(token, roleName, utilisateur.getId())).build();
    }
}