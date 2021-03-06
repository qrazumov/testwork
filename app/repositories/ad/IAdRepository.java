package repositories.ad;

import models.Ad;
import play.mvc.Http;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface IAdRepository {
    CompletionStage<List> list(Http.Request request);

    CompletionStage<Optional<Ad>> getByID(Long id);

    CompletionStage<Optional<Ad>> updateByID(Long id, Http.Request request);

    CompletionStage<Optional<Integer>> deleteByID(Long id);

    CompletionStage<Ad> create(Http.Request request);
}
