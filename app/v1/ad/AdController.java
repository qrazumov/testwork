package v1.ad;

import models.Ad;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.AdService;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class AdController extends Controller {

    private HttpExecutionContext ec;
    private AdService handler;

    @Inject
    public AdController(HttpExecutionContext ec, AdService handler) {
        this.ec = ec;
        this.handler = handler;
    }

    public CompletionStage<Result> list(Http.Request request) {
        return handler.list(request).thenApplyAsync(models -> {
            //final List brandList = models.stream().collect(Collectors.toList());
            return ok(Json.toJson(models));
        }, ec.current());
    }

    public CompletionStage<Result> getByID(Http.Request request, String id) {
        return handler.getByID(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                    ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> updateByID(Http.Request request, String id) {
        return handler.updateByID(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(Json.toJson(r))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> deleteByID(Http.Request request, String id) {
        return handler.deleteByID(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(Json.toJson(r))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        return handler.create(request).thenApplyAsync(b -> created(Json.toJson(b)), ec.current());
    }
}