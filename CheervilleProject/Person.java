import java.util.ArrayList;
/**[Person.java]
 * This program creates a person object
 * @authour Valerie Fernandes
 */
class Person extends Being{
  private int age;
  private String gender;
  private int pregnancy;
  private String babyGender;
  
  Person(int rowCoord, int colCoord, int health, String gender){
    super(rowCoord, colCoord, health);
    this.age = 0;
    this.gender = gender;
    this.pregnancy = 0;
    this.babyGender = "";
  }
  
  // setters and getters
  public String getGender(){
    return this.gender;
  }
  
  public int getAge(){
    return this.age;
  }
  public void setAge(int years){
    this.age += years;
  }
  
  public String getBabyGender(){
    return this.babyGender;
  }
  
  public void setBabyGender(String gender){
    this.babyGender = gender;
  }
  
  public int getPregnancy(){
    return this.pregnancy;
  }
  
  public void setPregnancy(int month){
    this.pregnancy = month;
  }
  
  /**chooseMove
   * This method allows the human to look around them
   * and then move towards 1)a plant, 2)another human 3) square without a zombie
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
      }else if (view.get(i) instanceof Plant){
        priority[i] = 3;
      }else if ((view.get(i) instanceof Person) && (view.get(i) != this)){
        priority[i] = 2;
      }else if ( view.get(i) instanceof Zombie){
        priority[i] = -1;
      }
    }
    for(int i=0; i<priority.length; i++){
      if(priority[i] > max){
        max = priority[i];
        maxCoord.setRow((coordinates.get(i)).getRow());
        maxCoord.setCol((coordinates.get(i)).getCol());
      }
    }
    return maxCoord;
  }

    /**eatPlant
   * This method allows the human to take the nutrition
   * of a grown plant
   * @param Plant food, the plant which is being eaten
   */ 
  public void eatPlant(Plant food){
    if(food.getHealth() > 0){
      Town.plant--;
      this.setHealth(this.getHealth() + food.getHealth());
      food.setHealth(0);
    }
  }

  /**interactwithPerson
   * This method allows the human to decide
   * if it will create a child with another person
   * @param other, Person the person with which it is interacting
   */  
  public void interactWithPerson(Person other){
    if ((this.getAge() > 18) && (other.getAge() > 18)){
      if(((this.gender.equals("male")) && (other.getGender().equals("female"))) || ((this.gender.equals("female")) && (other.getGender().equals("male")))){
        if((other.getPregnancy() == 0) && (this.pregnancy == 0)){
          createChild(other);
        }
      }
    }
          
  }

    /**chooseMove
   * This method allows the human to produce
   * a pregnancy with another person
   * @param other, Person the person with which the child is being created
   */  
  private void createChild(Person other){
    if(other.getGender().equals("female")){
      other.setPregnancy(1); // first month of pregnancy
      other.setBabyGender(this.gender);
    }else{
      this.pregnancy = 1;
      this.babyGender = this.gender;
    }
  }

    /**changeHealth
   * This method allows the human to loose
   * health based on its age
   */  
  public void changeHealth(){
    this.setHealth(this.getHealth()- (this.age/20));
  }
  
}