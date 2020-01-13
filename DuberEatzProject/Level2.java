import java.util.Scanner;
import java.io.File;
/** Level2.java
  * version 1.0
  * author Valerie Fernandes
  * date: october 24, 2019
  * description: finding Duber's shortest path too visit one delivery location
  */
class Level2{
  public static int rows;  //Make rows and columns of Duber's map into class variables
  public static int columns;
  
  public static void main(String[] args)throws Exception{
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome Duber!, what is your file caled?");
    String fileName = input.nextLine();    
    File delivery = new File(fileName);
    Scanner readFile = new Scanner(delivery);
    rows = readFile.nextInt();
    columns = readFile.nextInt();
    
    int startRow = 0;
    int startCol = 0;
    int endRow = 0;
    int endCol = 0;
    int[][] map = new int[rows][columns];
    String line;
    line = readFile.nextLine();
    
    for(int i=0; i<rows; i++){ //read data and fill map as integer array
      line = readFile.nextLine();
      for(int j=0; j<columns; j++){
        if(line.charAt(j) == '#'){
          map[i][j] = -1;
          
        }else if(line.charAt(j) == ' '){
          map[i][j] = -2; 
          
        }else if(line.charAt(j) == '1'){
          map[i][j] = -3;  
          endRow = i;
          endCol = j;          
        }else if(line.charAt(j) == 'S'){
          map[i][j] = 0;  
          startRow = i;
          startCol = j;
        }
      }
    }
    map = findOne(map, startRow, startCol);
    int time = map[endRow][endCol];
    map = formatMap(map, endRow, endCol, time);
    printMap(map, endRow, endCol);
    System.out.println(time);
  }
  
  public static int[][] findOne(int[][] map, int rowPos, int colPos){      
    if((map[rowPos + 1][colPos] < -1) || (map[rowPos + 1][colPos] >= map[rowPos][colPos])){ //Go as far to the down as possible  (check if its not a wall   
      map[rowPos + 1][colPos] = map[rowPos][colPos] + 1;                                    // or if you have gotten there in less steps before 
      map = findOne(map, rowPos + 1, colPos);
    }
    if((map[rowPos - 1][colPos] < -1) || (map[rowPos - 1][colPos] >= map[rowPos][colPos])){ //Go as far to the up as possible
      map[rowPos - 1][colPos] = map[rowPos][colPos] + 1;
      map = findOne(map, rowPos - 1, colPos);
    }
    if((map[rowPos][colPos + 1] < -1) || (map[rowPos][colPos + 1] >= map[rowPos][colPos])){ //Go as far right as possible
      map[rowPos][colPos + 1] = map[rowPos][colPos] + 1;
      map = findOne(map, rowPos, colPos + 1);
    }
    if((map[rowPos][colPos - 1] < -1) || (map[rowPos][colPos - 1] > map[rowPos][colPos])){ //Go as far left as possible
      map[rowPos][colPos - 1] = map[rowPos][colPos] + 1;
      map = findOne(map, rowPos, colPos - 1);
    }   
    return map; //Map shows how many steps to get to each location from the start
  }  
  
  public static int[][] formatMap(int[][] map, int endRow, int endCol,int steps){
    for(int i=0; i<steps; i++){ //mark path with -3 by chacking where the path continues (one step away) mark as path and keep following
      if(map[endRow][endCol] == map[endRow + 1][endCol] + 1){ // move down
        endRow++;
        map[endRow - 1][endCol] = -3;
      }else if(map[endRow][endCol] == map[endRow - 1][endCol] + 1){ // move up
        endRow--;
        map[endRow + 1][endCol] = -3;
      }else if(map[endRow][endCol] == map[endRow][endCol + 1] + 1){ // move right
        endCol++;
        map[endRow][endCol - 1] = -3;
      }else if(map[endRow][endCol] == map[endRow][endCol - 1] + 1){ // move left
        endCol--;
        map[endRow][endCol + 1] = -3;
      }
      
    }
    return map;
  }
  
  public static void printMap(int[][] map, int endRow, int endCol){
    for(int i=0; i<rows; i++){   //print maze
      for(int j=0; j<columns ; j++){
        if((i == endRow) && (j == endCol)){
          System.out.print('X');
        }else if (map[i][j] == -3){
          System.out.print('x');
        }else if (map[i][j] == -1){
          System.out.print('#');  
        }else if (map[i][j] == 0){
          System.out.print('S'); 
        }else{
          System.out.print(' ');
        }
      }
      System.out.println("");
    }        
  }
}
  

      
      
