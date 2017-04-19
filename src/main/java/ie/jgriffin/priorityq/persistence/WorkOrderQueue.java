package ie.jgriffin.priorityq.persistence;

import ie.jgriffin.priorityq.model.WorkOrder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class WorkOrderQueue {

    private final ConcurrentHashMap<Long, WorkOrder> workOrderMap = new ConcurrentHashMap<>();
}
