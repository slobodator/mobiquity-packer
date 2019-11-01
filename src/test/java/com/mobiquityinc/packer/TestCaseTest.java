package com.mobiquityinc.packer;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class TestCaseTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parseOk() {
        TestCase line = TestCase.parse("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3)");

        assertThat(line.getWeightLimit(), equalTo(new BigDecimal(81)));
        assertThat(line.getThings(), hasSize(3));
    }

    @Test
    public void parseEx() {
        exceptionRule.expectMessage(containsString("does not match the test case"));

        TestCase.parse("incorrect string");
    }

    @Test
    public void noSolution() {
        Solution solution = new TestCase(
                new BigDecimal(1),
                Arrays.asList(
                        new Thing(
                                1,
                                new BigDecimal(10),
                                new BigDecimal(10)
                        ),
                        new Thing(
                                2,
                                new BigDecimal(10),
                                new BigDecimal(10)
                        )
                )
        ).solution();

        assertThat(solution.text(), Matchers.equalTo("-"));
    }

    @Test
    public void singleThing() {
        Solution solution = new TestCase(
                new BigDecimal(3),
                Arrays.asList(
                        new Thing(
                                1,
                                new BigDecimal(3),
                                new BigDecimal(3)
                        ),
                        new Thing(
                                2,
                                new BigDecimal(5),
                                new BigDecimal(5)
                        )
                )
        ).solution();

        assertThat(solution.text(), Matchers.equalTo("1"));
    }

    @Test
    public void betterOne() {
        Solution solution = new TestCase(
                new BigDecimal(3),
                Arrays.asList(
                        new Thing(
                                1,
                                new BigDecimal(3),
                                new BigDecimal(3)
                        ),
                        new Thing(
                                2,
                                new BigDecimal(3),
                                new BigDecimal(5)
                        )
                )
        ).solution();

        assertThat(solution.text(), Matchers.equalTo("2"));
    }

    @Test
    public void complexSearch() {
        Solution solution = new TestCase(
                new BigDecimal(5),
                Arrays.asList(
                        new Thing(
                                1,
                                new BigDecimal(4),
                                new BigDecimal(7)
                        ),
                        new Thing(
                                2,
                                new BigDecimal(2),
                                new BigDecimal(4)
                        ),
                        new Thing(
                                3,
                                new BigDecimal(2),
                                new BigDecimal(4)
                        )
                )
        ).solution();

        assertThat(solution.text(), Matchers.equalTo("2,3"));
    }
}