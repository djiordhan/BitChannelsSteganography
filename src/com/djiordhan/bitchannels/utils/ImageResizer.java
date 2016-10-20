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

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author jordy
 */
public class ImageResizer {

    public static BufferedImage resizeImageByDimension(Dimension d, BufferedImage image, boolean isBigger, int adjust) {
        BufferedImage ret;
        double percent = 1.0;
        int bWidth = (int) d.getWidth();
        int bHeight = (int) d.getHeight();
        int iWidth = image.getWidth();
        int iHeight = image.getHeight();
        if (isBigger) {
            double averageHeight;
            double averageWidth;
            percent = bWidth < iWidth && bHeight < iHeight ? 1.0 : ((averageWidth = (double) bWidth / (double) iWidth) > (averageHeight = (double) bHeight / (double) iHeight) ? averageWidth : averageHeight);
            ret = ImageResizer.resizeImage(image, (int) ((double) image.getWidth() * percent) + adjust, (int) ((double) image.getHeight() * percent) + adjust);
        } else {
            double averageHeight;
            double averageWidth;
            percent = bWidth > iWidth && bHeight > iHeight ? 1.0 : ((averageWidth = (double) bWidth / (double) iWidth) < (averageHeight = (double) bHeight / (double) iHeight) ? averageWidth : averageHeight);
            ret = ImageResizer.resizeImage(image, (int) ((double) image.getWidth() * percent) - adjust, (int) ((double) image.getHeight() * percent) - adjust);
        }
        return ret;
    }

    public static BufferedImage resizeImage(BufferedImage image, int w, int h) {
        BufferedImage scaled_image = new BufferedImage(w, h, 5);
        Graphics2D graphics = scaled_image.createGraphics();
        graphics.drawImage(image, 0, 0, w, h, null);
        graphics.dispose();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return scaled_image;
    }

    public static BufferedImage resizeByPercent(double percent, BufferedImage img) {
        return ImageResizer.resizeImage(img, (int) (percent * (double) img.getWidth()), (int) ((percent /= 100.0) * (double) img.getHeight()));
    }

}
