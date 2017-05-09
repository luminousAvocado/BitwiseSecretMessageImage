import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;



public class BitwiseGUI extends Application{
	
	//private Image inputImage;
	//private PPMImageSub ppmImage;
	private File inputFile;

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try{
			BorderPane bPane = new BorderPane();
			
			// Top row of bPane
			HBox topHBox = new HBox();
			Button loadFile = new Button("Load File");
			Button hideMessage = new Button("Hide Message");
			Button recoverMessage = new Button("Recover Message");
			Button greyscale = new Button("Greyscale");
			Button sepia = new Button("Sepia");
			Button negative = new Button("Negative");
			topHBox.getChildren().addAll(loadFile, hideMessage, recoverMessage, greyscale, sepia, negative);
			topHBox.setPadding(new Insets(5, 5, 5, 5));
			topHBox.setSpacing(3);
			topHBox.setStyle("-fx-background-color: grey");
			topHBox.setAlignment(Pos.CENTER);
			bPane.setTop(topHBox);
			bPane.setPadding(new Insets(0, 5, 0, 5));
			bPane.setStyle("-fx-background-color: black");
			
			
			
			// Handles "Load File" button
			loadFile.setOnAction(e -> {
				
				//File inputFile = null;
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if(result == fileChooser.APPROVE_OPTION){
					this.inputFile = fileChooser.getSelectedFile();
				}
				PPMImageSub PPMImageOrig = new PPMImageSub(this.inputFile); // Creates instance of PPMImageSub
	
				//Displys image
				try {
					Image origImage = SwingFXUtils.toFXImage(ImageIO.read(this.inputFile), null);
					ImageView imageView = new ImageView(origImage);
					imageView.setFitWidth(744); // Resize image width
					imageView.setFitHeight(900); // Resize image height
					imageView.setPreserveRatio(true); // Preserves ratio
					imageView.setSmooth(true); // true for higher quality filtering when resize
					bPane.setAlignment(imageView, Pos.CENTER);
					bPane.setLeft(imageView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			
			// Handles "Hide Message" button
			hideMessage.setOnAction(e -> {
				
				PPMImageSub PPMImageHide = new PPMImageSub(this.inputFile);
				
				String inputString = JOptionPane.showInputDialog("Enter message to hide:");
				
				char[] msgArray = inputString.toCharArray(); // Convert inputString to array of chars
				
				char[] charArray = new char[msgArray.length + 1]; // Copy array of chars to new array with + 1 length
				for(int index = 0; index < charArray.length - 1; index++){
					charArray[index] = msgArray[index];
				}
				charArray[charArray.length - 1] = '\0'; // Add null terminating char as last array[index]

				String finalString = new String(charArray); // Convert char array back into string
				
				PPMImageHide.hideMessage(finalString); // Hides message and writes to new file
			});
			
			
			// Handles "Recover Message" button
			recoverMessage.setOnAction(e -> {
				
				PPMImageSub PPMImageRecover = new PPMImageSub(this.inputFile);
				
				String recoveredMsg = PPMImageRecover.recoverMessage();

				Text textBox = new Text(recoveredMsg);
				textBox.setFont(Font.font("Heletica", 25));
				textBox.setFill(Color.WHITE);
				bPane.setAlignment(textBox, Pos.CENTER);
				
				bPane.setRight(textBox);
			});
			
			
			// Handles "Greyscale" button
			greyscale.setOnAction(e ->{
				
				PPMImageSub PPMImageGrey = new PPMImageSub(this.inputFile);
				
				PPMImageGrey.greyscale(); // Calls greyscale() method, will create new grey image in project folder
				File greyFile = new File("greyscale.ppm"); // Create File from newly created grey file
				try {
					Image greyImage = SwingFXUtils.toFXImage(ImageIO.read(greyFile), null);
					ImageView imageView = new ImageView(greyImage);
					imageView.setFitWidth(744);
					imageView.setFitHeight(900);
					imageView.setPreserveRatio(true);
					imageView.setSmooth(true);
					bPane.setAlignment(imageView, Pos.CENTER);
					bPane.setRight(imageView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			
			// Handles "Sepia" button
			sepia.setOnAction(e -> {
				
				PPMImageSub PPMImageSepia = new PPMImageSub(this.inputFile);
				
				PPMImageSepia.sepia();
				File sepiaFile = new File("sepia.ppm");
				try{
					Image sepiaImage = SwingFXUtils.toFXImage(ImageIO.read(sepiaFile), null);
					ImageView imageView = new ImageView(sepiaImage);
					imageView.setFitWidth(744);
					imageView.setFitHeight(900);
					imageView.setPreserveRatio(true);
					imageView.setSmooth(true);
					bPane.setAlignment(imageView, Pos.CENTER);
					bPane.setRight(imageView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			
			// Handles "Negative" button
			negative.setOnAction(e -> {
				
				PPMImageSub PPMImageNeg = new PPMImageSub(this.inputFile);
				
				PPMImageNeg.negative();
				File negFile = new File("negative.ppm");
				try{
					Image negImage = SwingFXUtils.toFXImage(ImageIO.read(negFile), null);
					ImageView imageView = new ImageView(negImage);
					imageView.setFitWidth(744);
					imageView.setFitHeight(900);
					imageView.setPreserveRatio(true);
					imageView.setSmooth(true);
					bPane.setAlignment(imageView, Pos.CENTER);
					bPane.setRight(imageView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			
			
			Scene scene = new Scene(bPane, 1500, 950);
			primaryStage.setTitle("Bitwise Operators");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args){
		launch(args);
	}


}
