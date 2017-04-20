package ie.jgriffin.priorityq.persistence;

import ie.jgriffin.priorityq.model.WorkOrder;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by jgriffin on 20/04/2017.
 * <p>
 * Implementations should persist the submitted WorkOrders and encapsulates the operations required to store, remove, and determine WorkOrder data such as position and average wait times.
 */
public interface WorkOrderQueue {

    /**
     * Add the passed WorkOrder to the queue.
     *
     * @param workOrder The WorkOrder to persist.
     * @return true if the workOrder has been added, or if the same workOrder was already added, false if an existing unequal workOrder exists for the same id.
     */
    boolean add(WorkOrder workOrder);

    /**
     * Get the WorkOrder at the head of the queue.
     *
     * @return The WorkOrder at the head of the queue, null if the queue is empty.
     */
    WorkOrder getFirst();

    /**
     * Return the ids of WorkOrders in the queue, sorted according to requirements.
     *
     * @return A list of sorted ids, if the queue is empty an empty list will be returned.
     */
    List<Long> getSortedIds();

    /**
     * Remove the WorkOrder mapped to the passed id.
     *
     * @param id The id to use to remove the WorkOrder.
     * @return The removed WorkOrder, null if it was not in the queue.
     */
    WorkOrder remove(Long id);

    /**
     * Get the position of the WorkOrder with with the passed id, sorted according to requirements, the index is 0 based.
     *
     * @param id The id to use to determine the WorkOrder's position.
     * @return The position in the queue of the associated WorkOrder, the index is 0 based, returns null if the id is not present.
     */
    Integer getPosition(Long id);

    /**
     * Calculate the mean time in seconds that all currently queued WorkOrders have been in the queue.
     *
     * @param referenceDateTime The DateTime used to calculate the time offset.
     * @return The mean time in seconds that all currently queued WorkOrders have been in the queue, 0 if the queue is empty.
     */
    Integer getAverageWaitTime(DateTime referenceDateTime);
}
