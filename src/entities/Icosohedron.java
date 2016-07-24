package entities;

import org.lwjgl.util.vector.Vector3f;

import graphics.Loader;
import models.OBJLoader;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public class Icosohedron {
	
Loader loader = new Loader();
	
	RawModel icosoModel = OBJLoader.loadObjModel("icosohedron", loader);
	ModelTexture icosoTexture = new ModelTexture(loader.loadTexture("resources/textures/tron.png"));
	TexturedModel texturedIcosoModel = new TexturedModel(icosoModel, icosoTexture);


	public Entity Icosohedron(Vector3f position, float scale){
		texturedIcosoModel.getTexture();
		icosoTexture.setShineDamper(10);
		icosoTexture.setReflectivity(1);
		
		Entity icosohedron = new Entity(texturedIcosoModel, position, 0, 0, 0, scale);
		return icosohedron;
	}

}
