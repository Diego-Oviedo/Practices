package com.sg.foundations.flowcontrol.ifs;

public class SpaceRustlers {

	
	public static void main(String[] args) {
		
		
		 int spaceships = 10;
	        int aliens = 250;
	        int cows = 100;
	        
	        /**** An IF will follow an instruction, only if the condition established comply with the stated result+***/
	        /**** while and ELSE IF will follow an instruction if an condition is met and not previous condition has been met***/
	        
	        
	        if(aliens > spaceships){
	            System.out.println("Vrroom, vroom! Let's get going!");
	        } else{
	            System.out.println("There aren't enough green guys to drive these ships!");
	        } if(aliens > cows) {
	        	
	        	System.out.println("Hurrah, we've got the grub! Hamburger party on Alpha Centauri!");
	        	
	        }else if (cows >= aliens) {
	        	
	        	System.out.println("Oh no! The herds got restless and took over! Looks like we're hamburger now!!");
	        }

	        if(cows == spaceships){
	            System.out.println("Wow, way to plan ahead! JUST enough room for all these walking hamburgers!");
	        } else if (cows > spaceships){
	            System.out.println("Dangit! I don't how we're going to fit all these cows in here!");
	        } else {
	            System.out.println("Too many ships! Not enough cows.");
	        }
	    }
		
	
}

