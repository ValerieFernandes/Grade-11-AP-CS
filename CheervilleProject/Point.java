/**[Point.java]
 * This program creates a point object which contains two coordinates
 * @authour Valerie Fernandes
 */
class Point{
  private int row;
  private int col;
  
  Point(int row, int col){
    this.row = row;
    this.col = col;
  }
  
  // setters and getters
  public void setRow(int row){
    this.row = row;
  }
  public int getRow(){
    return this.row;
  }
  public void setCol(int col){
    this.col = col;
  }
  public int getCol(){
    return this.col;
  }
}