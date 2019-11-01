package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;

class Thing {
    private final static Pattern PATTERN = Pattern.compile("\\(?(.*),(.*),€(.*?)\\)?");

    private final int index;
    private final BigDecimal weight;
    private final BigDecimal cost;

    /**
     * Real constructor
     */
    Thing(int index, BigDecimal weight, BigDecimal cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    /**
     * Usually I prefer not to use static method, but this one is a kind of named constructor
     */
    static Thing parse(String str) {
        RuntimeException ex = new RuntimeException(format("{0} does not match the thing", str));

        Matcher matcher = PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw ex;
        }

        try {
            return new Thing(
                    Integer.parseInt(matcher.group(1)),
                    new BigDecimal(matcher.group(2)),
                    new BigDecimal(matcher.group(3))
            );
        } catch (Exception e) {
            ex.initCause(e);
            throw ex;
        }
    }

    int getIndex() {
        return index;
    }

    BigDecimal getWeight() {
        return weight;
    }

    BigDecimal getCost() {
        return cost;
    }
}
