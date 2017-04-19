package ie.jgriffin.priorityq.model.impl;

import ie.jgriffin.priorityq.model.WorkOrder;
import ie.jgriffin.priorityq.model.WorkOrderComputer;

import java.util.Comparator;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class WorkOrderComparator implements Comparator<WorkOrder> {

    private final WorkOrderComputer workOrderComputer;

    public WorkOrderComparator(WorkOrderComputer workOrderComputer) {
        this.workOrderComputer = workOrderComputer;
    }

    @Override
    public int compare(WorkOrder o1, WorkOrder o2) {


        return 0;
    }
}
