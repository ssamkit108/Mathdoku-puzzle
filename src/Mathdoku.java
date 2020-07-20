import java.io.BufferedReader;
import java.util.*;

public class Mathdoku
{
    static int counter=0;
    static int dim;  //dimension of the matrix
    static ArrayList<Group> problem; //List of Groups
    static public Set<Character> group_char=new HashSet<Character>(); //List of Group label
    static Map<Character, ArrayList<Point>> group_points = new HashMap  <Character, ArrayList<Point>>(); //Points associated with group label
    static int[][] cand;  //the current solution candidate under trying, which is a matrix
    static Checkpoint[][] constraint_at; //Checkpoint array for checking group constrain
    static public int[][] sol;  //Solution matrix
    static int choices=0;       //choices
    static String solution="";  //Solution String
    static String init_matrix="";   //Input matrix

    static boolean solved=false;        //flag for solve method
    static boolean ready=false;         //flag for readyToSolve method
    static boolean input_flag=true;     //flag for LoadPuzzle method

    //This method will load the puzzle matrix and group constraints
    public static boolean loadPuzzle(BufferedReader stream){
        clear_all();
        problem = new ArrayList<Group>();
        int col = 0;
        int row = 0;
        int temp=1;
        try {
            //read the matrix input
            //group_points has map for list of points with respect to group lable of character
            String line;
            //This while group is used to read square matrix
            while(row<temp) {
                line=stream.readLine();
                if(line.length() == 0) return false;
                Scanner sl = new Scanner(line).useDelimiter("\\s*");
                while(sl.hasNext()){
                    //read one column of the row
                    char c = sl.next().charAt(0);
                    group_char.add(c);
                    init_matrix=init_matrix+c;
                    ArrayList<Point> points = group_points.get(c);
                    if(points == null){ //the region has not been read yet, new the region
                        points = new ArrayList<Point>();
                    }
                    Point point = new Point(row, col);
                    points.add(point);
                    group_points.put(c, points);
                    col++;
                }
                sl.close();
                if(dim == 0){
                    dim = col;
                    temp=col;
                }
                if(col!=dim){
                    input_flag=false;
                    return false;
                }
                init_matrix=init_matrix+"\n";
                col = 0;
                row++;
            }
            //read the region of list of constraints
            line=null;
            while((line=stream.readLine())!= null) {
                if(line.isEmpty()) continue;
                String[] a = line.split("\\s+");
                if(a.length!=3 && !line.isEmpty()){
                    input_flag=false;
                    //return false;
                }
                if(a.length==3) {
                    char A = a[0].charAt(0);
                    //If someone enter condition for two times
                    if (!group_char.contains(A)) {
                        input_flag = false;
                        //return false;
                    }
                    group_char.remove(A);
                    int num = Integer.parseInt(a[1]);
                    if(num<0){
                        input_flag=false;
                    }
                    String op = "";
                    char O = a[2].charAt(0);
                    switch (O) {
                        case '+':
                            op = "ADD";
                            break;
                        case '-':
                        case '–':
                            op = "SUB";
                            break;
                        case '*':
                            op = "MUL";
                            break;
                        case '/':
                            op = "DIV";
                            break;
                        case '=':
                            op = "EQ";
                            break;
                        default:
                            input_flag = false;
                            break;
                    }

                    ArrayList<Point> points = group_points.get(A);
                    //If there is more than one points in = operator
                    if (op.equalsIgnoreCase("EQ") && points.size() != 1) {
                        //System.out.println(line + " should have an operator");
                        input_flag = false;
                        //return false;
                    }

                    Group p = new Group(op, num, points);
                    //p.output();
                    problem.add(p);
                }
            }
            //If there is constraint missing for some character letter
            if(!group_char.isEmpty()){
                input_flag=false;
                //return false;
            }
            //System.out.println(group_points);
            //stream.close();
            return true;
        }
        catch (Exception e) {
            input_flag=false;
            return false;
        }
    }

    //This method will check that required field needed to solve puzzle
    public boolean readyToSolve(){
        //We have already did all validation in loadpuzzle method
        if(input_flag && problem!=null && dim!=0){
            ready=true;
            return true;
        }
        else {
            ready=false;
            return false;
        }
    }

    //This method will solve the puzzle
    public boolean solve(){
        try {
            if (ready == true) {
                solved = false;
                sol = new int[dim][dim];
                cand = new int[dim][dim];
                preprocess();
                solve_puzzle(0, 0);
                if (!solution.isEmpty()) {
                    solved = true;
                    return true;
                } else {
                    solved = false;
                    return false;
                }
            } else {
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    //This method will return solution string
    public String print(){
        if(!solution.isEmpty()){
            return solution;
        }
        else if(!init_matrix.isEmpty()) {
            return init_matrix;
        }
        else{
            return null;
        }
    }

    public int choices(){
        return choices;
    }

    //This is recursive method to solve puzzle
    static void solve_puzzle(int row, int col)
    {
        try {
            //generate value for matrix
            for (int i = 0; i < dim; i++) {
                //Check is there value of i is possible for cell
                if (!check_possible(row, col, i)) continue;
                cand[row][col] = i + 1;

                //This will check the group whose all the points are generated already
                //If it fails then we do not proceed
                //and start backtracking and try to satisfy constraint of that group
                if (problem != null && constraint_at[row][col].exist) { //enable a Group to match against
                    boolean ok = Group.match_Group(cand, constraint_at[row][col].Group);
                    if(ok){
                        counter++;
                    }
                    if (!ok) {
                        cand[row][col] = 0;
                        continue;
                    }
                }

                //If last column and next row
                if (col + 1 == dim && row + 1 != dim) {
                    solve_puzzle(row + 1, 0);
                }
                //If same row next column
                else if (col + 1 != dim) {
                    solve_puzzle(row, col + 1);
                }
                //If whole matrix is generated
                else {
                    if (match_solution(cand, problem) && !solved) {
                        solved = true;
                        for (int k = 0; k < dim; k++) {
                            for (int l = 0; l < dim; l++) {
                                choices = counter;
                                solution = solution + cand[k][l];
                                sol[k][l] = cand[k][l];
                            }
                            solution = solution + "\n";
                        }
                        break;
                    }
                }
                cand[row][col] = 0;
            }
        }
        catch (Exception e){
            solved=false;
        }
    }

    //This will accept matrix and list of group
    //This will determine that solution matrix is valid or not
    static private boolean match_solution(int[][] sol, ArrayList<Group> quests){
        boolean flag = false;
        for(Group quest : quests){
            flag = Group.match_Group(sol, quest);
            if( !flag ) return false;
        }
        return true;
    }

    //This will return maximum points from the arraylist of points
    static private Point find_max(ArrayList<Point> points) {
        Point max = new Point(0,0);
        for( Point point: points){
            if( max.x == point.x){
                if( max.y < point.y ) max.y = point.y;
            }else if ( max.x < point.x) {
                max.x = point.x;
                max.y = point.y;
            }
        }
        return max;
    }

    //This will check that same element is present in respective row or column
    static boolean check_possible(int row,int col,int i){
        //check number is present in same row or not
        for(int k=0;k<col;k++){
            if(cand[row][k]==(i+1)){
                return false;
            }
        }
        //check number is present in same column or not
        for(int k=0;k<row;k++){
            if(cand[k][col]==(i+1)){
                return false;
            }
        }
        return true;
    }

    //This will initialize all field needed to solve puzzle
    void preprocess(){
        constraint_at = new Checkpoint[dim][dim];
        solution="";
        counter=0;
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                constraint_at[i][j] = new Checkpoint(false, null);
            }
        }
        for(Group Group: problem){
            Point max_point = find_max(Group.points);
            constraint_at[max_point.x][max_point.y] = new Checkpoint(true, Group);
        }
    }

    //This will clear all field of class before solving new puzzle
    static void clear_all(){
        dim=0;
        counter=0;
        choices=0;
        input_flag=true;
        if(problem!=null)
            problem.clear();
        if(group_points!=null)
            group_points.clear();
        solution="";
        init_matrix="";
        if(group_char!=null)
            group_char.clear();
    }
}
