package net.ripper.todoapp.api.config;

import net.ripper.todoapp.api.misc.JsonNullableValueExtractor;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfigurer {
    private static class ValidationFactoryBean extends LocalValidatorFactoryBean {
        @Override
        protected void postProcessConfiguration(javax.validation.Configuration<?> configuration) {
            configuration.addValueExtractor(new JsonNullableValueExtractor());
        }
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        ValidationFactoryBean factoryBean = new ValidationFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        return factoryBean;
    }
}
