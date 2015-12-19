/**
 * Interface used to create renderable objects
 * @author Sharhar
 */
package engine.graphics;

public interface Renderable {
	/**
	 * This function renders the object using normally
	 */
	public void render();
	
	/**
	 * This function renders the object using the MasterRenderer
	 */
	public void masterRender();
}
