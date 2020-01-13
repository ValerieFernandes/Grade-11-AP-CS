import java.util.ArrayList;
/**[Being.java]
 * This program is a used a superclass to create different types of
 * of being objects
 * @authour Valerie Fernandes
 */

abstract class Being extends Life implements Comparable<Being>{

   /**compareTo
   * Compares the health of two beings
   * @param other, another Being 
   * @return int, has value of -1, 0, 1 to represent if the health is
   * larger, equal, or smaller repectively
   */  
  Being(int health, int rowCoord, int colCoord){
    super(health, rowCoord, colCoord);
  }
  
  abstract Point chooseMove(ArrayList<Life> view, ArrayList<Point> coordinates);
  
    /**compareTo
   * Compares the health of two beings
   * @param other, another Being 
   * @return int, has value of -1, 0, 1 to represent if the health is
   * larger, equal, or smaller repectively
   */  
  public int compareTo(Being other){
    if(this.getHealth() > other.getHealth()){
      return 1;
    }else if(this.getHealth() < other.getHealth()){
      return -1;
    }else{
      return 0;
    }
  }
}