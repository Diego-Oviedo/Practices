package com.sg.foundations.flowcontrol.whiles;
import java.util.*;

public class RollerCoaster {

	public static void main(String[] args) {
		
		Scanner userInput = new Scanner(System.in);

        System.out.println("We're going to go on a roller coaster...");
        System.out.println("Let me know when you want to get off...!");

        String keepRiding = "y";
        int loopsLooped = 0;
//        while (keepRiding.equals("y")) {//if the value is different than y,  it will stop the loop 
//            System.out.println("WHEEEEEEEEEEEEEeEeEEEEeEeeee.....!!!");
//            System.out.print("Want to keep going? (y/n) :");
//            keepRiding = userInput.nextLine();
//            loopsLooped++;
//        }
        
        while (loopsLooped < 10) {//we're gonna have a 10 time ride 
        	System.out.print("\\Ö/ ");
            System.out.println("WHEEEEEEEEEEEEEeEeEEEEeEeeee.....!!!");
            
            loopsLooped++;
        }
        
        
        

        System.out.println("Wow, that was FUN!");
        System.out.println("We looped that loop " + loopsLooped + " times!!");

	}

}