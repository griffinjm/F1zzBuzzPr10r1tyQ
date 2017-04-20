package ie.jgriffin.priorityq.model.impl;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class RankConstants {

    /**
     * The factor used to determine whether a WorkOrder's requestor Id is of type priority.
     */
    public static final long PRIORITY_FACTOR = 3L;
    /**
     * The factor used to determine whether a WorkOrder's requestor Id is of type VIP.
     */
    public static final long VIP_FACTOR = 5L;
    /**
     * The factor used to determine whether a WorkOrder's requestor Id is of type management override.
     */
    public static final long MANAGEMENT_OVERRIDE = PRIORITY_FACTOR * VIP_FACTOR;

}
