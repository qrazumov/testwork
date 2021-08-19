package controllers;

import org.apache.ibatis.session.SqlSession;
import play.mvc.*;
import utils.MyBatisConfig;
import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    public Result index(Http.Request request) {
        return ok(views.html.index.render());
    }

}