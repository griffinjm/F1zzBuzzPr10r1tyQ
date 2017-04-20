package ie.jgriffin.priorityq.persistence;

import ie.jgriffin.priorityq.model.WorkOrder;
import ie.jgriffin.priorityq.model.impl.RankComputer;
import ie.jgriffin.priorityq.model.impl.RankConstants;
import ie.jgriffin.priorityq.model.impl.WorkOrderImpl;
import ie.jgriffin.priorityq.persistence.impl.WorkOrderQueueImpl;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class WorkOrderQueueImplTest {


    @Test
    public void add_SameWorkOrderTwice_Succeeds() {
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder workOrder = new WorkOrderImpl(RankConstants.VIP_FACTOR, DateTime.now());

        boolean result1 = workOrderQueue.add(workOrder);
        boolean result2 = workOrderQueue.add(workOrder);

        assertTrue("incorrect result", result1);
        assertTrue("incorrect result", result2);
    }

    @Test
    public void add_SameWorkOrderIdTwice_SecondsFails() {
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder workOrder = new WorkOrderImpl(RankConstants.VIP_FACTOR, DateTime.now());
        WorkOrder workOrder2 = new WorkOrderImpl(RankConstants.VIP_FACTOR, DateTime.now().minusSeconds(10));

        boolean result1 = workOrderQueue.add(workOrder);
        boolean result2 = workOrderQueue.add(workOrder2);

        assertTrue("incorrect result", result1);
        assertFalse("incorrect result", result2);
    }

    @Test
    public void remove_WorkOrderPresent_RemovesAndReturns() {
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder workOrder = new WorkOrderImpl(RankConstants.VIP_FACTOR, DateTime.now());
        workOrderQueue.add(workOrder);

        WorkOrder result = workOrderQueue.remove(RankConstants.VIP_FACTOR);
        assertNotNull(result);
        assertEquals("incorrect workOrder removed", RankConstants.VIP_FACTOR, result.getId().longValue());
    }

    @Test
    public void remove_WorkOrderNotPresent_ReturnsNull() {
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder result = workOrderQueue.remove(RankConstants.VIP_FACTOR);
        assertNull(result);
    }

    @Test
    public void getFirst_ManagementOverrideAndVip_ReturnsManagementOverride() {
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder wo1 = new WorkOrderImpl(RankConstants.VIP_FACTOR, DateTime.now().minusSeconds(10));
        WorkOrder wo2 = new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE, DateTime.now().minusSeconds(5));
        workOrderQueue.add(wo1);
        workOrderQueue.add(wo2);

        WorkOrder workOrder = workOrderQueue.getFirst();

        assertEquals(wo2, workOrder);
    }

    @Test
    public void getAverageWaitTime_ComputesAverage(){
        final int timeDiff = 10;
        DateTime testTime = DateTime.now();
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder wo1 = new WorkOrderImpl(RankConstants.VIP_FACTOR, testTime.minusSeconds(timeDiff));
        WorkOrder wo2 = new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE, testTime.minusSeconds(timeDiff));
        WorkOrder wo3 = new WorkOrderImpl(RankConstants.PRIORITY_FACTOR, testTime.minusSeconds(timeDiff));

        workOrderQueue.add(wo1);
        workOrderQueue.add(wo2);
        workOrderQueue.add(wo3);

        int result = workOrderQueue.getAverageWaitTime(testTime);

        assertEquals("incorrect average wait time", timeDiff, result);
    }

    @Test
    public void getPosition_BothManagementOverride_ReturnsPositionOfSorted(){
        final int timeDiff = 10;
        DateTime testTime = DateTime.now();
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();
        WorkOrder wo1 = new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE, testTime.minusSeconds(timeDiff));
        WorkOrder wo2 = new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE*2, testTime.minusSeconds(timeDiff*2));

        workOrderQueue.add(wo1);
        workOrderQueue.add(wo2);

        int wo1Position = workOrderQueue.getPosition(RankConstants.MANAGEMENT_OVERRIDE);
        int wo2Position = workOrderQueue.getPosition(RankConstants.MANAGEMENT_OVERRIDE*2);

        assertEquals("incorrect position", 1, wo1Position);
        assertEquals("incorrect position", 0, wo2Position);
    }

    @Test
    public void getIds_EmptyList_ReturnsEmptyList(){
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();

        List<Long> idList = workOrderQueue.getSortedIds();

        assertTrue("list is not empty", idList.isEmpty());
    }

    @Test
    public void getIds_Populated_ReturnsPopulatedList(){
        WorkOrderQueue workOrderQueue = new WorkOrderQueueImpl();

        WorkOrder wo1 = new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE, DateTime.now());
        WorkOrder wo2 = new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE*2, DateTime.now());

        workOrderQueue.add(wo1);
        workOrderQueue.add(wo2);
        List<Long> idList = workOrderQueue.getSortedIds();

        assertFalse("list is empty", idList.isEmpty());
        assertEquals("incorrect list size", 2, idList.size());
    }
}
