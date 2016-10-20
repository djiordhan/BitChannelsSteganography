/*
 * Copyright 2016 jordy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.djiordhan.bitchannels.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 * @author jordy
 */
public class ImageUtils {
    
    private static int[] getImagePixels(BufferedImage image) {
        int[] ret = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), ret, 0, image.getWidth());
        return ret;
    }
    
    public static int getIntFromBinary(int[] binary) {
        String n = "";
        int counter = 0;
        for (int i : binary) {
            n = i == 1 ? n + "1" : n + "0";
            if (++counter == 24) break;
        }
        return Integer.parseInt(n, 2);
    }
    
    public static int[] getByThrees(int i, int[] binary) {
        int[] ret = new int[3];
        if (i > 0 || i < 9) {
            ret[0] = binary[(i - 1) * 3];
            ret[1] = binary[(i - 1) * 3 + 1];
            ret[2] = binary[(i - 1) * 3 + 2];
        }
        return ret;
    }
    
    public static BufferedImage[] resizeAllCoverImages(BufferedImage[] covers, BufferedImage image) {
        BufferedImage[] ret = new BufferedImage[covers.length];
        int counter = 0;
        for (BufferedImage img : covers) {
            ret[counter++] = img.getWidth() < image.getWidth() || img.getHeight() < image.getHeight() ? ImageResizer.resizeImageByDimension((Dimension)new Dimension(image.getWidth(), image.getHeight()), (BufferedImage)img, (boolean)true, (int)0) : img;
        }
        return ret;
    }
    
    public static Dimension getAverageDimension(BufferedImage[] covers) {
        int w = 0;
        int h = 0;
        for (BufferedImage img : covers) {
            w += img.getWidth();
            h += img.getHeight();
        }
        return new Dimension(w /= covers.length, h /= covers.length);
    }

    public static int getBase256Even(int i) {
        int ret = i;
        if (i % 2 != 0) {
            ret = i == 0 ? i + 1 : i - 1;
        }
        return ret;
    }

    public static int getBase256Odd(int i) {
        int ret = i;
        if (i % 2 == 0) {
            ret = i == 255 ? i - 1 : i + 1;
        }
        return ret;
    }
    
    public static int[] getBinaryFromColor(Color color) {
        int rgb = -1 * color.getRGB();
        int[] ret = new int[24];
        String binary = Integer.toBinaryString(rgb);
        char[] charBinary = binary.toCharArray();
        int counter = charBinary.length - 1;
        for (int x = 23; x >= 0; --x) {
            ret[x] = counter < 0 ? 0 : Integer.parseInt("" + charBinary[counter] + "");
            --counter;
        }
        return ret;
    }
    
    public static Color getColorFromBinary(int[] binary) {
        return new Color(-1 * getIntFromBinary(binary));
    }

    public static int[] get3BitFromColor(Color color) {
        int[] ret = new int[3];
        ret[0] = color.getRed() % 2 == 0 ? 1 : 0;
        ret[1] = color.getGreen() % 2 == 0 ? 1 : 0;
        ret[2] = color.getBlue() % 2 == 0 ? 1 : 0;
        return ret;
    }

    public static int[][] getCoverPixels(BufferedImage[] coverImages, BufferedImage image) {
        int[] imagePixels = getImagePixels(image);
        int[][] coverPixels = new int[coverImages.length][imagePixels.length];
        for (int x = 0; x < coverImages.length; ++x) {
            int[] coverPixel = coverImages[x].getRGB(0, 0, image.getWidth(), image.getHeight(), coverPixels[x], 0, image.getWidth());
            for (int y = 0; y < imagePixels.length - 1; ++y) {
                coverPixels[x][y] = coverPixel[y];
            }
        }
        return coverPixels;
    }

    public static Dimension getMinimumImagesDimension(BufferedImage[] covers) {
        int width = 0;
        int height = 0;
        for (BufferedImage image : covers) {
            if (width == 0) {
                width = image.getWidth();
            } else if (width > image.getWidth()) {
                width = image.getWidth();
            }
            if (height == 0) {
                height = image.getHeight();
                continue;
            }
            if (height <= image.getHeight()) continue;
            height = image.getHeight();
        }
        return new Dimension(width, height);
    }

    
}
