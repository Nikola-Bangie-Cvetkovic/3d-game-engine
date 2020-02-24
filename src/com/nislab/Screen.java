package com.nislab;

import java.util.ArrayList;
import java.awt.Color;

public class Screen {
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Texture> textures;

    public Screen(int[][] map, int mapWidth, int mapHeight, ArrayList<Texture> textures, int width, int height) {
        this.map = map;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.textures = textures;
        this.width = width;
        this.height = height;
    }

    public int[] update(Camera camera, int[] pixels) {
        for(int n=0; n<pixels.length/2; n++) {
            if(pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.DARK_GRAY.getRGB();
        }
        for(int i=pixels.length/2; i<pixels.length; i++){
            if(pixels[i] != Color.gray.getRGB()) pixels[i] = Color.gray.getRGB();
        }

        for(int x=0; x<width; x=x+1) {
            double cameraX = 2 * x / (double)(width) -1;
            double rayDirX = camera.xDirection + camera.xPlane * cameraX;
            double rayDirY = camera.yDirection + camera.yPlane * cameraX;

            int mapX = (int)camera.xPosition;
            int mapY = (int)camera.yPosition;

            double sideDistX;
            double sideDistY;

            double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
            double perpWallDist;

            int stepX;
            int stepY;
            boolean hit = false;
            int side=0;
            if (rayDirX < 0)
            {
                stepX = -1;
                sideDistX = (camera.xPosition - mapX) * deltaDistX;
            }
            else
            {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPosition) * deltaDistX;
            }
            if (rayDirY < 0)
            {
                stepY = -1;
                sideDistY = (camera.yPosition - mapY) * deltaDistY;
            }
            else
            {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPosition) * deltaDistY;
            }
            while(!hit) {

                if (sideDistX < sideDistY)
                {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }
                else
                {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if(map[mapX][mapY] > 0) hit = true;
            }
            if(side==0)
                perpWallDist = Math.abs((mapX - camera.xPosition + (1 - stepX) / 2) / rayDirX);
            else
                perpWallDist = Math.abs((mapY - camera.yPosition + (1 - stepY) / 2) / rayDirY);
            int lineHeight;
            if(perpWallDist > 0) lineHeight = Math.abs((int)(height / perpWallDist));
            else lineHeight = height;
            int drawStart = -lineHeight/2+ height/2;
            if(drawStart < 0)
                drawStart = 0;
            int drawEnd = lineHeight/2 + height/2;
            if(drawEnd >= height)
                drawEnd = height - 1;
            int texNum = map[mapX][mapY] - 1;
            double wallX;
            if(side==1) {
                wallX = (camera.xPosition + ((mapY - camera.yPosition + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } else {
                wallX = (camera.yPosition + ((mapX - camera.xPosition + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }
            wallX-=Math.floor(wallX);
            int texX = (int)(wallX * (textures.get(texNum).SIZE));
            if(side == 0 && rayDirX > 0) texX = textures.get(texNum).SIZE - texX - 1;
            if(side == 1 && rayDirY < 0) texX = textures.get(texNum).SIZE - texX - 1;
            for(int y=drawStart; y<drawEnd; y++) {
                int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2;
                int color;
                if(side==0) color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
                else color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)]>>1) & 8355711;
                pixels[x + y*(width)] = color;
            }
        }
        return pixels;
    }
}
