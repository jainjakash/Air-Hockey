import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import java.lang.Math;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
public class Puck{

	private double puckRadius = 15;
	private double puckDiameter = 30;

	private double xPos = 385;
	private double yPos = 185;
	private int player1Score = 0;
	private int player2Score = 0;
	private double xCen = xPos+puckRadius;
	private double yCen = yPos+puckRadius;
	private Image puck = new Image("puck.png");
	private double centerDiff1;
	private double centerDiff2;
	private double magX1;
	private double magY1;
	private double magX2;
	private double magY2;
	private double momentum1;
	private double momentum2;

	private double velocityX = (Math.random()*2)-1;
	private double velocityY = (Math.random()*2)-1;

	public Image getPuck() {
		return puck;
	}

	public double getXVel() {
		return velocityX;
	}

	public double getYVel() {
		return velocityY;
	}

	public double getXCen() {
		return xCen;
	}

	public double getYCen() {
		return yCen;
	}

	public void setXVel(double a) {
		velocityX = a;
	}

	public void setYVel(double b) {
		velocityY = b;
	}

	public Rectangle2D getPuckRect() {
		return new Rectangle2D(xPos, yPos, 30 ,30);
	}
	
	public String player1Score() {
		return Integer.toString(player1Score);
	}
	
	public String player2Score() {
		return Integer.toString(player2Score);
	}

	public void player1Increase() {
		player1Score++;
	}
	
	public void player2Increase() {
		player2Score++;
	}
	
	public int getPlayer1Score() {
		return player1Score;
	}
	
	public int getPlayer2Score() {
		return player2Score;
	}
	
	public void resetPlayerScores() {
		player1Score = 0;
		player2Score = 0;
	}
	
	public void setPos(int x, int y) {
		xPos = x;
		yPos = y;
	}
	

	public boolean checkCollision1(Paddles paddle){
		
		Shape area = Shape.intersect(new Circle(xCen, yCen, puckRadius), new Circle(paddle.getPad1XCen(), paddle.getPad1YCen(), 35));
        if (area.getBoundsInLocal().getWidth() != -1)
          return true;
        
        return false;
	}
	
	
	public boolean checkCollision2(Paddles paddle) {
		
		Shape area2 = Shape.intersect(new Circle(xCen, yCen, puckRadius), new Circle(paddle.getPad2XCen(), paddle.getPad2YCen(), 35));
	    if (area2.getBoundsInLocal().getWidth() != -1)
	      return true;
	    
	    return false;
    
	}
	
	public void reset() {
		xPos = 385;
		yPos = 185;
		player1Score = 0;
		player2Score = 0;
		velocityX = (Math.random()*2)-1;
		velocityY = (Math.random()*2)-1;
	}

   


	public void move(Paddles paddle, Puck puck) {
		
		//Circle 1- Either pad
		//Circle 2- Hockey puck
	
				
		centerDiff1 = Math.sqrt(Math.pow(paddle.getPad1XCen() - xCen, 2) + Math.pow(paddle.getPad1YCen() - yCen, 2));
		centerDiff2 = Math.sqrt(Math.pow(paddle.getPad2XCen() - xCen, 2) + Math.pow(paddle.getPad2YCen() - yCen, 2));
		magX1 =  (xCen - paddle.getPad1XCen() )/ centerDiff1; 
		magY1 = (yCen - paddle.getPad1YCen() )/ centerDiff1; 
		magX2 = (xCen - paddle.getPad2XCen() )/ centerDiff2; 
		magY2 = (yCen - paddle.getPad2YCen() )/ centerDiff2;
		momentum1 = 2 * (paddle.getPad1XVel() * magX1 + paddle.getPad1YVel() * magY1 - velocityX * magX1 - velocityY * magY1) / 2; 
		momentum2 = 2 * (paddle.getPad2XVel() * magX2 + paddle.getPad2YVel() * magY2 - velocityX * magX2 - velocityY * magY2) / 2; 
		
		if (checkCollision1(paddle)) {
			velocityX = velocityX + momentum1 * 3 * magX1; 
			velocityY = velocityY + momentum1 * 3 * magY1;
		}
		
		if (checkCollision2(paddle)) {
			velocityX = velocityX + momentum2 * 3 * magX2; 
			velocityY = velocityY + momentum2 * 3 * magY2;
		}

		xPos+=velocityX;
		yPos+=velocityY;
		xCen = xPos+puckRadius;
		yCen = yPos+puckRadius;

		if (yPos<=0)
			velocityY = 2;

		if (yPos>=400-puckDiameter)
			velocityY = -2;

		if (xPos<=0)
			velocityX = 2;

		if (xPos>=800-puckDiameter)
			velocityX = -2;
		
			
	}

	public void paint(GraphicsContext gc) {
		gc.drawImage(puck, xPos, yPos);
	}


}