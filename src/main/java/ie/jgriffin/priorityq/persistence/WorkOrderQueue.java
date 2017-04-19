package ie.jgriffin.priorityq.persistence;

import ie.jgriffin.priorityq.model.WorkOrder;
import ie.jgriffin.priorityq.model.impl.RankComputer;
import ie.jgriffin.priorityq.model.impl.WorkOrderRankComparator;
import org.joda.time.DateTime;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class WorkOrderQueue {

    private final ConcurrentHashMap<Long, WorkOrder> workOrderMap = new ConcurrentHashMap<>();

    public boolean add(WorkOrder workOrder) {
        WorkOrder preExistingWorkOrder = workOrderMap.putIfAbsent(workOrder.getId(), workOrder);
        if (preExistingWorkOrder == null || preExistingWorkOrder.equals(workOrder)) {
            return true;
        }
        return false;
    }

    public WorkOrder getFirst() {
        List<WorkOrder> workOrders = new LinkedList<>(workOrderMap.values());
        Collections.sort(workOrders, new WorkOrderRankComparator(new RankComputer(DateTime.now())));
        if (!workOrders.isEmpty()) {
            return workOrders.get(0);
        }
        return null;
    }

    public List<Long> getIds() {
        List<Long> idList = new LinkedList<>();
        Collection<WorkOrder> workOrders = workOrderMap.values();
        for (WorkOrder workOrder : workOrders) {
            idList.add(workOrder.getId());
        }
        return idList;
    }

    public WorkOrder remove(Long id){
        return workOrderMap.remove(id);
    }

    public Integer getPosition(Long id){
        if (workOrderMap.containsKey(id)){
            List<Long> idList = getIds();
            return idList.indexOf(id);
        }
        return null;
    }
}
