package pl.edu.pg.cloudlib.editor_backend.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import pl.edu.pg.cloudlib.editor_backend.Exhibit;
import reactor.core.publisher.Flux;

public interface ExhibitRepository extends FirestoreReactiveRepository<Exhibit> {

    Flux<Exhibit> findByName(String name);

}
