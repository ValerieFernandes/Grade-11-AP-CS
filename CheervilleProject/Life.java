abstract class Life{
  private int health;
  private int rowCoord;
  private int colCoord;
  
  Life(int health, int rowCoord, int colCoord){
    this.health = health;
    this.rowCoord = rowCoord;
    this.colCoord = colCoord;
  }
  
  // setters and getters
  public void setHealth(int health){
    this.health = health;
  }
  
  public int getHealth(){
    return this.health;
  }
  
  public void setRowCoord(int rowCoord){
    this.rowCoord = rowCoord;
  }
  
  public int getRowCoord(){
    return this.rowCoord;
  }
  
  public void setColCoord(int colCoord){
    this.colCoord = colCoord;
  }
  
  public int getColCoord(){
    return this.colCoord;
  }
  
  // methods
  abstract void changeHealth();
}