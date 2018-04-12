package client.java;

import client.java.controllers.InGameController;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;


public class BoardCanvas extends ResizableCanvas {

	private ArrayList<Location> locations = new ArrayList<>();
	private int currentTile;


	public BoardCanvas() {
		// Redraw canvas when size changes
		currentTile = 0;
		initializeLocations();
		widthProperty().addListener(evt -> {
			try {
				draw();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		heightProperty().addListener(evt -> {
			try {
				draw();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void draw() throws IOException, JSONException{
		double width =  getWidth();
		double height = getHeight();

		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);

		Image background = new Image("/client/resources/images/space.jpg");
		gc.drawImage(background,0,0,width,height);

		drawInfinityShape(gc);
		drawTiles(gc);
	}

	// Draws infinity curve
	public void drawInfinityShape(GraphicsContext g){
		double width = getWidth();
		double height = getHeight();

		PixelWriter pw = g.getPixelWriter();

		for(double t = -PI; t<PI; t+=0.001){
			Point2D point = lemniscate(t);

			int pixel_x =(int) Math.round(point.getX() + width/2) ;
			int pixel_y =(int) Math.round(point.getY()+ height/2);

			pw.setColor(pixel_x,pixel_y, Color.GOLD);
		}
	}

	// Draws all tiles.
	public void drawTiles(GraphicsContext g) throws IOException, JSONException{
		int locIndex = 0;
		for(double t = -PI/2; t<PI-step; t+=step){
			if(Math.abs(t-PI/2) > 0.000001){    // so we don't draw two tiles in the centre.
				Point2D point = lemniscate(t);
				drawTile(point, g, locations.get(locIndex));
				locIndex+=1;
			}
		}
		for(double t = -PI; t<-PI/2-step; t+=step){
			Point2D point = lemniscate(t);
			drawTile(point, g, locations.get(locIndex));
			locIndex+=1;
		}
		// Redraw centre tile so that its not overlapped
		double t = PI/2;
		Point2D boardCenter = lemniscate(t);
		drawTile(boardCenter,g,locations.get(0));
	}

	// Draws individual tiles.
	public void drawTile(Point2D point, GraphicsContext g, Location location) throws IOException, JSONException{

		double width = getWidth();
		double height = getHeight();
		double x = point.getX();
		double y = point.getY();

		g.setFill(Color.BLACK);
		g.setStroke(location.getColour());

		//Platform.runLater(() -> {
			if (!isNumeric(location.getName())) {
				try {

					Image image = new Image(
							"/client/resources/images/worlds/" + location.getName().trim().replace(":", "") + ".jpg");
					if (image != null) {
						Pane p = (Pane) getParent();
						Circle circle = new Circle(x + (width / 2), y + (height / 2), width / 30 - 6);
						circle.setStroke(location.getColour());
						circle.setFill(new ImagePattern(image));
						p.getChildren().add(circle);
					}
				} catch (Exception e) {
					System.out.println(location.getName());
					g.fillOval(x + (width / 2) - width / 30, y + (height / 2) - width / 30, width / 15, width / 15);
					g.strokeOval(x + (width / 2) - width / 30, y + (height / 2) - width / 30, width / 15, width / 15);
				}
			} else {
				g.fillOval(x + (width / 2) - width / 30, y + (height / 2) - width / 30, width / 15, width / 15);
				g.strokeOval(x + (width / 2) - width / 30, y + (height / 2) - width / 30, width / 15, width / 15);
			} 
		//}//);
		g.setFill(Color.WHITE);
		g.fillText(location.getName(),x + (width/2) - 20,y + (height/2));
	}

	public static boolean isNumeric(String str)  {  
		try  { Double.parseDouble(str);  }
		catch(NumberFormatException nfe) { return false;  
		}  return true;  
	}
	public void initializeLocations(){
		for(int index = 0; index<40; index++){
			String initName = Integer.toString(index);
			locations.add(new Location(initName, index, 0,0,0, Color.GOLD, false));
		}
	}

	// Adds new updated location to location list.
	public void updateLocations(List<Location> locs) throws IOException, JSONException{
		for(Location l : locs){
			if(locations.contains(l)) {
				updateLocationData(l);
			}
			else{
				System.out.println("Couldn't find that location.");
			}
		}
		draw();
	}

	public void updateLocationData(Location location){
		if(locations.contains(location)){
			int index = locations.indexOf(location);
			locations.get(index).setName(location.getName());
			locations.get(index).setRent(location.getRent());
			locations.get(index).setPrice(location.getPosition());
			locations.get(index).setOwnerID(location.getOwnerID());
			locations.get(index).setColour(location.getColour());
			// etc
		}
	}

	public Location getLocation(int position){
		for(Location loc: locations){
			if(loc.getPosition() == position){
				return loc;
			}
		}
		return null;
	}


}
