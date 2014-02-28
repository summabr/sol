package br.com.summa.sol.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTopNTest {
    protected void validate(int n, List<Integer> input, List<Integer> expected) {
        validate(new ListTopN<Integer>(n), input, expected);
        validate(new TreeTopN<Integer>(n), input, expected);
    }

    protected List<Integer> validate(TopN<Integer> topN, List<Integer> input,
            List<Integer> expected) {
        assertTrue(topN.isEmpty());
        topN.addAll(input);
        List<Integer> result = new ArrayList<Integer>(topN);
        assertEquals(expected, result);
        assertEquals(expected.size(), result.size());
        assertEquals(expected.size(), topN.size());
        assertFalse(topN.isEmpty());
        return result;
    }

    protected void validateRepetitionOrder(int n, List<Integer> input, List<Integer> expected) {
        validateRepetitionOrder(new ListTopN<Integer>(n), input, expected);
        validateRepetitionOrder(new TreeTopN<Integer>(n), input, expected);
    }

    protected void validateRepetitionOrder(TopN<Integer> topN, List<Integer> input,
            List<Integer> expected) {
        List<Integer> result = validate(topN, input, expected);
        for (int i=0; i < result.size(); i++) {
            assertTrue(expected.get(i) == result.get(i));
        }
    }
}
