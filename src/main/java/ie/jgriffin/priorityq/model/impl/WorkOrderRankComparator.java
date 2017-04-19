package ie.jgriffin.priorityq.model.impl;

import ie.jgriffin.priorityq.model.WorkOrder;


import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by jgriffin on 18/04/2017.
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
