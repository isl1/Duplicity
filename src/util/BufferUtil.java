package util;

import static org.lwjgl.system.jemalloc.JEmalloc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;

import org.lwjgl.BufferUtils;

public class BufferUtil {
	
	public static FloatBuffer toFloatBuffer(float[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
	
	public static ByteBuffer createByteBuffer(long size) {
		return je_malloc(size);
	}
	
	public static ByteBuffer fileToByteBuffer(String path) throws IOException {
		ByteBuffer buffer;
		
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		FileChannel fc = fis.getChannel();
		
		buffer = createByteBuffer((int) fc.size() + 1);
		
		while(fc.read(buffer) != -1);
		
		fis.close();
		fc.close();
		
		buffer.flip();
		return buffer;
	}
	
}