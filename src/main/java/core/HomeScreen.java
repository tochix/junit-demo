package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeScreen extends Application {
	
	private static final Logger logger = LogManager.getLogger(HomeScreen.class);
	
	private Button solnBtn;
	private TextField leftOperandTxtBox;
	private TextField rightOperandTxtBox;
	private TextField answerTxtBox;
	private ComboBox<String> operatorDropdown;
	private Image[] cardsImg;
	private ImageView imgView;
	
	public static void main(String[] args) {
		logger.info("Home Screen booting up ...");
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initUI(primaryStage);
	}

	private void initUI(Stage primaryStage) {
		Pane canvas = new Pane();
		//canvas.setStyle("-fx-background-color: black");
		
		addControlsToCanvas(canvas);
		setupCardsAnimation(canvas);
		
		Scene scene = new Scene(canvas, 420, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Simple Calculator App");
		primaryStage.show();
	}

	private void setupCardsAnimation(Pane canvas) {
		File cardsDir = new File("src/main/resources/core/cards");
		FilenameFilter imgFilter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().endsWith("jpeg");
			}
		};
		
		File[] cardsFile = cardsDir.listFiles(imgFilter);
		cardsImg = new Image[cardsFile.length];
		int idx = 0;
		
		for (File cardFile : cardsFile) {
			try {
				cardsImg[idx] = new Image(new FileInputStream(cardFile.getPath()));
				idx++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		imgView = new ImageView();
		imgView.setImage(cardsImg[0]);
		imgView.relocate(20, 120);
		imgView.setFitWidth(150);
		imgView.setFitHeight(200);
		imgView.setPreserveRatio(true);
		
		Timeline timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyValue keyValue = new KeyValue(imgView.xProperty(), 200, Interpolator.EASE_BOTH);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
		
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		setCardClickHandler();
				
		canvas.getChildren().addAll(imgView);
	}

	private void setCardClickHandler() {
		Random rand = new Random();
		
		imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			logger.info("Card click detected");
			Image randomImage = cardsImg[rand.nextInt(cardsImg.length)];
			imgView.setImage(randomImage);
		});
	}

	private void addControlsToCanvas(Pane canvas) {
		int row1 = 20;
		int row2 = 60;
		int txtBoxWidth = 50;
		
		Label label = new Label("Simple Calculator using JavaFX");
		label.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
		label.relocate(20, row1);
		
		leftOperandTxtBox = new TextField();
		leftOperandTxtBox.setMaxWidth(txtBoxWidth);
		leftOperandTxtBox.relocate(20, row2);
		
		operatorDropdown = new ComboBox<String>();
		operatorDropdown.getItems().addAll("+", "-", "x", "/", "%");
		operatorDropdown.setValue("+");
		operatorDropdown.relocate(80, row2);
		
		rightOperandTxtBox = new TextField();
		rightOperandTxtBox.setMaxWidth(txtBoxWidth);
		rightOperandTxtBox.relocate(150, row2);
		
		solnBtn = new Button("=");
		solnBtn.relocate(210, row2);
		
		answerTxtBox = new TextField();
		answerTxtBox.setMaxWidth(txtBoxWidth);
		answerTxtBox.setEditable(false);
		answerTxtBox.relocate(250, row2);
		
		setSolnBtnClickHandler();
		
		canvas.getChildren().addAll(label, leftOperandTxtBox, rightOperandTxtBox, 
				operatorDropdown, solnBtn, answerTxtBox);
	}

	private void setSolnBtnClickHandler() {
		solnBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				logger.info("Solving Button clicked");
				
				Double leftOperand = Double.valueOf(leftOperandTxtBox.getText());
				Double rightOperand = Double.valueOf(rightOperandTxtBox.getText());
				String operator = operatorDropdown.getValue();
				
				ArithmeticSolver solver = new ArithmeticSolver();
				String answer = String.valueOf(solver.solve(operator, leftOperand, rightOperand));
				
				answerTxtBox.setText(answer);
			}
		});
	}

}
