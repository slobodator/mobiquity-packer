package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

class Solution {
    private final Collection<Thing> things;
    private final BigDecimal totalCost;
    private final BigDecimal totalWeight;

    /**
     * Main constructor. To have less parameters totalCost and totalWeight are computing each time.
     * Hope this is not an issue
     */
    Solution(Collection<Thing> things) {
        this.things = things;
        this.totalCost = things.stream()
                .map(Thing::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalWeight = things.stream()
                .map(Thing::getWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Starts new solution from the thing
     */
    Solution(Thing thing) {
        this(Collections.singleton(thing));
    }

    /**
     * Empty solution, no more things are available to pack
     */
    Solution() {
        this(Collections.emptyList());
    }

    BigDecimal getTotalCost() {
        return totalCost;
    }

    BigDecimal getTotalWeight() {
        return totalWeight;
    }

    /**
     * Add two solutions together combining their things
     */
    Solution add(Solution solution) {
        Collection<Thing> allThings = new ArrayList<>(this.things);
        allThings.addAll(solution.things);

        return new Solution(allThings);
    }

    /**
     * Represents solution as text
     */
    String text() {
        if (things.isEmpty()) {
            return "-";
        }

        return things.stream()
                .map(Thing::getIndex)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
