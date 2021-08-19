package services;

import models.Model;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import repositories.model.ModelRepository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class ModelService {

    private final ModelRepository repository;
    private final HttpExecutionContext ec;

    @Inject
    public ModelService(ModelRepository repository, HttpExecutionContext ec) {
        this.repository = repository;
        this.ec = ec;
    }

    public CompletionStage<Stream<Model>> list(Http.Request request) {
        return repository.list().thenApplyAsync(m -> m, ec.current());
    }

    public CompletionStage<Optional<Model>> getByID(Http.Request request, String id) {
        return repository.getByID(Long.parseLong(id)).thenApplyAsync(optionalData -> optionalData, ec.current());
    }

    public CompletionStage<Optional<Model>> updateByID(Http.Request request, String id) {
        return repository.updateByID(Long.parseLong(id), request).thenApplyAsync(
                optionalData -> optionalData, ec.current()
        );
    }

    public CompletionStage<Optional<Integer>> deleteByID(Http.Request request, String id) {
        return repository.deleteByID(Long.parseLong(id)).thenApplyAsync(optionalData -> optionalData, ec.current());
    }

    public CompletionStage<Model> create(Http.Request request) {
        return repository.create(request).thenApplyAsync((s) -> s, ec.current());
    }


}
