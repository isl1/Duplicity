package world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;


public class Load {
	
	public Load() {}
	
	Vector3f rotation;

	public Vector3f load() throws IOException {
		
		Vector3f position = new Vector3f(215, 0, 616);
		Vector3f rotation = new Vector3f(0, 0, 0);
		String file = "src/world/save/player_position.txt";
		FileReader fr = new FileReader(file);
		BufferedReader textReader = new BufferedReader(fr);
		//String aLine;
		int numLines = 5;
		//while((aLine = textReader.readLine()) != null){
		//	numLines++;
		//}
		String[] data = new String[numLines];
		
		for(int i = 0; i < numLines; i++){
			data[i] = textReader.readLine();
		}
		textReader.close();
		//System.out.println(data[0]);
		//System.out.println(data[1]);
		//System.out.println(data[2]);
		
		position.x = Float.parseFloat(data[0]);
		position.y = Float.parseFloat(data[1]);
		position.z = Float.parseFloat(data[2]);
		rotation.x = Float.parseFloat(data[3]);
		rotation.y = Float.parseFloat(data[4]);
		
		this.rotation = rotation;
		return position;
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
}
