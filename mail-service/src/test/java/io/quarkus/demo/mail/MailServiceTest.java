package io.quarkus.demo.mail;

import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;

@QuarkusTest
@QuarkusTestResource(ReactiveMessagingInMemoryConnector.class)
public class MailServiceTest {

    @Inject
    @Any
    InMemoryConnector connector;

    @Inject
    MockMailbox mailbox;

    @Test
    public void test() {
        InMemorySource<Order> ordersSource = connector.source("orders");
        Order order = new Order(
                "test@quarkus.io",
                new Recipe(0L, "", false, false, 0d, "", 0, false, "", "", "", false, false, 0, "", ""),
                List.of()
        );
        ordersSource.send(order);

        await().atMost(Duration.ofSeconds(10)).until(() -> !mailbox.getMessagesSentTo("test@quarkus.io").isEmpty());
    }
}
