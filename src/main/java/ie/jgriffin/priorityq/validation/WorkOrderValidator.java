package ie.jgriffin.priorityq.validation;

import ie.jgriffin.priorityq.model.WorkOrder;

/**
 * Created by jgriffin on 19/04/2017.
 * <p>
 * Class which performs some simple validation on the WorkOrder object and fields.
 */
public class WorkOrderValidator {

    /**
     * Determines if the passed id is valid given the business requirements.
     *
     * @param id The id to validate.
     * @return true if the id is valid, false if invalid.
     */
    public boolean idIsValid(Long id) {
        return id != null && id > 0L;
    }

    /**
     * Determines if the passed WorkOrder is valid given the business requirements.
     *
     * @param workOrder The WorkOrder to validate.
     * @return true if the WorkOrder is valid, false if invalid.
     */
    public boolean workOrderIsValid(WorkOrder workOrder) {
        return workOrder.getId() != null && idIsValid(workOrder.getId()) && workOrder.getDateTime() != null;
    }
}
