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
package com.djiordhan.bitchannels;

import com.djiordhan.bitchannels.utils.ImageUtils;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author jordy
 */
public class ImageEncoder {
    
    public static BufferedImage[] encodeToCovers(BufferedImage[] covers, BufferedImage image) {
        BufferedImage[] ret = ImageUtils.resizeAllCoverImages(covers, image);
        for (int y = 0; y < image.getHeight() - 1; ++y) {
            for (int x = 0; x < image.getWidth() - 1; ++x) {
                for (int z = 0; z < covers.length; ++z) {
                    int[] binary = ImageUtils.getBinaryFromColor(new Color(image.getRGB(x, y)));
                    int[] threes = ImageUtils.getByThrees(z + 1, binary);
                    int encoded = encodeBinaryToColor(threes, new Color(ret[z].getRGB(x, y))).getRGB();
                    ret[z].setRGB(x, y, encoded);
                }
            }
        }
     
        return ret;
    }
    
    private static Color encodeBinaryToColor(int[] bin, Color color) {
        int r = bin[0] == 1 ? ImageUtils.getBase256Even(color.getRed()) : ImageUtils.getBase256Odd(color.getRed());
        int g = bin[1] == 1 ? ImageUtils.getBase256Even(color.getGreen()) : ImageUtils.getBase256Odd(color.getGreen());
        int b = bin[2] == 1 ? ImageUtils.getBase256Even(color.getBlue()) : ImageUtils.getBase256Odd(color.getBlue());
        return new Color(r, g, b);
    }
    
}
