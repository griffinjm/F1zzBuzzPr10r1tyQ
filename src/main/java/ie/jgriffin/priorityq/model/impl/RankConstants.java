package ie.jgriffin.priorityq.model.impl;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class RankConstants {

    public static final long PRIORITY_FACTOR = 3L;
    public static final long VIP_FACTOR = 5L;
    public static final long MANAGEMENT_OVERRIDE = PRIORITY_FACTOR * VIP_FACTOR;

}
