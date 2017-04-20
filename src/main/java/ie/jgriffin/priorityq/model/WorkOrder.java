package ie.jgriffin.priorityq.model;

import org.joda.time.DateTime;

/**
 * Created by jgriffin on 17/04/2017.
 * <p>
 * This class models a WorkOrder placed in the queue.
 */
public interface WorkOrder {

    /**
     * The id of this WorkOrder. Represents the requestor's id.
     *
     * @return a Long representing the id of this WorkOrder.
     */
    Long getId();

    /**
     * The DateTime of this WorkOrder. Represents the moment the WorkOrder was placed.
     *
     * @return a DateTime representing the moment this WorkOrder was placed in the queue.
     */
    DateTime getDateTime();
}
