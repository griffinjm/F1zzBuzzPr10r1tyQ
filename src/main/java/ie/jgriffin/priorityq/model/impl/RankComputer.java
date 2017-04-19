package ie.jgriffin.priorityq.model.impl;

import ie.jgriffin.priorityq.model.WorkOrder;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class RankComputer {

    /*
     * This reference DateTime is used to ensure that all time computations performed by this instance reference the
     * same instance in time.
     */
    private final DateTime referenceDateTime;

    /**
     * Constructs a new instance of RankComputer.
     *
     * @param referenceDateTime This reference DateTime is used to ensure that all time computations performed by this instance reference the same instance in time.
     */
    public RankComputer(DateTime referenceDateTime) {
        this.referenceDateTime = referenceDateTime;
    }

    /**
     * Computes the rank for the passed WorkOrderImpl.
     *
     * @param workOrder The WorkOrder to compute the ranking for.
     * @return a double representing the ranking for the WorkOrder.
     */
    public Double compute(WorkOrder workOrder) {
        long workOrderId = workOrder.getId();
        long managementOverrideRemainder = workOrderId % RankConstants.MANAGEMENT_OVERRIDE;

        if (managementOverrideRemainder == 0) {
            return computeSecondsElapsed(workOrder);
        } else if (managementOverrideRemainder % RankConstants.VIP_FACTOR == 0) {
            return computeVIPRank(workOrder);
        } else if (managementOverrideRemainder % RankConstants.PRIORITY_FACTOR == 0) {
            return computePriorityRank(workOrder);
        } else {
            return computeSecondsElapsed(workOrder);
        }
    }


    /**
     * @param workOrder The WorkOrder to compare to the reference DateTime to compute how many seconds have elapsed.
     * @return The number of seconds that have elapsed from the reference DateTime.
     */
    private double computeSecondsElapsed(WorkOrder workOrder) {
        return Seconds.secondsBetween(referenceDateTime, workOrder.getDateTime()).getSeconds();
    }

    /**
     * The rank for a VIP WorkOrder is calculated using the formula max(4, 2n log n) where n is the number of seconds that have elapsed from the reference DateTime.
     *
     * @param workOrder The VIP WorkOrder to compute the rank for.
     * @return The rank of the VIP WorkOrder
     */
    private double computeVIPRank(WorkOrder workOrder) {
        final double secondsElapsed = computeSecondsElapsed(workOrder);
        return Math.max(4, (2 * secondsElapsed) * Math.log(secondsElapsed));
    }

    /**
     * The rank for a Priority WorkOrder is calculated using the formula max(3, n log n) where n is the number of seconds that have elapsed from the reference DateTime.
     *
     * @param workOrder The Priority WorkOrder to compute the rank for.
     * @return The rank of the Priority WorkOrder
     */
    private double computePriorityRank(WorkOrder workOrder) {
        final double secondsElapsed = computeSecondsElapsed(workOrder);
        return Math.max(3, secondsElapsed * Math.log(secondsElapsed));
    }
}
