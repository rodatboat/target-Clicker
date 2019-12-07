
//////////////////////////////////////////////////////////////////////////////////////////////
//    _                                   _      ____   _   _          _                    //
//   | |_    __ _   _ __    __ _    ___  | |_   / ___| | | (_)   ___  | | __   ___   _ __   //
//   | __|  / _` | | '__|  / _` |  / _ \ | __| | |     | | | |  / __| | |/ /  / _ \ | '__|  //
//   | |_  | (_| | | |    | (_| | |  __/ | |_  | |___  | | | | | (__  |   <  |  __/ | |     //
//    \__|  \__,_| |_|     \__, |  \___|  \__|  \____| |_| |_|  \___| |_|\_\  \___| |_|     //
//                         |___/                                                            //
//////////////////////////////////////////////////////////////////////////////////////////////

package targetclicker;

import static targetclicker.Actions.click;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;

//////////////////////////////////////
////////@author Ronaldo Suarez////////
//////////////////////////////////////

public class targetClicker {

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        int xaxis = 0;
        int yaxis = 0;

        System.out.println("Type 1 to begin");
        if (scanner.nextInt() == 1) {
            System.out.println("Calibrate the screen...");

            System.out.print("3... ");
            Thread.sleep(1000);
            System.out.print("2... ");
            Thread.sleep(1000);
            System.out.print("1...");
            Thread.sleep(1000);
            System.out.println("");

            xaxis = MouseInfo.getPointerInfo().getLocation().x;
            yaxis = MouseInfo.getPointerInfo().getLocation().y;

            System.out.println(xaxis + ", " + yaxis);
        } else {
            System.out.println("Screen won't be calibrated...");
        }

        int x_start = xaxis;
        int y_start = yaxis;

        Robot robot = new Robot();
        Rectangle capture = new Rectangle(x_start, y_start, 610, 425);
        
        //CLASS THAT ALLOWS FOR USER TO ENTER MORE COLORS TO TARGET
        class newRGB {
            int[] cRGB = new int[3];
            public newRGB(int R, int G, int B) {
                cRGB[0] = R;
                cRGB[1] = G;
                cRGB[2] = B;
            }
            @Override
            public String toString() {
                return "{ " + cRGB[0] + ", " + cRGB[1] + ", " + cRGB[2] + " }";
            }
        }

        //DECLARING COLORS
        newRGB t1 = new newRGB(252, 0, 0); //#fc0000
        
        ///////////////////////////////////////////////////////////////////////
        //FOR TROUBLE SHOOTING, SAVES SCREENSHOT...                          //
        //BufferedImage image = robot.createScreenCapture(capture);          //
        //ImageIO.write(image, "jpg", new File("screen.jpg"));               //
        //System.out.println("Screenshot saved");                            //
        ///////////////////////////////////////////////////////////////////////
        
        //THIS VARIABLE IS USED LATER TO MAKE THE BULLET BUBBLE
        int count = 0;
        Thread.sleep(1500);
        while (true) {
            //CAPTURES A SCREENSHOT OF THE AREA THE ROBOT WILL SEARCH IN...
            BufferedImage image = robot.createScreenCapture(capture);
            
            //c AND d ARE USED TO KEEP TRACK OF THE PLACE WHERE THE BOT LAST CLICKED AFTER A FRAME HAS BEEN PROCESSED
            int c = 0;
            int d = 0;
            
            System.out.println("Done.");
                    
            //LOOPS THROUGH EVERY PIXEL IN EACH FRAME, LOOKING FOR THE TARGET COLOR
            for (int x = 0; x <= image.getWidth() - 1; x++) {
                for (int y = 0; y <= image.getHeight() - 1; y++) {
                    
                    //QUICKLY GETS THE RGB DATA OF EACH PIXEL, AND TURNS IT INTO INTEGERS
                    int color = image.getRGB(x, y);
                    int iRed = (color >> 16) & 0xff;
                    int iGreen = (color >> 8) & 0xff;
                    int iBlue = (color) & 0xff;
                    int[] RGB = new int[3];
                    RGB[0] = iRed;
                    RGB[1] = iBlue;
                    RGB[2] = iGreen;

                    int bubblexupper = 0;
                    int bubbleyupper = 0;
                    int bubblexlower = 0;
                    int bubbleylower = 0;
                    
                    //DECLARING THE SIZE OF THE AREA NOT TO CLICK ON
                    int range = 50;

                    //DIMENSIONS FOR THE MOUSE MOVEMENTS ARE DIFFERENT FROM THE ACTUAL RESOLUTION
                    int b = (y + y_start);
                    int a = (x + x_start);
                    
                    //COMPARES THE RGB VALUES OF EACH PIXEL TO THAT OF THE TARGET COLOR
                    if (Arrays.equals(t1.cRGB, RGB)) {

                        if (count > 0) {
                            bubblexupper = c + range;
                            bubblexlower = c - range;
                            bubbleyupper = d + range;
                            bubbleylower = d - range;
                        }
                        
                        //CHECKING IF IT'S THE SAME TARGET AS THE PREVIOUS ONE
                        if (count > 0 && a < bubblexupper && a > bubblexlower && b < bubbleyupper && b > bubbleylower) {
                            break;
                        }
                        
                        
                        //RESETS THE MOUSE TO (0,0) TO AVOID ANY ERRORS...
                        robot.mouseMove(0, 0);
                        
                        //MOVES TO THE (a,b) COORDINATES AND CLICKS...
                        click(a, b);
                        
                        //KEEPS TRACK OF WHERE THE BOT LAST CLICKED...
                        c = a;
                        d = b;
                        count = 1;
                        
                        Thread.sleep(50); //TIMEOUT BETWEEN CLICKS
                    }
                }
            }
        }
    }

}
