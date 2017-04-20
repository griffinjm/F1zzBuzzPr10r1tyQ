package ie.jgriffin.priorityq.validation;

import ie.jgriffin.priorityq.model.WorkOrder;

/**
 * Created by jgriffin on 19/04/2017.
 * <p>
 * Class which performs some simple validation on the WorkOrder object and fields.
 */
public class WorkOrderValidator {

    public boolean idIsValid(Long id) {
        return id != null && id > 0L;
    }

    public boolean workOrderIsValid(WorkOrder workOrder) {
        return workOrder.getId() != null && idIsValid(workOrder.getId()) && workOrder.getDateTime() != null;
    }
}
