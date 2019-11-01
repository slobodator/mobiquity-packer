package com.mobiquityinc.packer;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SolutionTest {
    private Thing thing1 = new Thing(
            1,
            new BigDecimal(22),
            new BigDecimal(33)
    );

    private Thing thing2 = new Thing(
            2,
            new BigDecimal(44),
            new BigDecimal(55)
    );

    @Test
    public void add() {
        Solution solution = new Solution(thing1).add(new Solution(thing2));

        assertThat(solution.getTotalWeight(), equalTo(new BigDecimal(66)));
        assertThat(solution.getTotalCost(), equalTo(new BigDecimal(88)));
    }

    @Test
    public void text() {
        Solution solution = new Solution(Arrays.asList(thing1, thing2));

        assertThat(solution.text(), equalTo("1,2"));
    }
}