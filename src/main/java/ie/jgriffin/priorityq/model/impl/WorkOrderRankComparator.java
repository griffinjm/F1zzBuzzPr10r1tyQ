package ie.jgriffin.priorityq.model.impl;

import ie.jgriffin.priorityq.model.WorkOrder;


import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by jgriffin on 18/04/2017.
 * <p>
 * The Comparator used to sort WorkOrders in the WorkOrderQueue by rank, higher ranked WorkOrders will be placed at the head of the queue.
 */
public class WorkOrderRankComparator implements Comparator<WorkOrder>, Serializable {

    private final RankComputer rankComputer;

    public WorkOrderRankComparator(RankComputer rankComputer) {
        this.rankComputer = rankComputer;
    }

    @Override
    public int compare(WorkOrder o1, WorkOrder o2) {
        boolean managementOverrideO1 = o1.getId() % RankConstants.MANAGEMENT_OVERRIDE == 0;
        boolean managementOverrideO2 = o2.getId() % RankConstants.MANAGEMENT_OVERRIDE == 0;

        //special case: if only one of the workOrders is a management override id then there is no need to compute rank
        if (managementOverrideO1 && !managementOverrideO2) {
            return -1;
        } else if (!managementOverrideO1 && managementOverrideO2) {
            return 1;
        } else {
            //the higher ranked objects should be at the head of the queue, so reverse the computed rank difference
            return -((Double) (rankComputer.compute(o1) - rankComputer.compute(o2))).intValue();
        }
    }
}
