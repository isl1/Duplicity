package entities;

import org.lwjgl.util.vector.Vector3f;

import graphics.Loader;
import models.OBJLoader;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public class Lamp {
	
	Loader loader = new Loader();
	
	RawModel lampModel = OBJLoader.loadObjModel("lamp", loader);
	ModelTexture lampTexture = new ModelTexture(loader.loadTexture("resources/textures/marble.png"));
	TexturedModel lampTexModel = new TexturedModel(lampModel, lampTexture);

	public Entity Lamp(Vector3f position, float scale){
		lampTexModel.getTexture();
		lampTexture.setShineDamper(10);
		lampTexture.setReflectivity(5);
		
		Entity lamp = new Entity(lampTexModel, position, 0, 0, 0, scale);
		return lamp;
	}
	
	

}
