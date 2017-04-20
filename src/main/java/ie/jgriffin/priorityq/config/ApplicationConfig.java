package ie.jgriffin.priorityq.config;

import ie.jgriffin.priorityq.persistence.WorkOrderQueue;
import ie.jgriffin.priorityq.rest.WorkOrderRestController;
import ie.jgriffin.priorityq.validation.WorkOrderValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jgriffin on 19/04/2017.
 * <p>
 * The JavaConfig class for this service, creates and configures all necessary beans.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public WorkOrderRestController workOrderRestController() {
        return new WorkOrderRestController(workOrderQueue(), workOrderValidator());
    }

    @Bean
    public WorkOrderQueue workOrderQueue() {
        return new WorkOrderQueue();
    }

    @Bean
    public WorkOrderValidator workOrderValidator() {
        return new WorkOrderValidator();
    }

}
