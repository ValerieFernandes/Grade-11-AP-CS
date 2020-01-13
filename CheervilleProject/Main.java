import java.util.Scanner;
/**[Main.java]
 * This program intiates the creation of a town
 * @authour Valerie Fernandes
 */

class Main{
  public static void main(String[] args){  
    Town cheerville = initializeSim();
    cheerville.runSim();
  }
  
  /**intializeSim
 * This method intiates creates a town based on the specfics inputed by the user
 * @return t, a town object containing what the user requested
 */
  public static Town initializeSim(){  
    Scanner input = new Scanner(System.in);
    int people=20;
    int plant=10;
    String modifyPl = "N";
    String modifyPe = "N";
    
    System.out.println("Welcome to Cheerville, Let's set up your town");
    System.out.println("Would you like to change amount of plants, y or n");
    modifyPl = input.nextLine();
    
    if(modifyPl.equalsIgnoreCase("y")){
      System.out.println("How many plants are there?");
      plant = input.nextInt();
      input.nextLine();
    }

    System.out.println("Would you like to change amount of people, y or n");
    modifyPe = input.nextLine();
      
    if(modifyPe.equalsIgnoreCase("y")){
      System.out.println("How many people are there?");
      people = input.nextInt();
    }
      
    Town t = new Town(people, plant);
    return t;
  }
}