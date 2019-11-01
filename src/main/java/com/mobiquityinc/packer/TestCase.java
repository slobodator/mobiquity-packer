package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

class TestCase {
    private final static Pattern PATTERN = Pattern.compile("(.*?)\\s*:\\s*(.*?)");

    private final BigDecimal weightLimit;
    private final List<Thing> things;
    private final BigDecimal minWeight;

    /**
     * Internal constructor
     *
     * @param weightLimit weight limit for this test case
     * @param things      an <b>ordered by weight</b> list of things
     * @param minWeight   is weight from which exhaust search starts
     */
    private TestCase(BigDecimal weightLimit, List<Thing> things, BigDecimal minWeight) {
        this.weightLimit = weightLimit;
        this.things = things;
        this.minWeight = minWeight;
    }

    /**
     * Real constructor, sorts incoming things by weight (asc) and cost (desc)
     */
    TestCase(BigDecimal weightLimit, List<Thing> things) {
        this(
                weightLimit,
                things.stream()
                        .sorted(
                                Comparator.comparing(Thing::getWeight)
                                        .thenComparing(Thing::getCost, Comparator.reverseOrder())
                        ).collect(Collectors.toList()),
                BigDecimal.ZERO
        );
    }

    /**
     * Usually I prefer not to use static method, but this one is a kind of named constructor
     */
    static TestCase parse(String str) {
        RuntimeException ex = new RuntimeException(format("{0} does not match the test case", str));

        Matcher matcher = PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw ex;
        }

        try {
            return new TestCase(
                    new BigDecimal(matcher.group(1)),
                    Arrays.stream(matcher.group(2)
                            .split("\\s+"))
                            .map(Thing::parse)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            ex.initCause(e);
            throw ex;
        }
    }

    BigDecimal getWeightLimit() {
        return weightLimit;
    }

    List<Thing> getThings() {
        return things;
    }

    /**
     * Creates a copy of the test case without a thing
     */
    private TestCase copyWithout(Thing thing) {
        ArrayList<Thing> copy = new ArrayList<>(this.things);
        copy.remove(thing);
        return new TestCase(weightLimit.subtract(thing.getWeight()), copy, thing.getWeight());
    }

    /**
     * Finds a solution
     */
    Solution solution() {
        // At first step filter for things that are less heavy than the limit but more that minWeight
        // avoiding duplication at the exhaustive search
        List<Thing> filteredThings = things.stream()
                .filter(
                        thing -> thing.getWeight().compareTo(minWeight) >= 0
                                && thing.getWeight().compareTo(weightLimit) <= 0
                ).collect(Collectors.toList());

        // Let's be a bit smart. If a thing is more expensive and weights lighter that others
        // the best solution should contain it
        // Keep in mind that things are already ordered by weight
        if (filteredThings.size() > 1) {
            Thing first = filteredThings.get(0);
            Thing second = filteredThings.get(1);
            if (first.getCost().compareTo(second.getCost()) >= 0) {
                return new Solution(first).add(copyWithout(first).solution());
            }
        }

        // No more ideas -- exhaustive search
        return filteredThings.stream()
                .map(thing -> new Solution(thing).add(copyWithout(thing).solution()))
                .max(
                        Comparator.comparing(Solution::getTotalCost).
                                thenComparing(Solution::getTotalWeight, Comparator.reverseOrder())
                ).orElse(new Solution());
    }
}
