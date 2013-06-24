import org.lwjgl.opengl.GL11;
 
//First Person Camera Controller
public class FPCameraController {
	public float yaw = 0.0F;
	public float pitch = 0.0F;
	public float x = 0.0F;
	public float y = 130.0F;
	public float z = 0.0F;
	
    public FPCameraController() {
        
    }
    
    //increment the camera's current yaw rotation
    public void yaw(float amount) {
        //increment the yaw by the amount param
    	yaw += amount;
    }
     
    //increment the camera's current yaw rotation
    public void pitch(float amount) {
        //increment the pitch by the amount param
    	pitch -= amount;
    }
    
    //moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance) {
    	x += distance * (float)Math.sin(Math.toRadians(yaw+180));
    	z -= distance * (float)Math.cos(Math.toRadians(yaw+180));
    }
     
    //moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance) {
    	x -= distance * (float)Math.sin(Math.toRadians(yaw+180));
    	z += distance * (float)Math.cos(Math.toRadians(yaw+180));
    }
    
    public void up(float distance) {
    	y += distance;
    }
    
    public void down(float distance) {
    	y -= distance;
    }
     
    //strafes the camera left relitive to its current rotation (yaw)
    public void strafeLeft(float distance) {
    	x += distance * (float)Math.sin(Math.toRadians(yaw+90));
    	z -= distance * (float)Math.cos(Math.toRadians(yaw+90));
    }
     
    //strafes the camera right relitive to its current rotation (yaw)
    public void strafeRight(float distance) {
    	x += distance * (float)Math.sin(Math.toRadians(yaw-90));
    	z -= distance * (float)Math.cos(Math.toRadians(yaw-90));
    }
    
    public void lookThrough() {
    	if(yaw > 360) yaw -= 360;
    	if(yaw < -360) yaw += 360;
    	if(pitch > 90) pitch = 90;
    	if(pitch < -90) pitch = -90;
        //roatate the pitch around the X axis
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        GL11.glRotatef(yaw+180, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        GL11.glTranslatef(x*-1, y*-1, z*-1);
    }
}