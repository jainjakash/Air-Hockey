import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

public class AirHockey extends Application implements EventHandler<InputEvent>{
	GraphicsContext gc;
	AnimateObjects animate;
	Paddles paddle;
	Puck puck;
	Canvas canvas;
	Rectangle2D goal1;
	Rectangle2D goal2;
	URL resource;
	URL resource2;
	AudioClip airHockeyMusic;
	AudioClip clip2;
	int goal1Counter = 0;
	int goal2Counter = 0;
	int startMenuCounter = 0;
	boolean startMenu = true;
	boolean goalAudio = true;

	public static void main(String[]args){
		launch();
	}
	public void start(Stage stage){
		stage.setTitle("Air Hockey");
		Group root = new Group();
		canvas = new Canvas(800, 400);
		paddle = new Paddles();
		puck = new Puck();
		root.getChildren().add(canvas);
		resource = getClass().getResource("airHockeyMusic.mp3");
		resource2 = getClass().getResource("goalScored.mp3");
		airHockeyMusic = new AudioClip(resource.toString());
		clip2 = new AudioClip(resource2.toString());
		animate = new AnimateObjects();
		animate.start();
		Scene scene = new Scene(root, 800, 400, Color.BLACK);
		gc = canvas.getGraphicsContext2D();
		gc.drawImage(paddle.getPaddle1(), 165, 165);
		gc.drawImage(paddle.getPaddle2(), 565, 165);
		gc.drawImage(puck.getPuck(), 385, 185);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, this);
		stage.setScene(scene);
		stage.show();
	}
	public void handle (final InputEvent event){
		if (event.getEventType() == KeyEvent.KEY_PRESSED){
			if (((KeyEvent)event).getCode() == KeyCode.W)
				paddle.pad1Up();
			if (((KeyEvent)event).getCode() == KeyCode.A)
				paddle.pad1Left();
			if (((KeyEvent)event).getCode() == KeyCode.S)
				paddle.pad1Down();
			if (((KeyEvent)event).getCode() == KeyCode.D)
				paddle.pad1Right();
			if (((KeyEvent)event).getCode() == KeyCode.UP)
				paddle.pad2Up();
			if (((KeyEvent)event).getCode() == KeyCode.LEFT)
				paddle.pad2Left();
			if (((KeyEvent)event).getCode() == KeyCode.DOWN)
				paddle.pad2Down();
			if (((KeyEvent)event).getCode() == KeyCode.RIGHT)
				paddle.pad2Right();
			if (((KeyEvent)event).getCode() == KeyCode.SPACE) {
				//restart
				puck.reset();
				paddle.reset();
			}
			if (((KeyEvent)event).getCode() == KeyCode.ENTER) {
				//exit start menu
				startMenu = false;
				airHockeyMusic.play();
			}
		}		
		if (event.getEventType() == KeyEvent.KEY_RELEASED){
			if (((KeyEvent)event).getCode() == KeyCode.W)
				paddle.pad1Released(false);
			if (((KeyEvent)event).getCode() == KeyCode.A)
				paddle.pad1Released(true);
			if (((KeyEvent)event).getCode() == KeyCode.S)
				paddle.pad1Released(false);
			if (((KeyEvent)event).getCode() == KeyCode.D)
				paddle.pad1Released(true);
			if (((KeyEvent)event).getCode() == KeyCode.UP)
				paddle.pad2Released(false);
			if (((KeyEvent)event).getCode() == KeyCode.LEFT)
				paddle.pad2Released(true);
			if (((KeyEvent)event).getCode() == KeyCode.DOWN)
				paddle.pad2Released(false);
			if (((KeyEvent)event).getCode() == KeyCode.RIGHT)
				paddle.pad2Released(true);
		}
	}
	public class AnimateObjects extends AnimationTimer{		
		public void handle(long now){
			Font originalFont = Font.font("Times New Roman", FontWeight.NORMAL, 36);
			gc.setFill(Color.GREENYELLOW);
			gc.clearRect(0,  0,  800, 400);
			gc.setFont(originalFont);
			if (startMenu == true) {
				if (startMenuCounter < 0) {
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					startMenu = false;
				}
				else {
					gc.fillText("Air Hockey Game- 2 Player", 175, 100);
					gc.fillText("Good Luck!", 300, 150);
					gc.fillText("First to 5 Goals wins!", 225, 200);
					gc.fillText("PRESS SPACE AT ANY TIME TO RESTART", 50, 250);
					gc.fillText("PRESS THE ENTER KEY TO CONTINUE", 75, 300);
					startMenuCounter++;
				}
				
			}
			else {
				gc.setFill(Color.WHITE);
				
				//center line
				gc.fillRect(395, 0, 10, 400);
				
				// 1st goal
				gc.fillRect(0, 140, 50, 10);
				gc.fillRect(0, 250, 50, 10);
				gc.fillRect(50, 140, 10, 120);
				
				//2nd goal
				
				gc.fillRect(740, 140, 60, 10);
				gc.fillRect(740, 250, 60, 10);
				gc.fillRect(740, 140, 10, 120);
				
				Font font = Font.font("Arial", FontWeight.NORMAL, 36);
				gc.setFont(font);
				gc.fillText(puck.player1Score(), 10, 50);
				gc.fillText(puck.player2Score(), 770, 50);
				
				goal1 = new Rectangle2D(0, 140, 60, 120); 
				goal2 = new Rectangle2D(740, 140, 60, 120);	
				
				if (goal1.contains(puck.getPuckRect()) == true) {
					if (goalAudio == true) {
						airHockeyMusic.stop();
						clip2.play();
						puck.player2Increase();
						goalAudio = false;
					}
					puck.setXVel(0);
					puck.setYVel(0);
					paddle.setPad1Pos(165, 165);
					paddle.setPad2Pos(565, 165);
					if (goal1Counter == 150) {
						if (puck.getPlayer2Score() == 5) {
							puck.resetPlayerScores();
						}
						goal1Counter = 0;
						puck.setPos(385, 185);
						puck.setXVel(-1);
						puck.setYVel(0);
						puck.paint(gc);
						paddle.paint(gc);
						goalAudio = true;
						airHockeyMusic.play();
					}
					else {
						if (puck.getPlayer2Score() == 5) {
							gc.fillText("Player 2  Wins!", 260, 200);
							goal1Counter++;
						}
						else {
							gc.fillText("Player 2  Scores!", 260, 200);
							goal1Counter++;
						}
					}
					
				}
				else if (goal2.contains(puck.getPuckRect()) == true) {
					if (goalAudio == true) {
						airHockeyMusic.stop();
						clip2.play();
						puck.player1Increase();
						goalAudio = false;
					}
					puck.setXVel(0);
					puck.setYVel(0);
					paddle.setPad1Pos(165, 165);
					paddle.setPad2Pos(565, 165);
					if (goal2Counter == 150) {
						if (puck.getPlayer1Score() == 5) {
							puck.reset();
							paddle.reset();
						}
						goal2Counter = 0;
						puck.setPos(385, 185);
						puck.setXVel(1);
						puck.setYVel(0);
						puck.paint(gc);
						paddle.paint(gc);
						goalAudio = true;
						airHockeyMusic.play();
					}
					else {
						if (puck.getPlayer1Score() == 5) {
							gc.fillText("Player 1  Wins!", 260, 200);
							goal2Counter++;
						}
						else {
							gc.fillText("Player 1  Scores!", 260, 200);
							goal2Counter++;
						}
					
				}
				}

				paddle.move();
				puck.move(paddle, puck);
				paddle.paint(gc);
				puck.paint(gc);
				
				
				
			}
		}
	}
			
}
