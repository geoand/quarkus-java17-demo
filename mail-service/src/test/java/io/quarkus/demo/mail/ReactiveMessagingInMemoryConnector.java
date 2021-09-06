package io.quarkus.demo.mail;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import java.util.Map;

public class ReactiveMessagingInMemoryConnector implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        return InMemoryConnector.switchIncomingChannelsToInMemory("orders");
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}
