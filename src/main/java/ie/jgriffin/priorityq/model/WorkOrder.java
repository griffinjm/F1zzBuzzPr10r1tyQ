package ie.jgriffin.priorityq.model;

import org.joda.time.DateTime;

/**
 * Created by jgriffin on 17/04/2017.
 */
public interface WorkOrder {

    Long getId();

    DateTime getDateTime();
}
