package fr.istic.taa.jaxrs.rest;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

@Hidden
@Path("/api")
public class SwaggerResource {

    @GET
    public byte[] getIndex() {
        try {
            return Files.readAllBytes(FileSystems.getDefault().getPath("src/main/webapp/swagger/index.html"));
        } catch (IOException e) {
            return null;
        }
    }

    @GET
    @Path("{path:.*}")
    public byte[] getResource(@PathParam("path") String path) {
        try {
            return Files.readAllBytes(FileSystems.getDefault().getPath("src/main/webapp/swagger/"+path));
        } catch (IOException e) {
            return null;
        }
    }

}
