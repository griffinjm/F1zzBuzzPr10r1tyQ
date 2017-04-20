package ie.jgriffin.priorityq.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;


/**
 * Created by jgriffin on 17/04/2017.
 * <p>
 * This class models a WorkOrder placed in the queue.
 */
@ApiModel
public interface WorkOrder {

    /**
     * The id of this WorkOrder. Represents the requestor's id.
     *
     * @return a Long representing the id of this WorkOrder.
     */
    @ApiModelProperty(notes = "The requestor id of the workOrder", required = true)
    Long getId();

    /**
     * The DateTime of this WorkOrder. Represents the moment the WorkOrder was placed.
     *
     * @return a DateTime representing the moment this WorkOrder was placed in the queue.
     */
    @ApiModelProperty(notes = "The dateTime the workOrder is submitted", required = true)
    DateTime getDateTime();
}
