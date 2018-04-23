package client.java;

import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;


public class PlayerCanvas extends ResizableCanvas {

	public PlayerCanvas() {
		// Redraw canvas when size changes.
		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
	}

	public void draw(){
		double width =  getWidth();
		double height = getHeight();

		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);

		//drawPlayers(gc, width, height);
		drawVillains(gc, width, height);
	}

	// Draws players at their current location.
	public void drawPlayers(GraphicsContext g, double width, double height){

		for(Player player : Game.players){
			int position = player.getPosition();
			if(position>19&&position<39)position++;
			double t = -PI/2 + step*position;

			Point2D point = lemniscate(t);
			Point2D offset = playerOffset(player);

			double playerX = point.getX() + (width/2) -10 + offset.getX();
			double playerY = point.getY() + (height/2)-10 + offset.getY();

			g.setFill(player.getColor());
			g.fillOval(playerX,playerY,20,20);

			// Payer ID numbers for testing
			g.setStroke(Color.RED);
			g.strokeText(Integer.toString(player.getId()),playerX+5,playerY+15);
		}

		for(Player p: Game.players ){
			relocatePlayer(p);
		}
	}

	public void drawVillains(GraphicsContext g, double width, double height){
		if(Game.villainGang.isActive()){
			int position = Game.villainGang.getPos();
			if(position>19&&position<39)position++;
			double t = -PI/2 + step*position;

			Point2D point = lemniscate(t);

			double playerX = point.getX() + (width/2) -10;
			double playerY = point.getY() + (height/2)-10;

			g.setFill(Color.BLACK);
			g.fillOval(playerX,playerY,20,20);

			// Payer ID numbers for testing
			g.setStroke(Color.RED);
			g.strokeText("V",playerX+5,playerY+15);
		}
	}

	// Calculates each players x,y offset so they don't draw on top of each other.
	public Point2D playerOffset(Player player){
		double baseOffset = getWidth() / 80;
		double offsetX;
		double offsetY;

		switch(player.getId()){
		case 1:     offsetX = baseOffset;
		offsetY = baseOffset;
		break;
		case 2:     offsetX = baseOffset;
		offsetY = -baseOffset;
		break;
		case 3:     offsetX = -baseOffset;
		offsetY = -baseOffset;
		break;
		case 4:     offsetX = -baseOffset;
		offsetY = baseOffset;
		break;
		default:    offsetX = baseOffset;
		offsetY = baseOffset;
		}

		return new Point2D(offsetX,offsetY);
	}

	public void addPlayerToken(Player p){
		double t = -PI/2 + step*p.getPosition();
		Point2D point = lemniscate(t);
		Point2D offset = playerOffset(p);
		double playerX = point.getX() + (getWidth()/2) + offset.getX();
		double playerY = point.getY() + (getHeight()/2) + offset.getY();

		p.playerToken.setLayoutX(playerX);
		p.playerToken.setLayoutY(playerY);
		p.playerToken.setFill(p.getColor());
		Pane parent = (Pane) this.getParent();
		parent.getChildren().add(p.playerToken);
	}

	public void animatePlayer(Player p, int newPos){
		Polyline path = new Polyline();

		int diff = newPos - p.getPosition();

		for(int i = p.getPosition(); i <= newPos; i++){
			double t = -PI/2 + step*i;
			Point2D point = lemniscate(t);
			Point2D offset = playerOffset(p);
			double playerX = point.getX() + (getWidth()/2) + offset.getX();
			double playerY = point.getY() + (getHeight()/2) + offset.getY();
			path.getPoints().addAll(new Double[]{playerX,playerY});
		}

		PathTransition trans = new PathTransition();
		trans.setNode(p.playerToken);
		trans.setDuration(Duration.seconds(4));
		trans.setPath(path);
		trans.setCycleCount(1);
		trans.play();
	}

	public void relocatePlayer(Player p){
		Pane parent = (Pane) this.getParent();

		double t = -PI/2 + step*p.getPosition();
		Point2D point = lemniscate(t);
		Point2D offset = playerOffset(p);
		double playerX = point.getX() + (getWidth()/2) + offset.getX();
		double playerY = point.getY() + (getHeight()/2) + offset.getY();

		p.playerToken.setCenterX(playerX);
		p.playerToken.setCenterY(playerY);
	}

}
