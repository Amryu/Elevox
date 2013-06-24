
public class Block {
	// Definierte Blöcke
	public static Block[] blockList = new Block[256];
	
	public static final Block AIR = new Block(0, EnumMaterial.gas);
	public static final Block GRASS = new Block(1, EnumMaterial.dirt);
	
	// Blockeigenschaften
	private final int id;
	private final EnumMaterial material;
	
	private String name = "unknown";
	
	private Block(int id, EnumMaterial material) {
		if(blockList[id] != null) {
			throw new IllegalArgumentException("Die ID "+id+" ist bereits belegt!");
		}
		
		this.id = id;
		this.material = material;
		
		blockList[id] = this;
	}
	
	private Block setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public EnumMaterial getMaterial() {
		return this.material;
	}
}
