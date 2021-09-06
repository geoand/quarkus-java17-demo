package io.quarkus.demo.wellness;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import java.util.Map;

public class ReactiveMessagingInMemoryConnector implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        return InMemoryConnector.switchOutgoingChannelsToInMemory("ordered-recipes");
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}
