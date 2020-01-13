import java.util.ArrayList;
/**[Zombie.java]
 * This class allows to creates a Zombie object
 * @authour Valerie Fernandes
 */
class Zombie extends Being{
  Zombie(int xCoord, int yCoord, int health){
    super(xCoord, yCoord, health);
  }
  
  /**chooseMove
   * This zombie allows the being to look around them
   * and then move towards 1)a person, 2)a plant 3) empty square
   * @param view, ArrayList of life that you can see
   * @param coordinates, ArrayList of the adjacent object's coordinates 
   * @return Point, coordinates of optimal move
   */  
  public Point chooseMove(ArrayList<Life> view, ArrayList<Point> coordinates){
    int[] priority = new int[view.size()];
    int max = -2;
    Point maxCoord = new Point(0, 0);
    
    for(int i=0; i<priority.length; i++){
      if (view.get(i) == null){
        priority[i] = 1;
      }else if (view .get(i) instanceof Plant){
        priority[i] = 2;
      }else if (view.get(i) instanceof Person){
        priority[i] = 3;
      }else if (view .get(i) instanceof Zombie){
        priority[i] = -1;
      }
    }
      
    for(int i=0; i<priority.length; i++){
      if(priority[i] > max){
        max = priority[i];
        maxCoord = coordinates.get(i);
      }
    }
    return maxCoord;
  }
  
  /**changeHealth
   * This method reduces the zombies health by one
   */   
  public void changeHealth(){
    this.setHealth(this.getHealth() - 1);
  }

}
  
