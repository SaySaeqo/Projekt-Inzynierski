package pl.edu.pg.cloudlib.editor_backend.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class UserRouter {

    @Autowired
    private pl.edu.pg.cloudlib.editor_backend.handler.ExhibitHandler exhibitHandler;

    @Bean
    RouterFunction<ServerResponse> userRoute(){
        return RouterFunctions
                .route(RequestPredicates.GET("/"), exhibitHandler::getExhibitsByName)
                .andRoute(RequestPredicates.POST("/exhibit/:id"), exhibitHandler::save);
    }

}
