import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
/**[Town.java]
 * This program creates a town object which contains people, zombies, and plants
 * @authour Valerie Fernandes
 */
class Town{
  private Life[][] map = new Life[25][25];
  private boolean peopleExist;
  private int[][] displayMap = new int[25][25];
  static int people;
  static int plant;
  static int zombie;
  static int upWind;
  static int sideWind;
  static int month;
  MatrixDisplayWithMouse display = new MatrixDisplayWithMouse("Testing 123...", displayMap);
  private ArrayList<Point> clicked = MatrixDisplayWithMouse.clicked;
  
  
  Town(int people, int plant){
    this.people = people;
    this.plant = plant;
    this.zombie = 0;
    this.month = -1;
    int rowCoord;
    int colCoord;
    
    for(int i=0; i<people/2; i++){
      do{
        rowCoord = (int)(Math.random() * 25); // Generate random coordinates in an open spot
        colCoord = (int)(Math.random() * 25);
      }while(this.map[rowCoord][colCoord] != null);
      
      this.map[rowCoord][colCoord] = new Person(rowCoord, colCoord, 20, "male");
    }
    
     for(int i=0; i<Math.ceil(people/2.0); i++){
      do{
        rowCoord = (int)(Math.random() * 25); // Generate random coordinates in an open spot
        colCoord = (int)(Math.random() * 25);
      }while(this.map[rowCoord][colCoord] != null);
      
      this.map[rowCoord][colCoord] = new Person(rowCoord, colCoord, 20, "female");
    }

    for(int i=0; i<plant; i++){ // generates random coordinats for plants
      do{
        rowCoord = (int)(Math.random() * 25); 
        colCoord = (int)(Math.random() * 25);
      }while((this.map[rowCoord][colCoord] != null));
      
      this.map[rowCoord][colCoord] = new Plant(5, 12, rowCoord, colCoord);
    }
    this.peopleExist = true;
  }

    /**runSim
   * This runs the town through the passage of time
   * until all the humas are gone
   */
  
  public void runSim(){ 
    Point newCoord;
    ArrayList<Life> adjacent = new ArrayList<Life>();
    ArrayList<Point> adjacentCoord = new ArrayList<Point>();
    
    while(people > 0){ 
      this.month++;
      upWind = ((int)(Math.random() * 24)) - 24; // generate wind for the month
      sideWind =((int)(Math.random() * 24)) - 24;      
      for(int i=0; i<25; i++){ // loop through each squre of the town grid 
        for(int j=0; j<25; j++){
          
          displayTown();          
          convertClickedHumans();
          
          if(this.map[i][j] != null){
             
            if(this.map[i][j].getHealth() <= 0){ // kill beings who have lost all their health
              if(this.map[i][j] instanceof Person){
                people--;
              }else if(this.map[i][j] instanceof Zombie){
                zombie--;
              }else if(this.map[i][j] instanceof Plant){
                plant--;
              }
              this.map[i][j] = null; 
              
            }else if(this.map[i][j] instanceof Plant){ 
              this.map[i][j].changeHealth();
              ((Plant)this.map[i][j]).setAge(((Plant)this.map[i][j]).getAge() + 1);
              //drop seeds
              if( (((Plant)this.map[i][j]).getAge() % 4 == 0) && (((Plant)this.map[i][j]).getAge() > 0) ){
                if((i+upWind < 25) && (i+upWind > -1) && (j+sideWind < 25) && (j+sideWind > -1)){
                  if(this.map[i+upWind][j+sideWind] == null){
                    plant++;
                    this.map[i+upWind][j+sideWind] = new Plant(-2, 1, i+upWind, j+sideWind); // becomes edible once age is above 0
                  }
                }
              }
                    
              
            }else{ // otherwise it must be a being
              adjacent.clear();
              adjacentCoord.clear(); 
              for(int x=-1; x<2; x++){ // find adjacent squares within range of town
                for(int y=-1; y<2; y++){
                  if((x+i < 25) && (x+i > -1) && (y+j < 25) && (y+j > -1)){ 
                    adjacent.add(this.map[x+i][y+j]);
                    adjacentCoord.add(new Point (x+i, y+j));
                  }
                }
              }
              
              newCoord = ((Being)this.map[i][j]).chooseMove(adjacent, adjacentCoord);

              if(this.map[i][j] instanceof Person){
                progressPregnancy((Person)this.map[i][j], i, j);
                       
                if(this.map[newCoord.getRow()][newCoord.getCol()] != null){ 
                  
                  if(this.map[newCoord.getRow()][newCoord.getCol()] instanceof Person){ // interact with another
                    ((Person)this.map[i][j]).interactWithPerson((Person)this.map[newCoord.getRow()][newCoord.getCol()]);
                    newCoord.setRow(i);
                    newCoord.setCol(j);
                  
                  }else if(this.map[newCoord.getRow()][newCoord.getCol()] instanceof Plant){ // eat a plant
                    ((Person)this.map[i][j]).eatPlant((Plant)this.map[newCoord.getRow()][newCoord.getCol()]);
                  }
                 // chooseMove prioritizes remaining over encountering Zombie       
                }
                ((Person)this.map[i][j]).setAge(((Person)this.map[i][j]).getAge() + 1);
                
              }else if(this.map[newCoord.getRow()][newCoord.getCol()] instanceof Zombie){         
                if(this.map[newCoord.getRow()][newCoord.getCol()] != null){  
                  
                  if(this.map[newCoord.getRow()][newCoord.getCol()] instanceof Person){ // interact with a person
                    
                    if ( ( (Zombie)this.map[i][j]).compareTo( (Person)this.map[newCoord.getRow()][newCoord.getCol()] ) <= 0){
                      this.map[newCoord.getRow()][newCoord.getCol()] = new Zombie(newCoord.getRow(), newCoord.getCol(), this.map[newCoord.getRow()][newCoord.getCol()].getHealth());                      
                      newCoord.setRow(i);
                      newCoord.setCol(j);
                      
                    }else{ // otherwise the person will be ended
                      people--;
                      this.map[i][j].setHealth(this.map[i][j].getHealth() + this.map[newCoord.getRow()][newCoord.getCol()].getHealth());
                    }
                  }else if(this.map[newCoord.getRow()][newCoord.getCol()] instanceof Plant){ // trample plant
                    plant--;
                  }
                  // chooseMove prioritizes remaining over encountering Zombie
                }
              }
              
              this.map[i][j].changeHealth();     
              this.map[newCoord.getRow()][newCoord.getCol()] = this.map[i][j];
              this.map[i][j].setRowCoord(newCoord.getRow());
              this.map[i][j].setColCoord(newCoord.getCol());
           
              if ((newCoord.getRow() != i) || (newCoord.getCol() != j)){
                this.map[i][j] = null;
              }
            }
          }
        }
      }
    }
  }
  
    /**convertClickedHumans
   * This function changes all the humans that
   * have been clicked into zombies
   */ 
  public void convertClickedHumans(){
    for(int k=0; k<clicked.size(); k++){ // find any humans clicked
      
      if((clicked.get(k).getRow() < 25) && (clicked.get(k).getCol() < 25)){
        if(this.map[clicked.get(k).getRow()][clicked.get(k).getCol()] != null){
          if(this.map[clicked.get(k).getRow()][clicked.get(k).getCol()] instanceof Person){
            people--;
            zombie++;
            this.map[clicked.get(k).getRow()][clicked.get(k).getCol()] = new Zombie(clicked.get(k).getRow(), clicked.get(k).getCol(), this.map[clicked.get(k).getRow()][clicked.get(k).getCol()].getHealth());
          }
        }
      }
    }
    clicked.clear();
  }

  /**progressPregnancy
   * This function moves forward a pregnant person's progress
   * by a month and drops the child when ready
   * @param person, Person the person whose pregnancy is being progressed
   * @param row, int row coordinate of the person
   * @param col, int column coordinate of the person
   */ 
  private void progressPregnancy(Person person, int row, int col){
    if (person.getPregnancy() > 0){
       person.setPregnancy(person.getPregnancy() + 1);
   }
   
    if(person.getPregnancy() == 4){
      for(int x=-1; x<2; x++){ // try to find empty adjacent space to drop baby
        for(int y=-1; y<2; y++){
          if((x+row < 25) && (x+row > -1) && (y+col < 25) && (y+col > -1)){ 
            if(this.map[x+row][y+col] == null){
              people++;
              this.map[x+row][y+col] = new Person(x+row, y+col, 20,person.getBabyGender());
              break;
             }
           }
        }
        person.setPregnancy(0); // reset their pregnancy abilities
        person.setBabyGender("");
      }
    }
  }
 
    /**displayTown
   * This calls for the displpay to update and calls
   * for the new display to be shown
   */ 
  private void displayTown(){
    updateDisplay();
    this.display.refresh();    //display the new town     
    try{ Thread.sleep(50); }catch(Exception e) {};  
  }
  
    /**updateDisplay
   * This function updates the display based on
   * the current life in the town
   */ 
  private void updateDisplay(){ 
    
    for(int i = 0; i<25;i++){
      for(int j = 0; j<25 ;j++){ 
        displayMap[i][j] = 0;
        
        if(this.map[i][j] instanceof Zombie){
          this.displayMap[i][j] = 3;
          
        }else if(this.map[i][j] instanceof Person){
          this.displayMap[i][j] = 1;
          
        }else if(this.map[i][j] instanceof Plant){
          this.displayMap[i][j] = 2;
        } 
      }
    }
  }
}