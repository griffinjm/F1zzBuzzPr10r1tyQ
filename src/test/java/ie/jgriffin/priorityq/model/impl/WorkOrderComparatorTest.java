package ie.jgriffin.priorityq.model.impl;

import ie.jgriffin.priorityq.model.WorkOrder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by jgriffin on 18/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WorkOrderComparatorTest {

    @InjectMocks
    private WorkOrderRankComparator workOrderComparator;

    @Mock
    private RankComputer mockRankComputer;
    @Mock
    private WorkOrder mockWO1;
    @Mock
    private WorkOrder mockWO2;

    @Test
    public void compare_BothComparatorsNotManagementOverride_InvertedRankDifferenceUsedToCompare(){
        when(mockWO1.getId()).thenReturn(RankConstants.PRIORITY_FACTOR);
        when(mockWO2.getId()).thenReturn(RankConstants.PRIORITY_FACTOR);
        when(mockRankComputer.compute(mockWO1)).thenReturn(10D);
        when(mockRankComputer.compute(mockWO2)).thenReturn(1D);

        int result = workOrderComparator.compare(mockWO1, mockWO2);

        assertTrue("incorrect compare result", result < 0);
    }

    @Test
    public void compare_ManagementOverrideFirst_NegativeResult(){
        when(mockWO1.getId()).thenReturn(RankConstants.MANAGEMENT_OVERRIDE);
        when(mockWO2.getId()).thenReturn(RankConstants.PRIORITY_FACTOR);

        int result = workOrderComparator.compare(mockWO1, mockWO2);

        assertTrue("incorrect compare result", result < 0);
    }

    @Test
    public void compare_ManagementOverrideSecond_PositiveResult(){
        when(mockWO1.getId()).thenReturn(RankConstants.PRIORITY_FACTOR);
        when(mockWO2.getId()).thenReturn(RankConstants.MANAGEMENT_OVERRIDE);

        int result = workOrderComparator.compare(mockWO1, mockWO2);

        assertTrue("incorrect compare result", result > 0);
    }

}
