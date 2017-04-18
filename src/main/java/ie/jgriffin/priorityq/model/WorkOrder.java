package ie.jgriffin.priorityq.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/**
 * Created by jgriffin on 17/04/2017.
 */
public class WorkOrder {

    private final Long id;
    private final DateTime dateTime;

    @JsonCreator
    public WorkOrder(@JsonProperty("id") Long id, @JsonProperty("dateTime") DateTime dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public DateTime getDateTime() {
        return dateTime;
    }
}
