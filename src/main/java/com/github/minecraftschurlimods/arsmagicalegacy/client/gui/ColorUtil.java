package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

public final class ColorUtil {
    public static final int UNKNOWN_SKILL_LINE_COLOR_MASK = 0x999999;
    public static final int KNOWS_COLOR = 0x00ff00;
    public static final int BLACK = 0x000000;
    public static final int WHITE = 0xffffff;
    public static final int GRAY = 0xcccccc;

    private ColorUtil() {
    }

    /**
     * @param color an RGB color value
     * @return the blue value (from 0.0 to 1.0)
     */
    public static float getBlue(int color) {
        return (0xFF & color) / 255f;
    }

    /**
     * @param color an RGB color value
     * @return the green value (from 0.0 to 1.0)
     */
    public static float getGreen(int color) {
        return (0xFF & (color >> 8)) / 255f;
    }

    /**
     * @param color an RGB color value
     * @return the red value (from 0.0 to 1.0)
     */
    public static float getRed(int color) {
        return (0xFF & (color >> 16)) / 255f;
    }

    /**
     * @param rgbColor an RGB color value
     * @return the corresponding HSB value
     */
    public static float[] RGBToHSB(int rgbColor) {
        float hue, saturation, brightness;
        int r = (int) (getRed(rgbColor) * 255);
        int g = (int) (getGreen(rgbColor) * 255);
        int b = (int) (getBlue(rgbColor) * 255);
        int cmax = Math.max(Math.max(r, g), b);
        int cmin = Math.min(Math.min(r, g), b);
        brightness = ((float) cmax) / 255f;
        saturation = cmax != 0 ? ((float) (cmax - cmin)) / ((float) cmax) : 0;
        if (saturation == 0) {
            hue = 0;
        } else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax) hue = bluec - greenc;
            else if (g == cmax) hue = 2f + redc - bluec;
            else hue = 4f + greenc - redc;
            hue = hue / 6f;
            if (hue < 0) hue = hue + 1f;
        }
        return new float[]{hue, saturation, brightness};
    }

    /**
     * @param hue        an HSB hue value
     * @param saturation an HSB saturation value
     * @param brightness an HSB brightness value
     * @return the corresponding RGB color value
     */
    public static int HSBToRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6f;
            float f = h - (float) java.lang.Math.floor(h);
            float p = brightness * (1f - saturation);
            float q = brightness * (1f - saturation * f);
            float t = brightness * (1f - (saturation * (1f - f)));
            switch ((int) h) {
                case 0 -> {
                    r = (int) (brightness * 255f + 0.5f);
                    g = (int) (t * 255f + 0.5f);
                    b = (int) (p * 255f + 0.5f);
                }
                case 1 -> {
                    r = (int) (q * 255f + 0.5f);
                    g = (int) (brightness * 255f + 0.5f);
                    b = (int) (p * 255f + 0.5f);
                }
                case 2 -> {
                    r = (int) (p * 255f + 0.5f);
                    g = (int) (brightness * 255f + 0.5f);
                    b = (int) (t * 255f + 0.5f);
                }
                case 3 -> {
                    r = (int) (p * 255f + 0.5f);
                    g = (int) (q * 255f + 0.5f);
                    b = (int) (brightness * 255f + 0.5f);
                }
                case 4 -> {
                    r = (int) (t * 255f + 0.5f);
                    g = (int) (p * 255f + 0.5f);
                    b = (int) (brightness * 255f + 0.5f);
                }
                case 5 -> {
                    r = (int) (brightness * 255f + 0.5f);
                    g = (int) (p * 255f + 0.5f);
                    b = (int) (q * 255f + 0.5f);
                }
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }

    /**
     * @param colors at least 1 RGB color value
     * @return the average RGB value of all parameters passed in
     */
    public static int calculateAverage(int... colors) {
        float hue = 0;
        float saturation = 0;
        float brightness = 0;
        for (int color : colors) {
            float[] hsb = RGBToHSB(color);
            hue += hsb[0];
            saturation += hsb[1];
            brightness += hsb[2];
        }
        return HSBToRGB(hue / colors.length, saturation / colors.length, brightness / colors.length);
    }
}