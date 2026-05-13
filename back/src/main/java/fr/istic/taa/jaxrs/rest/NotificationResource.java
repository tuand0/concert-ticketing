package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.mappers.NotificationMapper;
import fr.istic.taa.jaxrs.services.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/notifications")
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Notification", description = "Gestion des notifications")
public class NotificationResource {

    private final NotificationService NotificationService = new NotificationService();
    private final NotificationMapper notificationMapper = new NotificationMapper();

    @GET
    @Path("/user/{userId}")
    public Response getNotificationsByUserId(@PathParam("userId") Long userId) {
        return Response.ok(
                notificationMapper.toDTOList(
                        NotificationService.findByUserId(userId)
                )
        ).build();
    }
}