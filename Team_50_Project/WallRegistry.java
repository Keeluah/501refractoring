import java.util.ArrayList;
import java.util.Collection;

/**
 * This class contains methods to operate on a collection of walls.
 * @author Caelan Hilferty
 * @author Jacob Goodwin
 * @version 0.01
 * @see Wall
 * @since Mar 16, 2017
 */
public class WallRegistry {
    private Collection<Wall> registry;

	/**
	* Constructs a new WallRegistry.
	*/
    public WallRegistry() {
        registry = new ArrayList<>();
    }

	/**
	* Constructs a new WallRegistry using a collection of walls.
	* @param wallList the Collection of walls that will be copied
	*                 to the new WallRegistry
	*/
    public WallRegistry(Collection<Wall> wallList) {
        registry = new ArrayList<Wall>(wallList);
    }

	/**
	* Constructs a new WallRegistry using another WallRegistry.
	* @param reg the WallRegistry that the new WallRegistry will
	*            be a copy of
	*/
    public WallRegistry(WallRegistry reg) {
        this(reg.getRegistry());
    }

	/**
	* Adds a new wall to the registry of walls.
	* @param wall the wall that is added to the registry
	*/
    public void addWall(Wall wall) {
        registry.add(wall);
    }

	/**
	* Returns a copy of the registry.
	* @return a copy of the registry
	*/
    public Collection<Wall> getRegistry() {
        return new ArrayList<Wall>(registry);
    }
}
