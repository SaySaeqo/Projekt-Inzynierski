package pl.edu.pg.cloudlib.editor_backend.handler;

import pl.edu.pg.cloudlib.editor_backend.Exhibit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.edu.pg.cloudlib.editor_backend.repository.ExhibitRepository;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

@Component
public class ExhibitHandler {

    @Autowired
    private ExhibitRepository repository;

    @Nonnull
    public Mono<ServerResponse> getExhibitsByName(ServerRequest request) {
        return request.queryParam("name")
                .map(String::valueOf)
                .map(name -> repository.findByName(name))
                .map(exhibits -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(exhibits, Exhibit.class))
                .orElse(
                        ServerResponse
                                .badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .build()
                );
    }

    @Nonnull
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Exhibit.class)
                .flatMap(exhibit ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(repository.save(exhibit), Exhibit.class)
                );
    }
}
