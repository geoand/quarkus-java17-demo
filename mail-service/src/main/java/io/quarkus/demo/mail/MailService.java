package io.quarkus.demo.mail;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Singleton;

@Singleton
public class MailService {

    private final Mailer mailer;

    public MailService(Mailer mailer) {
        this.mailer = mailer;
    }

    @Incoming("orders")
    @Blocking
    public void process(Order order) {
        mailer.send(Mail.withText(
                order.email(),
                "Your recipe: " + order.recipe().title(),
                order.offsettingWorkout().isEmpty() ? "Congratulations on picking a very health meal!" : "We have included a complementary workout plan for you to offset the calories obtained by your meal."
        ));
    }


}
