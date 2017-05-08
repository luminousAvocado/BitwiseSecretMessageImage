import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
			topHBox.setStyle("-fx-background-color: blue");
			topHBox.setAlignment(Pos.CENTER);
			bPane.setTop(topHBox);
			
			
			
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
					imageView.setFitWidth(750); // Resize image width
					imageView.setFitHeight(900); // Resize image height
					imageView.setPreserveRatio(true); // Preserves ratio
					imageView.setSmooth(true); // true for higher quality filtering when resize
					bPane.setLeft(imageView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			
			// Handles "Greyscale" button
			greyscale.setOnAction(e ->{
				
				PPMImageSub PPMImageGrey = new PPMImageSub(this.inputFile);
				
				PPMImageGrey.greyscale(); // Calls greyscale() method, will create new grey image in project folder
				File greyFile = new File("greyscale.ppm"); // Create File from newly created grey file
				try {
					Image greyImage = SwingFXUtils.toFXImage(ImageIO.read(greyFile), null);
					ImageView imageView = new ImageView(greyImage);
					imageView.setFitWidth(750); // Resize image width
					imageView.setFitHeight(900); // Resize image height
					imageView.setPreserveRatio(true); // Preserves ratio
					imageView.setSmooth(true); // true for higher quality filtering when resize
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
					imageView.setFitWidth(750);
					imageView.setFitHeight(900);
					imageView.setPreserveRatio(true);
					imageView.setSmooth(true);
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
					imageView.setFitWidth(750);
					imageView.setFitHeight(900);
					imageView.setPreserveRatio(true);
					imageView.setSmooth(true);
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
