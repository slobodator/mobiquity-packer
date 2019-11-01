package com.mobiquityinc.packer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ThingTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parseOk1() {
        Thing thing = Thing.parse("1,53.38,€45");

        assertThat(thing.getIndex(), equalTo(1));
        assertThat(thing.getWeight(), equalTo(new BigDecimal("53.38")));
        assertThat(thing.getCost(), equalTo(new BigDecimal(45)));
    }

    @Test
    public void parseOk2() {
        Thing thing = Thing.parse("(1,53.38,€45)");

        assertThat(thing.getIndex(), equalTo(1));
        assertThat(thing.getWeight(), equalTo(new BigDecimal("53.38")));
        assertThat(thing.getCost(), equalTo(new BigDecimal(45)));
    }

    @Test
    public void parseEx() {
        exceptionRule.expectMessage(containsString("does not match the thing"));

        Thing.parse("a,b,c");
    }
}