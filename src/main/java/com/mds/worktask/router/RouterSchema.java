package com.mds.worktask.router;

import com.mds.worktask.handler.HandlerSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;


@Configuration
public class RouterSchema {

    @Bean
    RouterFunction<ServerResponse> composedRoutes(HandlerSchema handlerSchema) {
        RequestPredicate route = RequestPredicates
                .GET("/api/v1/schema");
        RequestPredicate route1 = RequestPredicates
                .GET("/api/v1/schema/condition");
        RequestPredicate route2 = RequestPredicates
                .POST("/api/v1/schema/condition");

        return RouterFunctions.route(route,handlerSchema::schema)
                .andRoute(route1,handlerSchema::schemaConditionGet)
                .andRoute(route2,handlerSchema::schemaConditionPost);
    }

}
