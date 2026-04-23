package fr.istic.taa.jaxrs.rest;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Hidden
@Path("/api")
public class SwaggerResource {

    private static final String SWAGGER_DIR = "src/main/webapp/swagger";

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getIndex() {
        return getFile("index.html", MediaType.TEXT_HTML);
    }

    @GET
    @Path("{path:.*}")
    public Response getResource(@PathParam("path") String path) {
        String mediaType = guessMediaType(path);
        return getFile(path, mediaType);
    }

    private Response getFile(String fileName, String mediaType) {
        try {
            java.nio.file.Path filePath = Paths.get(SWAGGER_DIR, fileName);

            if (!Files.exists(filePath)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("File not found: " + filePath.toAbsolutePath())
                        .build();
            }

            byte[] content = Files.readAllBytes(filePath);
            return Response.ok(content, mediaType).build();

        } catch (IOException e) {
            return Response.serverError()
                    .entity("Error reading Swagger UI file: " + e.getMessage())
                    .build();
        }
    }

    private String guessMediaType(String path) {
        if (path.endsWith(".html")) return MediaType.TEXT_HTML;
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".json")) return MediaType.APPLICATION_JSON;
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".svg")) return "image/svg+xml";
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}