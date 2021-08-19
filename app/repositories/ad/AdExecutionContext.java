package repositories.ad;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

/**
 * Custom execution context wired to "brand.repository" thread pool
 */
public class AdExecutionContext extends CustomExecutionContext {

    @Inject
    public AdExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "ad.repository");
    }
}
