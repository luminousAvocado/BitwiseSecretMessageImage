import java.io.File;

import javafx.scene.image.Image;

public class PPMImageSub extends PPMImage{
	
	
	private char[] origPixelData = this.getPixelData();

	
	public PPMImageSub(File inputImage) {
		super(inputImage);
	}
	
	
	public void hideMessage(String message){
		
	}
	
	
	public void recoverMessage(){
		
	}
	
	
	public void greyscale(){
		
		for(int index = 0; index < this.origPixelData.length; index+=3){
			char result = (char) ((this.origPixelData[index] * .299) + (this.origPixelData[index + 1] * .587) + (this.origPixelData[index + 2] * .114));
			
			this.origPixelData[index] = result;
			this.origPixelData[index + 1] = result;
			this.origPixelData[index + 2] =  result;
		}
		this.writeImage("greyscale.ppm");	
	}
	
	
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
		
	
	public void negative(){
		
		for(int index = 0; index < this.origPixelData.length; index+=3){
			this.origPixelData[index] = (char) (255 - this.origPixelData[index]);
			this.origPixelData[index + 1] = (char) (255 - this.origPixelData[index + 1]);
			this.origPixelData[index + 2] = (char) (255 - this.origPixelData[index + 2]);
		}
		this.writeImage("negative.ppm");
	}
	
	
}
