package fr.istic.taa.jaxrs;

import fr.istic.taa.jaxrs.configuration.ClientErrorExceptionMapper;
import fr.istic.taa.jaxrs.configuration.ConstraintViolationExceptionMapper;
import fr.istic.taa.jaxrs.configuration.MyObjectMapperProvider;
import fr.istic.taa.jaxrs.rest.ArtisteResource;
import fr.istic.taa.jaxrs.rest.ConcertResource;
import fr.istic.taa.jaxrs.rest.SwaggerResource;
import fr.istic.taa.jaxrs.rest.TicketResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.Set;

@ApplicationPath("/")
public class TestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
            SwaggerResource.class,
            OpenApiResource.class,
            ConcertResource.class,
            TicketResource.class,
            ArtisteResource.class,
            ClientErrorExceptionMapper.class,
            ConstraintViolationExceptionMapper.class,
            MyObjectMapperProvider.class
        );
    }
}
