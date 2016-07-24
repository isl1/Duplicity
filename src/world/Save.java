package world;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import input.KeyboardInput;

public class Save {
	
	public Save() {}
	
	Vector3f rotation = new Vector3f(0, 0, 0);

	public void save(Vector3f position, Vector3f rotation) throws IOException {
		
		this.rotation = rotation;
			String x = Float.toString(position.x);
			String y = Float.toString(position.y);
			String z = Float.toString(position.z);
			
			String yaw = Float.toString(rotation.x);
			String pitch = Float.toString(rotation.y);
		
			String data[] = {x, y, z, yaw, pitch};
			List<String> lines = Arrays.asList(data[0], data[1], data[2], data[3], data[4]);
			Path file = Paths.get("src/world/save/player_position.txt");
			Files.write(file, lines, Charset.forName("UTF-8"));
			if(KeyboardInput.isDown(KeyboardInput.KEY_L)){
				System.out.println("x = " + data[0]);
				System.out.println("y = " + data[1]);
				System.out.println("z = " + data[2]);
				System.out.println("yaw = " + data[3]);
				System.out.println("pitch = " + data[4]);
			}
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
}
