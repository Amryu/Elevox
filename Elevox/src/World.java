import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class World {
	private String name;
	
	private long seed = 0L;
	
	public ChunkMap chunkMap;
	
	private Point playerPos = new Point(0,0);

	public World(String name) {
		this(name, new Random().nextLong());
	}
	
	public World(String name, long seed) {
		this.name = name;
		this.seed = seed;
	}

	public static World load(String name) {
		return null;
	}
	
	public static void save(World world) {
		ChunkMap.saveAndClose();
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./worlds/"+world.name+"/world.info")));
			
			bw.append("seed="+world.seed+"\n");
			bw.append("pos="+world.playerPos.x+"|"+world.playerPos.y+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
