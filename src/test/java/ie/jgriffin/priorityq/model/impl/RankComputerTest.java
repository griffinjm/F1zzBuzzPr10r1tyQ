package ie.jgriffin.priorityq.model.impl;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class RankComputerTest {

    DateTime referenceDateTime = DateTime.now();
    RankComputer rankComputer = new RankComputer(referenceDateTime);

    @Test
    public void compute_ManagementOverride_ReturnsElapsedSeconds() {
        final Double timeDiff = 10D;

        Double rank = rankComputer.compute(new WorkOrderImpl(RankConstants.MANAGEMENT_OVERRIDE, referenceDateTime.minusSeconds(timeDiff.intValue())));

        assertEquals("incorrect rank", timeDiff, rank);
    }

    @Test
    public void compute_Normal_ReturnsElapsedSeconds() {
        final Double timeDiff = 10D;

        Double rank = rankComputer.compute(new WorkOrderImpl(1L, referenceDateTime.minusSeconds(timeDiff.intValue())));

        assertEquals("incorrect rank", timeDiff, rank);
    }


    @Test
    public void compute_Priority_ReturnsFormulaicRank() {
        final Double timeDiff = 10D;

        Double rank = rankComputer.compute(new WorkOrderImpl(RankConstants.PRIORITY_FACTOR, referenceDateTime.minusSeconds(timeDiff.intValue())));

        //max (3, n logn)
        Double expectedRank = Math.max(3, timeDiff * Math.log(timeDiff));

        assertEquals("incorrect rank", expectedRank, rank);
    }

    @Test
    public void compute_VIP_ReturnsFormulaicRank() {
        final Double timeDiff = 10D;

        Double rank = rankComputer.compute(new WorkOrderImpl(RankConstants.VIP_FACTOR, referenceDateTime.minusSeconds(timeDiff.intValue())));

        //max (4, 2n logn)
        Double expectedRank = Math.max(4, (timeDiff * 2) * Math.log(timeDiff));

        assertEquals("incorrect rank", expectedRank, rank);
    }
}
