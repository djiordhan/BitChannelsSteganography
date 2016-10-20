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
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 * @author jordy
 */
public class ImageDecoder {
    
    public static BufferedImage decodeFromCovers(BufferedImage[] covers) {
        Dimension minimum = ImageUtils.getMinimumImagesDimension(covers);
        int minimumWidth = (int)minimum.getWidth();
        int minimumHeight = (int)minimum.getHeight();
        BufferedImage processed = new BufferedImage(minimumWidth, minimumHeight, 5);
        
        for (int y = 0; y < minimumHeight; ++y) {
            int[] currentBinary = new int[24];
            for (int x = 0; x < minimumWidth; ++x) {
                for (int z = 0; z < covers.length; ++z) {
                    int[] binColor = ImageUtils.get3BitFromColor(new Color(covers[z].getRGB(x, y)));
                    currentBinary[z * 3 + 0] = binColor[0];
                    currentBinary[z * 3 + 1] = binColor[1];
                    currentBinary[z * 3 + 2] = binColor[2];
                }
                processed.setRGB(x, y, ImageUtils.getColorFromBinary(currentBinary).getRGB());
            }
        }
        
        return processed;
    }
    
}
