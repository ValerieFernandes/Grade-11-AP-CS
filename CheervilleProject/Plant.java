/**[Plant.java]
 * 
 * @authour Valerie Fernandes
 */
class Plant extends Life{
  private int age;
  Plant(int age, int health, int rowCoord, int colCoord){
    super(health, rowCoord, colCoord);
    this.age = age;
  }
  // setters and getters
  public int getAge(){
    return this.age;
  }
  public void setAge(int age){
    this.age = age;
  }
  /**changeHealth
   * This method changes the plants health based
   * on its age
   */   
  public void changeHealth(){
    if(this.age < 15){
      this.setHealth(this.getHealth() + 2);
    }else{
      this.setHealth(this.getHealth() - 1);
    }
  }
  
}
  