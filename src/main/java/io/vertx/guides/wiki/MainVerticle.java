package io.vertx.guides.wiki;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.guides.wiki.database.WikiDatabaseVerticle;


public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> promise) throws Exception {
        Promise<String> dbVerticleDeploy = Promise.promise();
        vertx.deployVerticle(new WikiDatabaseVerticle(), dbVerticleDeploy);
        dbVerticleDeploy.future().compose(id -> {
            Promise<String> httpVertDeploy = Promise.promise();
            vertx.deployVerticle("io.vertx.guides.wiki.http.HttpServerVerticle",
                    new DeploymentOptions().setInstances(2),
                    httpVertDeploy);
            return httpVertDeploy.future();
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                promise.complete();
            } else {
                promise.fail(ar.cause());
            }
        });
    }
}
