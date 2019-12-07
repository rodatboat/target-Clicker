/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package targetclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author Kentec
 */
public class Actions {
    
    public static void click(int a, int b) throws AWTException{
    Robot r = new Robot();
    
    //MOVES CURSOR
    r.mouseMove(a, b);
    
    //CLICKS
    r.mousePress(InputEvent.BUTTON1_MASK);
    r.mouseRelease(InputEvent.BUTTON1_MASK);
        //System.out.println("clicked");
    }
}
