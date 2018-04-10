package client.java;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;

public class ImageCreator {

	public javafx.scene.image.Image getImage(String query) throws IOException, JSONException {
		String key = "AIzaSyDJEYAc_d2j-WkTTA-Onl4DOLd8JeIIkAM";
		URL url = new URL(
				"https://www.googleapis.com/customsearch/v1?key="
						+ key
						+ "&cx=005549965342671456294:x_n33ych_qm&q="
						+ query 
						+ "&searchType=image"
						+ "&alt=json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		conn.setDoInput(true);

		//Read response
		try{
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = br.readLine();
			StringBuilder output = new StringBuilder();
			while (line != null) {
				output.append(line);
				line = br.readLine();
			}

			//Get link to image
			JSONObject object = new JSONObject(output.toString());
			JSONArray array = (JSONArray) object.get("items");
			object = array.getJSONObject(0);
			String link = (String) object.get("link");

			//Get image from link
			URL pic = new URL(link);
			Image image = ImageIO.read(pic);
			BufferedImage im = (BufferedImage) image;
			javafx.scene.image.Image i = SwingFXUtils.toFXImage(im, null);

		/*	//Display image in a JFrame
				JFrame frame = new JFrame();
		frame.setSize(image.getWidth(frame), image.getHeight(frame));
		JLabel label = new JLabel(new ImageIcon(image));
		frame.add(label);
		frame.setVisible(true);*/

			in.close();
			br.close();
			conn.disconnect(); 

			return i;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
