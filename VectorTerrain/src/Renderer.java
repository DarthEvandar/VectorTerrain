

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer {
	static int size = 8;
	static double[][] grid = new GenTerrain(new double[size+1][size+1]).getGrid();
	static int n=1;
	static int d = size/2;
	static int iters = size/2;
	static double var = 1.0;
	static double range = 2.0;
	static double[][] grid2 = new GenTerrain(new double[size+1][size+1]).getGrid();
	static double[][] gridConnector = new double[2][9];
	static int a = 0;
	static float rotation = 0;
    
    /** time at last frame */
    long lastFrame;
     
    /** frames per second */
    int fps;
    /** last fps time */
    static long lastFPS;
	 public static void main(String[] args)
     {
		for(int i=0;i<=8;i++){
			gridConnector[0][i]=grid[size][i];
			gridConnector[1][i]=grid2[0][i];
		}	
      Renderer r = new Renderer();
      lastFPS = getTime();
      r.Start();
     }
	 public void updateFPS() {
		    if (getTime() - lastFPS > 1000) {
		        Display.setTitle("FPS: " + fps); 
		        fps = 0; //reset the FPS counter
		        lastFPS += 1000; //add one second
		    }
		    fps++;
		}
	 public void Start(){
		 try {
		 createWindow();
		 InitGL();
		     Run();
		 } catch (Exception e) {
		 
		 e.printStackTrace();
		 }
		 }
	 DisplayMode displayMode;

	private void createWindow() throws Exception {
	         Display.setFullscreen(false);
	         DisplayMode d[] = Display.getAvailableDisplayModes();
	         for (int i = 0; i < d.length; i++) {
	             if (d[i].getWidth() == 640
	                 && d[i].getHeight() == 480
	                 && d[i].getBitsPerPixel() == 32) {
	                 displayMode = d[i];
	                 break;
	             }
	         }
	         Display.setDisplayMode(displayMode);
	         Display.setTitle("LWJGL Voxel engine");
	         Display.create();
	     }
	    private void InitGL() {
	    	GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
	        GL11.glClearDepth(1.0); 
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDepthFunc(GL11.GL_LEQUAL); 

	        GL11.glMatrixMode(GL11.GL_PROJECTION); 
	        GL11.glLoadIdentity();

	         
	        GLU.gluPerspective(
	          45.0f,
	          (float)displayMode.getWidth()/(float)displayMode.getHeight(),
	          0.1f,
	          100.0f);

	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	        
	    }
	    public static long getTime() {
	        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	    }
	    public int getDelta() {
	        long time = getTime();
	        int delta = (int) (time - lastFrame);
	        lastFrame = time;
	             
	        return delta;
	    }
	    private void Run() {
	    	 getDelta();
	    	 lastFPS = getTime();
	    	 
	    	 while (!Display.isCloseRequested()) {
	    	 int delta = getDelta();	             
	         
	    	 try{
	    	 //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	    	 update(delta);
		     Render();
	    	 Display.update();
	    	 Display.sync(60);
	    	 }catch(Exception e){
	    	 
	    	 }
	    	 }
	    	 Display.destroy();
	    	 }
	    public void update(int delta) {
	        // rotate quad
	        rotation += 1f;
	        //System.out.println(rotation);
	        /*if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
	        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;
	         
	        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y -= 0.35f * delta;
	        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y += 0.35f * delta;*/
	         
	        // keep quad on the screen
	         
	        updateFPS(); // update FPS Counter
	    }
	    private void Render() {
	    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	    	GL11.glLoadIdentity();
	    	GL11.glTranslatef(-10f, 0.0f, -20f);
	    	//System.out.println(rotation);
	    	GL11.glRotatef(300, 1f, .3f, .5f);
	    	GL11.glColor3f(0.5f, 0.5f, 1.0f);
	       /* GL11.glBegin(GL11.GL_LINES);
	        GL11.glVertex3d(1,1,1);
	        GL11.glVertex3d(0,0,0);
	        
	        GL11.glEnd();*/
	        for(int i=0;i<=size;i++){
	        	for(int j=0;j<=size;j++){
	        		/*if(i==8){
	        			GL11.glColor3f(1f, 0f, 0f);
	        		}*/
	        		try{
	        			//DownRight
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i+1,j+1,grid[i+1][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//DownLeft
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i-1,j+1,grid[i-1][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Down
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i,j+1,grid[i][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Left
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i-1,j,grid[i-1][j]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//LeftUp
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i-1,j-1,grid[i-1][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//RightUp
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i+1,j-1,grid[i+1][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Up
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i,j-1,grid[i][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Right
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i,j,grid[i][j]);
	        			GL11.glVertex3d(i+1,j,grid[i+1][j]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        	}
	        } 
	        //GL11.glTranslatef(0f,0f,0f);
	       // GL11.glColor3f(1f, 0f, 0f);
	        for(int i=0;i<2;i++){
	        	for(int j=0;j<=8;j++){
	        		if(i==8){
	        			
	        		}
	        		try{
	        			//DownRight
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i+1+8,j+1,gridConnector[i+1][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//DownLeft
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i-1+8,j+1,gridConnector[i-1][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Down
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i+8,j+1,gridConnector[i][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Left
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i-1+8,j,gridConnector[i-1][j]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//LeftUp
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i-1+8,j-1,gridConnector[i-1][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//RightUp
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i+1+8,j-1,gridConnector[i+1][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Up
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i+8,j-1,gridConnector[i][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Right
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+8,j,gridConnector[i][j]);
	        			GL11.glVertex3d(i+8+1,j,gridConnector[i+1][j]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        	}
	        }
	       // GL11.glColor3f(0.5f, 0.5f, 1.0f);
	        for(int i=0;i<=size;i++){
	        	for(int j=0;j<=size;j++){
	        		/*if(i==8){
	        			GL11.glColor3f(1f, 0f, 0f);
	        		}*/
	        		try{
	        			//DownRight
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i+1+9,j+1,grid2[i+1][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//DownLeft
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i-1+9,j+1,grid2[i-1][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Down
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i+9,j+1,grid2[i][j+1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Left
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i-1+9,j,grid2[i-1][j]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//LeftUp
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i-1+9,j-1,grid2[i-1][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//RightUp
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i+1+9,j-1,grid2[i+1][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Up
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i+9,j-1,grid2[i][j-1]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        			//Right
	        		try{
	        			GL11.glBegin(GL11.GL_LINES);
	        			GL11.glVertex3d(i+9,j,grid2[i][j]);
	        			GL11.glVertex3d(i+9+1,j,grid2[i+1][j]);
	        			GL11.glEnd();
	        		}catch(ArrayIndexOutOfBoundsException e){}
	        	}
	        }
   	 }
}
