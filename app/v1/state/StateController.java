package v1.state;

import com.typesafe.config.Config;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class StateController extends Controller {

    @Inject
    private Config config;

    public Result health(Http.Request request) {
        return ok();
    }

    public Result version(Http.Request request) {
        String versionAPI = config.getString("versionAPI");
        return ok(versionAPI);
    }

}
