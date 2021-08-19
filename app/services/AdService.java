package services;

import models.Ad;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import repositories.ad.AdRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class AdService {

    private final AdRepository repository;
    private final HttpExecutionContext ec;

    @Inject
    public AdService(AdRepository repository, HttpExecutionContext ec) {
        this.repository = repository;
        this.ec = ec;
    }

    public CompletionStage<List> list(Http.Request request) {
        return repository.list(request).thenApplyAsync(m -> m, ec.current());
    }

    public CompletionStage<Optional<Ad>> getByID(Http.Request request, String id) {
        return repository.getByID(Long.parseLong(id)).thenApplyAsync(optionalData -> optionalData, ec.current());
    }

    public CompletionStage<Optional<Ad>> updateByID(Http.Request request, String id) {
        return repository.updateByID(Long.parseLong(id), request).thenApplyAsync(
                optionalData -> optionalData, ec.current()
        );
    }

    public CompletionStage<Optional<Integer>> deleteByID(Http.Request request, String id) {
        return repository.deleteByID(Long.parseLong(id)).thenApplyAsync(optionalData -> optionalData, ec.current());
    }

    public CompletionStage<Ad> create(Http.Request request) {
        return repository.create(request).thenApplyAsync((s) -> s, ec.current());
    }


}
