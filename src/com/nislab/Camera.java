package com.nislab;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Camera implements KeyListener{
    public double xPosition, yPosition, xDirection, yDirection, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;
    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        this.xPosition = x;
        this.yPosition = y;
        this.xDirection = xd;
        this.yDirection = yd;
        this.xPlane = xp;
        this.yPlane = yp;
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyPressed(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = true;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = true;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = true;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = true;
    }

    public void keyReleased(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = false;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = false;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = false;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = false;
    }

    public void update(int[][] map) {
        if(forward) {
            if(map[(int)(xPosition + xDirection * MOVE_SPEED)][(int) yPosition] == 0) {
                xPosition += xDirection * MOVE_SPEED;
            }
            if(map[(int) xPosition][(int)(yPosition + yDirection * MOVE_SPEED)] ==0)
                yPosition += yDirection * MOVE_SPEED;
        }
        if(back) {
            if(map[(int)(xPosition - xDirection * MOVE_SPEED)][(int) yPosition] == 0)
                xPosition -= xDirection * MOVE_SPEED;
            if(map[(int) xPosition][(int)(yPosition - yDirection * MOVE_SPEED)]==0)
                yPosition -= yDirection * MOVE_SPEED;
        }
        if(right) {
            double oldxDir= xDirection;
            xDirection = xDirection *Math.cos(-ROTATION_SPEED) - yDirection *Math.sin(-ROTATION_SPEED);
            yDirection =oldxDir*Math.sin(-ROTATION_SPEED) + yDirection *Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(left) {
            double oldxDir= xDirection;
            xDirection = xDirection *Math.cos(ROTATION_SPEED) - yDirection *Math.sin(ROTATION_SPEED);
            yDirection =oldxDir*Math.sin(ROTATION_SPEED) + yDirection *Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
    }
}
