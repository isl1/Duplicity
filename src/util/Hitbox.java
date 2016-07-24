package util;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import toxi.geom.AABB;
import toxi.geom.Vec3D;

public class Hitbox {
	
	public AABB Hitbox(Entity entity){
		AABB hitbox = new AABB(new Vec3D(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z), entity.getScale());
		return hitbox;
	}
	
	public AABB Hitbox(List<Entity> entities){
		AABB hitbox = null;
		for(int i = 0; i < entities.size(); i++){
			hitbox = new AABB(new Vec3D(entities.get(i).getPosition().x, entities.get(i).getPosition().y, entities.get(i).getPosition().z), entities.get(i).getScale());
		}
		return hitbox;
	}

}
