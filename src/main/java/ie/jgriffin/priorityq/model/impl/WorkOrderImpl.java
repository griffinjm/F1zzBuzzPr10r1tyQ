package ie.jgriffin.priorityq.model.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.jgriffin.priorityq.model.WorkOrder;
import org.joda.time.DateTime;

/**
 * Created by jgriffin on 17/04/2017.
 * This class models a WorkOrder placed in the queue. This class is immutable.
 */
public class WorkOrderImpl implements WorkOrder {

    private final Long id;
    private final DateTime dateTime;

    @JsonCreator
    public WorkOrderImpl(@JsonProperty("id") Long id, @JsonProperty("dateTime") DateTime dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    /**
     * The id of this WorkOrder. Represents the requestor's id.
     *
     * @return a Long representing the id of this WorkOrder.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * The DateTime of this WorkOrder. Represents the moment the WorkOrder was placed.
     *
     * @return a DateTime representing the moment this WorkOrder was placed in the queue.
     */
    @Override
    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkOrderImpl workOrder = (WorkOrderImpl) o;

        if (id != null ? !id.equals(workOrder.id) : workOrder.id != null) return false;
        return dateTime != null ? dateTime.equals(workOrder.dateTime) : workOrder.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkOrderImpl{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                '}';
    }
}
