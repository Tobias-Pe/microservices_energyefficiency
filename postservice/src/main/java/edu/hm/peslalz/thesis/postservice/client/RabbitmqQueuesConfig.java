package edu.hm.peslalz.thesis.postservice.client;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqQueuesConfig {
    @Bean
    public Queue statisticsQueue() {
        return new Queue("post-statistics");
    }

    @Bean
    public Queue notificationsQueue() {
        return new Queue("notifications");
    }

    @Bean
    public Binding bindingNotificationsQueue(FanoutExchange fanout,
                                             Queue notificationsQueue) {
        return BindingBuilder.bind(notificationsQueue).to(fanout);
    }

    @Bean
    public Binding bindingStatisticsQueue(FanoutExchange fanout,
                                          Queue statisticsQueue) {
        return BindingBuilder.bind(statisticsQueue).to(fanout);
    }

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("postservice.posts.fanout");
    }
}
