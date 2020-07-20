import java.util.ArrayList;

class Group
{
    //one computation Group in Kenken
    String op;
    int  num;
    ArrayList<Point> points;

    Group(String st, int ans, ArrayList<Point> p)
    {
        op = st; num = ans; points = p;
    }

    //This method will check particular group constraints for given points
    static boolean match_Group(int[][] sol, Group quest)
    {
        Point point1=null,point2=null;
        int a=0,b=0;

        boolean flag = false;
        switch(quest.op){
            case "ADD":
                int sum = 0;
                for(Point p : quest.points){
                    sum += sol[p.x][p.y];
                }
                flag=(sum == quest.num);
                //flag = match_add(sol, quest);
                break;
            case "SUB":
                point1 = quest.points.get(0);
                point2 = quest.points.get(1);

                a = sol[point1.x][point1.y];
                b = sol[point2.x][point2.y];
                flag = ((a-b) == quest.num) || ((b-a) == quest.num);
                break;
            case "MUL":
                int prod = 1;
                for(Point p : quest.points){
                    prod *= sol[p.x][p.y];
                }
                flag = (prod == quest.num);
                break;
            case "DIV":
                point1 = quest.points.get(0);
                point2 = quest.points.get(1);

                a = sol[point1.x][point1.y];
                b = sol[point2.x][point2.y];
                flag = ((a/b) == quest.num) || ((b/a) == quest.num);
                break;
            case "EQ":
                Point p = quest.points.get(0);

                flag = (quest.num == sol[p.x][p.y]);
                break;
            default:
                flag=false;
        }
        return flag;

    }

}