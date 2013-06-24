import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class SkySphere {
	public static void render() {
		GL11.glTranslatef(150.0f - MainWindow.camera.x * - 1, 150.0f + MainWindow.camera.y, 150.0f - MainWindow.camera.z * - 1);
		
		GL11.glColor3f(0.5f, 1.0f, 1.0f);
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glStencilMask(0);
		
		glPushMatrix();
        Sphere s = new Sphere();
        s.draw(300f, 32, 32);
        glPopMatrix();
        
        GL11.glStencilMask(1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glTranslatef(-150.0f + MainWindow.camera.x * - 1, -150.0f - MainWindow.camera.y, -150.0f + MainWindow.camera.z * - 1);
	}
}
