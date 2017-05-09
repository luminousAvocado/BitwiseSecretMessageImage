import java.io.File;

import javafx.scene.image.Image;

public class PPMImageSub extends PPMImage{
	
	
	private char[] origPixelData = this.getPixelData();

	
	public PPMImageSub(File inputImage) {
		super(inputImage);
	}
	
	
	/*
	 * Hides a string in the image
	*/
	public void hideMessage(String message){
		
		/*
		char tempChar = message.charAt(0);
		int tempInt = (int) tempChar;
		
		System.out.println("char: " + tempChar);
		System.out.println("int: " + tempInt);
		
		for(int index = 1; index < 9; index++){
			char mask = (char) (1 << (index - 1));
			System.out.println("mask: " + mask + ", " + (int)mask);
			System.out.println("At: " + index + " , " + (tempChar & mask));
		}
		*/
		int pixelDataCounter = 0; // Used to keep track of origPixelData[index]
		
		// Outer loop goes through each char in String
		for(int messageIndex = 0; messageIndex < message.length(); messageIndex++){
			
			char tempChar = message.charAt(messageIndex);
			
			// Inner loop goes through each bit of char, starts from end(left)
			for(int bitPos = 8; bitPos > 0; bitPos--){
				
				char msgMask = (char) (1 << (bitPos - 1)); // Creates a mask for the bit position we're at
				
				int bitStatus = (tempChar & msgMask); // This checks the status of char at bit position. 0 = off(0), > 0 = on(1)
				
				
				if(bitStatus > 0){ // If on, then switch pixelData[counter] to on on LEAST SIG(right)
					char pixelMask = (char) (1 << (1 - 1)); // Mask for singling out least sig bit in pixelData[counter]
					origPixelData[pixelDataCounter] = (char) (origPixelData[pixelDataCounter] | pixelMask); // Switches on least sig
					pixelDataCounter++;
				}
				else{ // If off, then switch pixelData[counter] to off on LEAST SIG(right)
					char pixelMask = (char) (~(1 << (1 - 1)));
					origPixelData[pixelDataCounter] = (char) (origPixelData[pixelDataCounter] & pixelMask);
					pixelDataCounter++;
				}
			}
		}
		
		this.writeImage("hiddenMsg.ppm");
		
		/*// This will print the least sig bit in each char in the array up to however far up the array we went
		for(int counter = 0; counter < pixelDataCounter; counter++){
			char testMask = (char) (1 << (1 - 1));
			System.out.println(origPixelData[counter] & testMask);
		}
		*/
	}
	
	
	/*
	 * Recovers a string from an image
	*/
	public String recoverMessage(){
		
		
	}
	
	
	/*
	 * Creates a greyscale copy of image
	*/
	public void greyscale(){
		
		for(int index = 0; index < this.origPixelData.length; index+=3){
			char result = (char) ((this.origPixelData[index] * .299) + (this.origPixelData[index + 1] * .587) + (this.origPixelData[index + 2] * .114));
			
			this.origPixelData[index] = result;
			this.origPixelData[index + 1] = result;
			this.origPixelData[index + 2] =  result;
		}
		this.writeImage("greyscale.ppm");	
	}
	
	
	/*
	 * Creates a sepia copy of image
	*/
	public void sepia(){
		
		for(int index = 0; index < this.origPixelData.length; index+=3){
			char red = (char) ((this.origPixelData[index] * .393) + (this.origPixelData[index + 1] * .769) + (this.origPixelData[index + 2] * .189));
			char green = (char) ((this.origPixelData[index] * .349) + (this.origPixelData[index + 1] * .686) + (this.origPixelData[index + 2] * .168));
			char blue = (char) ((this.origPixelData[index] * .272) + (this.origPixelData[index + 1] * .534) + (this.origPixelData[index + 2] * .131));
			
			if(red > 255){
				red = (char) 255;
			}
			this.origPixelData[index] = red;
			
			if(green > 255){
				green = (char) 255;
			}
			this.origPixelData[index + 1] = green;
			
			if(blue > 255){
				blue = (char) 255;
			}
			this.origPixelData[index + 2] = blue;
		}
		this.writeImage("sepia.ppm");
	}
		
	
	/*
	 * Creates a negative copy of image
	*/
	public void negative(){
		
		for(int index = 0; index < this.origPixelData.length; index+=3){
			this.origPixelData[index] = (char) (255 - this.origPixelData[index]);
			this.origPixelData[index + 1] = (char) (255 - this.origPixelData[index + 1]);
			this.origPixelData[index + 2] = (char) (255 - this.origPixelData[index + 2]);
		}
		this.writeImage("negative.ppm");
	}
	
	
}
