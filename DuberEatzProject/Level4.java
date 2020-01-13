import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
/** Level4.java
  * version 1.0
  * author Valerie Fernandes
  * date: november 2, 2019
  * description: outputs Duber's shortest path to ppm file
  */
class Level4{
  public static int rows;
  public static int columns;
  public static int destCount = 0;
  public static int startRow = 0;
  public static int startCol = 0;
  public static int bestTime = -1;
  
  public static void main(String[] args)throws Exception{
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome Duber!, what is your file called?");
    String fileName = input.nextLine();
    
    File delivery = new File(fileName);
    Scanner readFile = new Scanner(delivery);
    rows = readFile.nextInt();
    columns = readFile.nextInt();
    char[][] readMap = new char[rows][columns];
    char[][] bestMap = new char[rows][columns];
    int[][] stepMap = new int[rows][columns];
    int time = 0;
    String line;
    line = readFile.nextLine();
   
    for(int i=0; i<rows; i++){ //read data into map array
      line = readFile.nextLine();
      for(int j=0; j<columns; j++){       
        if(line.charAt(j) == 'S'){ 
          startRow = i; //Mark starting position
          startCol = j;
        }else if((line.charAt(j) != '#') && (line.charAt(j) != ' ')){
          destCount++; //Count destinations
        }   
        readMap[i][j] = line.charAt(j);
      }
    }
    int [][] destinations = new int[destCount][2];
    int[] path = new int[destCount]; //possible path
    for(int i=0; i<destCount; i++){
      path[i] = i;
    }
    destinations = findDestinationPositions(readMap); // Find positions of the destinations  
    stepMap = charToInt(readMap); //Convert to integer map in order to count steps
    bestMap = findBest(stepMap, 0, destinations, path, readMap, bestMap);
    System.out.print("Minimum amount of steps: ");
    System.out.println(bestTime);
    for(int i=0; i<rows; i++){ //make a map duplicate
      for(int j=0; j<columns; j++){
        System.out.print(bestMap[i][j]);
      }
      System.out.println("");
    } 
    makePPM(bestMap, rows, columns, fileName);
  }

  public static char[][] findBest(int[][] map,int position, int[][] destinations, int[] path, char[][] readMap, char[][] bestMap){ 
    int temp;
    int time;
    int steps;
    char[][] pathMap = new char[rows][columns];
    if(position == destCount - 1){//possible path is found
      for(int i=0; i<rows; i++){ //make a map duplicate
        for(int j=0; j<columns; j++){
          pathMap[i][j] = readMap[i][j];
        }
      }      
      time = 0;
      map = findSteps(map, startRow, startCol);
      time += map[destinations[path[0]][0]][destinations[path[0]][1]];
      pathMap = addPath(map, destinations[path[0]][0], destinations[path[0]][1], time, pathMap); //add shortest path to map
      pathMap[destinations[path[0]][0]][destinations[path[0]][1]] = 'X';
        
      for(int i=0; i<destCount - 1; i++){
        map = charToInt(readMap); //reset the step map
        map[destinations[path[i]][0]][destinations[path[i]][1]] = 0; //change current destination to position 0
        map[startRow][startCol] = -2;
        map = findSteps(map, destinations[path[i]][0], destinations[path[i]][1] );
        steps = map[destinations[path[i+1]][0]][destinations[path[i+1]][1]];
        time += steps;
        pathMap = addPath(map, destinations[path[i+1]][0], destinations[path[i+1]][1], steps, pathMap); //add shortest path to map
        pathMap[destinations[path[i+1]][0]][destinations[path[i+1]][1]] = 'X';
      }
      if((bestTime < 0) || (time < bestTime)){
        bestTime = time;
        for(int i=0; i<rows; i++){ //make best path the sharter one
          for(int j=0; j<columns; j++){
            bestMap[i][j] = pathMap[i][j];
          }
        } 
      } 
    }
    
    for(int i=position; i<destCount; i++){
      temp = path[position];//swap with next
      path[position] = path[i];
      path[i] = temp;

      findBest(map, position + 1, destinations, path, readMap, bestMap);//find all permutations once swap occured
      
      temp = path[position];//swap back
      path[position] = path[i];
      path[i] = temp;
    }
    return bestMap;
  }
   
  
  public static int[][] findSteps(int[][] map, int rowPos, int colPos){  

    if((map[rowPos + 1][colPos] < -1) || (map[rowPos + 1][colPos] >= map[rowPos][colPos])){ //Go as far to the down as possible  (check if its not a wall   
      map[rowPos + 1][colPos] = map[rowPos][colPos] + 1;                                    // or if you have gotten there in less steps before)
      map = findSteps(map, rowPos + 1, colPos);
    }
    if((map[rowPos - 1][colPos] < -1) || (map[rowPos - 1][colPos] >= map[rowPos][colPos])){ //Go as far to the up as possible
      map[rowPos - 1][colPos] = map[rowPos][colPos] + 1;
      map = findSteps(map, rowPos - 1, colPos);
    }
    if((map[rowPos][colPos + 1] < -1) || (map[rowPos][colPos + 1] >= map[rowPos][colPos])){ //Go as far right as possible
      map[rowPos][colPos + 1] = map[rowPos][colPos] + 1;
      map = findSteps(map, rowPos, colPos + 1);
    }
    if((map[rowPos][colPos - 1] < -1) || (map[rowPos][colPos - 1] > map[rowPos][colPos])){ //Go as far left as possible
      map[rowPos][colPos - 1] = map[rowPos][colPos] + 1;
      map = findSteps(map, rowPos, colPos - 1);
    }   
    return map;  //Map shows how many steps to get to each location from the the chosen start position
  }  
  
  public static int[][] charToInt(char[][] map){
    int [][]intMap = new int[rows][columns];
    for(int i=0; i<rows; i++){ //read data and fill map as integer array
      for(int j=0; j<columns; j++){
        if(map[i][j] == '#'){
          intMap[i][j] = -1;          
        }else if(map[i][j] == ' '){
          intMap[i][j] = -2;                   
        }else if(map[i][j] == 'S'){
          intMap[i][j] = 0;  
        }else{
          intMap[i][j] = -2;
        }
      }
    }
    return intMap;
  }
      
   public static char[][] addPath(int[][] map, int endRow, int endCol,int steps, char[][] pathMap){ //Overlay new path onto a map
    for(int i=0; i<steps; i++){ //wherever the path continues (one step away) mark as path and keep following
      if(map[endRow][endCol] == map[endRow + 1][endCol] + 1){ //move down
        endRow++;
        pathMap[endRow - 1][endCol] = 'x';
        
      }else if(map[endRow][endCol] == map[endRow - 1][endCol] + 1){ // move up
        endRow--;
        pathMap[endRow + 1][endCol] = 'x';
        
      }else if(map[endRow][endCol] == map[endRow][endCol + 1] + 1){ // move right
        endCol++;
        pathMap[endRow][endCol - 1] = 'x';
        
      }else if(map[endRow][endCol] == map[endRow][endCol - 1] + 1){ // move left
        endCol--;
        pathMap[endRow][endCol + 1] = 'x';
      }
      
    }
    return pathMap;
  }
    
  
  public static int[][] findDestinationPositions(char[][]map){
    int dests = 0;
    int[][] destinations = new int[destCount][2];
    for(int i=0; i<rows; i++){ //read data into map array
      for(int j=0; j<columns; j++){       
        if((map[i][j] != '#') && (map[i][j] != ' ') && (map[i][j] != 'S')){
          destinations [dests][0] = i;
          destinations [dests][1] = j;
          dests++; //Count destinations found
        }
       }
    }
    return destinations;
  }
  
  public static void makePPM(char[][] map, int rows, int columns, String fileName)throws Exception{
    String line = "";
    String colour;
    File mapPPM = new File(fileName.substring(0, fileName.length() - 4) + "PPM.txt");
    PrintWriter output = new PrintWriter(mapPPM);
    output.println("P3"); // fill in ppm header
    output.print(rows * 25);
    output.print(" ");
    output.println(columns * 25);
    output.println(255);
    for(int i=0; i<rows; i++){
      line = "";
      for(int j=0; j<columns; j++){
        if(map[i][j] == 'X'){
          colour = "0 0 255  ";  //blue for destinations
        }else if (map[i][j] == 'x'){
          colour = "255 0 0  "; //red for path
        }else if (map[i][j] == '#'){
          colour = "0 0 0  ";  //bllack for walls
        }else if (map[i][j] == 'S'){
          colour = "0 255 0  "; //green for start
        }else{
          colour = "255 255 255  "; //white for empty space
        }
        for(int k=0; k<25; k++){
          line += colour;
        }
      }
      for(int k=0; k<25; k++){
        output.println(line);
      }
    }    
    output.close();
  }   
}  

      
      
