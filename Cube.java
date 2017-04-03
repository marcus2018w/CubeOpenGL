
import java.awt.Dimension;


import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;

import java.util.Scanner;

public class Cube extends GLJPanel implements GLEventListener {

	//private float rotateX, rotateY, rotateZ;
	private static float viewX,viewY;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter perspective coordinates (x,y,z)");
		viewX = s.nextFloat();
		viewY = s.nextFloat();
		
		JFrame window = new JFrame("Viewing Scene");
		GLCapabilities caps = new GLCapabilities(null);
		Cube panel = new Cube(caps);
		window.setContentPane(panel);
		window.pack();
		window.setLocation(50, 50);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		panel.requestFocusInWindow();
	}

	// rotation amounts about axes, controlled by keyboard

	public Cube(GLCapabilities capabilities) {
		super(capabilities);
		setPreferredSize(new Dimension(500, 500));
		addGLEventListener(this);
		
		
	}

	

	private void square(GL2 gl, float r, float g, float b) {
		gl.glColor3f(r, g, b); // The color for the square.
		gl.glTranslatef(0, 0, 0.5f);
		gl.glNormal3f(0, 0, 1); // Normal vector to square (this is actually the
								// default).
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex2f(-0.5f, -0.5f); // Draw the square (before the
		gl.glVertex2f(0.5f, -0.5f); // the translation is applied)
		gl.glVertex2f(0.5f, 0.5f);
		gl.glVertex2f(-0.5f, 0.5f);
		gl.glEnd();
	}

	private void cube(GL2 gl) {

		gl.glPushMatrix();
		// gl.glTranslatef(.5f, 0f, 0);
		square(gl, 1, 0, 0); // front face is red
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(180, 0, 1, 0); // rotate square to back face
		square(gl, 0, 1, 1); // back face is cyan
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90, 0, 1, 0); // rotate square to left face
		square(gl, 0, 1, 0); // left face is green
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90, 0, 1, 0); // rotate square to right face
		square(gl, 1, 0, 1); // right face is magenta
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90, 1, 0, 0); // rotate square to top face
		square(gl, 0, 0, 1); // top face is blue
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0); // rotate square to bottom face
		square(gl, 1, 1, 0); // bottom face is yellow
		gl.glPopMatrix();

		gl.glPushMatrix();

	}

	public void display(GLAutoDrawable drawable) {
		// called when the panel needs to be drawn
//poop/ 
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl);
		glu.gluLookAt(1, 0, 1, 0 , 1,0 , 0, 1, 0);
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_PROJECTION); // Set up the projection.
		gl.glLoadIdentity();
		gl.glOrtho(-1, 1, -1, 1, -2, 2);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		float view = .03965f *(viewX)-.43784f; 
		glu.gluLookAt(view, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		

		cube(gl);

	}

	public void init(GLAutoDrawable drawable) {
		// called when the panel is created
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.8F, 0.8F, 0.8F, 1.0F);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
	}

	public void dispose(GLAutoDrawable drawable) {
		// called when the panel is being disposed

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// called when user resizes the window
		final GL2 gl = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl);

		if (height <= 0)
			height = 1;

		final float h = (float) width / (float) height;

		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 20.0);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}
