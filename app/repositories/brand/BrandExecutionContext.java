package repositories.brand;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

/**
 * Custom execution context wired to "brand.repository" thread pool
 */
public class BrandExecutionContext extends CustomExecutionContext {

    @Inject
    public BrandExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "brand.repository");
    }
}
