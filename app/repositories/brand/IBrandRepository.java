package repositories.brand;

import models.Brand;
import play.mvc.Http;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface IBrandRepository {
    CompletionStage<Stream<Brand>> list();

    CompletionStage<Optional<Brand>> getByID(Long id);

    CompletionStage<Optional<Brand>> updateByID(Long id, Http.Request request);

    CompletionStage<Optional<Integer>> deleteByID(Long id);

    CompletionStage<Brand> create(Http.Request request);
}
