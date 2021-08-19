package repositories.model;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

/**
 * Custom execution context wired to "brand.repository" thread pool
 */
public class ModelExecutionContext extends CustomExecutionContext {

    @Inject
    public ModelExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "model.repository");
    }
}
