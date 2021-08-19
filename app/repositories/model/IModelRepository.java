package repositories.model;

import models.Brand;
import models.Model;
import play.mvc.Http;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface IModelRepository {
    CompletionStage<Stream<Model>> list();

    CompletionStage<Optional<Model>> getByID(Long id);

    CompletionStage<Optional<Model>> updateByID(Long id, Http.Request request);

    CompletionStage<Optional<Integer>> deleteByID(Long id);

    CompletionStage<Model> create(Http.Request request);
}
