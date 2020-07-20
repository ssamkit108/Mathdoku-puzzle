
//This class mainly used to improve the efficiency of state space
//It contains only check point where to check given conditions
public class Checkpoint{
    boolean exist;
    Group Group;
    Checkpoint(boolean e, Group p)
    {
        exist = e;
        Group = p;
    }
}