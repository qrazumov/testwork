package services;

import models.Brand;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import repositories.brand.BrandRepository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class BrandService {

    private final BrandRepository repository;
    private final HttpExecutionContext ec;

    @Inject
    public BrandService(BrandRepository repository, HttpExecutionContext ec) {
        this.repository = repository;
        this.ec = ec;
    }

    public CompletionStage<Stream<Brand>> list(Http.Request request) {
        return repository.list().thenApplyAsync(brandStream -> brandStream, ec.current());
    }

    public CompletionStage<Optional<Brand>> getByID(Http.Request request, String id) {
        return repository.getByID(Long.parseLong(id)).thenApplyAsync(optionalData -> optionalData, ec.current());
    }

    public CompletionStage<Optional<Brand>> updateByID(Http.Request request, String id) {
        return repository.updateByID(Long.parseLong(id), request).thenApplyAsync(
                optionalData -> optionalData, ec.current()
        );
    }

    public CompletionStage<Optional<Integer>> deleteByID(Http.Request request, String id) {
        return repository.deleteByID(Long.parseLong(id)).thenApplyAsync(optionalData -> optionalData, ec.current());
    }

    public CompletionStage<Brand> create(Http.Request request) {
        return repository.create(request).thenApplyAsync((s) -> s, ec.current());
    }


}