package com.bdoemu.gameserver.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceUtils {
    private static final Pattern pattern = Pattern.compile("(\\d+)(D)(\\d+)(([\\+\\-\\*/])(\\d+))*");

    public static int[] getDice(final String string) {
        final Matcher matcher = DiceUtils.pattern.matcher(string.replaceAll("\\s", "").toUpperCase());
        if (matcher.matches()) {
            int count = Integer.parseInt(matcher.group(1));
            int sides = Integer.parseInt(matcher.group(3));
            int modifier = 0;
            String modx = matcher.group(4);
            if (modx != null && !modx.trim().isEmpty())
                modifier = Integer.parseInt(modx);
            return new int[]{count, sides, modifier};
        }
        return new int[]{0, 0, 0};
    }

    public static int rollDice(int[] input) {
        int result = 0;
        for (int i = 0; i < input[0]; ++i)
            result += ThreadLocalRandom.current().nextInt(0, input[1] + 1);
        return result + input[2];
    }

    // used for encyclopedia
    public static DiceValue getDiceValueRnd(final String string) {
        final Matcher matcher = DiceUtils.pattern.matcher(string.replaceAll("\\s", ""));
        if (matcher.matches()) {
            final int count = Integer.parseInt(matcher.group(1));
            final int sides = Integer.parseInt(matcher.group(3));
            final int max = count * sides;
            int minMod = count;
            int maxMod = count * sides;
            final String function = matcher.group(5);
            if (function != null) {
                final int mod = Integer.parseInt(matcher.group(6));
                switch (function) {
                    case "-": {
                        minMod -= mod;
                        maxMod -= mod;
                        break;
                    }
                    case "+": {
                        minMod += mod;
                        maxMod += mod;
                        break;
                    }
                    case "/": {
                        minMod /= mod;
                        maxMod /= mod;
                        break;
                    }
                    case "*": {
                        minMod *= mod;
                        maxMod *= mod;
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            return new DiceValue(count, max, minMod, maxMod);
        }
        return new DiceValue(0, 0, 0, 0);
    }

    // Used to load from datapack
    public static int[] getDiceRnd(final String string) {
        return getDice(string);
    }

    public static int[] add(final int[] a, final int[] b) {
        final int[] result = {0, 0};
        result[0] = a[0] + b[0];
        result[1] = a[1] + b[1];
        return result;
    }

    public static int[] sub(final int[] a, final int[] b) {
        final int[] result = {0, 0};
        result[0] = a[0] - b[0];
        result[1] = a[1] - b[1];
        return result;
    }

    public static class DiceValue {
        private int minValue;
        private int maxValue;
        private int minModValue;
        private int maxModValue;

        public DiceValue(final int minValue, final int maxValue, final int minModValue, final int maxModValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.minModValue = minModValue;
            this.maxModValue = maxModValue;
        }

        public int getMaxValue() {
            return this.maxValue;
        }

        public int getMinValue() {
            return this.minValue;
        }

        public int getMaxModValue() {
            return this.maxModValue;
        }

        public int getMinModValue() {
            return this.minModValue;
        }
    }
}
