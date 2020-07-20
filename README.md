## Note
> This is an assignment for the course CSCI3901-Software Developement Concepts. The main purpose of this assignment is to familier with the state space search and implement problem.
# Description

This program is developed to solve the Mathdoku puzzle. There will be two inputs from the user: a square grid with a group letter, list of constraints for each Group. This problem is a kind of state-space exploration. It is solved by the concept of recursion and backtracking. There are mainly **four** classes, such as **Mathdoku** , **Group** , **Point** and **Checkpoint**. Following is the general approach used and a high-level overview of classes in the program:

**Class Mathdoku** : This class have 12 methods, and it is responsible for loading puzzle and solving the puzzle with a given input stream.

**Class Group** : This class has one method, and it is responsible for storing the Group of a grid. Which include the operator, number and ArrayList of points which belong to that Group.

**Class Point:** This class stores x and y coordinates for a cell of a square grid.

**Class Checkpoint:** This class is responsible for storing the details about where to check the condition of the Group. This class is mainly used to improve the efficiency of the solve method. It contains exist flag and group identifier that shows which Group we need to check if checkpoint exists.

# Files and external data

- There are four java files for this problem.
  - First java file named &quot; **Mathdoku.java**&quot;, which contains the Mathdoku class with 12 methods.
  - Second java file named &quot; **Group.java,**&quot; which contains Group class.
  - Third java file named &quot; **Checkpoint.java**&quot;, which contains Checkpoint class.
  - Fourth java file named &quot; **Point.java**&quot;, which contains Point class.
- There are **no database** tables related to this program implementation.

# Data Structure and their relation to each other

Following are the data structures used in the program:

- Two-dimensional integer array is used to store the solution in a two-dimensional array.
- All the Groups are stored in ArrayList\&lt;Group\&gt;.
- We have used HashMap to store character letter of Group as a key and ArrayList of Points.

# Key algorithms and design elements:

Here is my general approach to a solution:

1. Load the input stream and store the group character and that point of a matrix in HashMap.
2. We store all the constraints in one ArrayList named &quot;problem.&quot;
3. After successfully loading, we will check that all the required fields are available, and input is valid or not using the readyToSolve method.
4. If the puzzle is ready to solve, then we call the solve method to solve the method.
5. In that, we first invoke the preprocess method.
    1. This method will generate one 2D matrix of Checkpoint object.
    2. We will iterate through each group constraint and points of Group. Each time we will find that which is the maximum point location in that Group.
    3. We will create a checkpoint at that point for that specific point.
6. After that, we start from the first point of the matrix, which is at 0th row and 0th column.
7. Call recursive method sove\_puzzle with 0th row and 0th column.
    1. For loop for 1 to the dimension of a square matrix.
    2. Check that is the value already present in the same row or the same column using check\_possible. If that method returns true, then put that value.
    3. Check does there any checkpoint exists at a point.If checkpoint exists then check the particular Group with a filled value of the matrix.
    4. So, how it will help us that we are recursively exploring state-space matrix and if I got all the points which I need to check any group then we will check it and if data of matrix satisfy the condition for that Group then only we will explore more state-space for that input.
    5. If it does not satisfy the group constraint condition, then we restore the value to 0. And start backtracking to the upper state.which will lead to getting the correct solution point. Start from step 7 (i).
    6. After all, This will improve the efficiency of the algorithm. Because we don&#39;t need to check the matrix after all the points filled.
    7. If it satisfies the group constraint, then move to the next point and pass the coordinates of the next point to solve\_puzzle method and start from step 7.
8. After iterating all the points one full matrix will be generated, we will check that matrix using the match\_solution method. If the whole matrix satisfies all constraints, then we store that matrix in the solution matrix.
9. If it does not solve, then we start backtracking and follow all steps from 7.

# Improving efficiency:

- This program will check Group when all points of Group are available. If that Group does not satisfy the condition, then we do not explore more space for that matrix.
- This is how we minimize our state space.
- The other thing we are doing is that first, we fix the value of the &quot;=&quot; operator. So, we don&#39;t need to make guesses for that Group.
- I tried to make it as possible as time efficient.

# Description of Methods Used:

- loadPuzzle:
  - This method will read the BufferedReader stream, store the square matrix and Group of conditions.
  - This will do all the validation related to input data.
  - It would return true if the puzzle loaded successfully. Otherwise, it will return false.
- readyToSolve:
  - This method will check that all the required details to solve the puzzle are available or not.
  - If all the details are available, then it will return true. Otherwise it will return false.
- Preprocess:
  - This method will create a two-dimensional array named &quot;constraint\_at.&quot;
  - First, we will iterate all groups and identify that which is the maximum point for that Group, we will check the condition on that point.
- find\_max:
  - This method will compare two points and return the maximum point.
  - This method will be called by the preprocess method .
- Solve:
  - Solve method will initialize the sol matrix and candidate matrix.
  - This will call the preprocess method to initialize problem.
  - It will call recursive method named solve\_puzzle.
  - If puzzle is solved then it will return true. Otherwise, it will return false.
- Solve\_puzzle:
  - This is a recursive method.
  - This will start from the 0th row and 0th column.
  - This will first check that value is possible at the position according to row and column rules.
  - This will be checked using the check\_possible method. If it returns true, then we will proceed.
  - We put the value and then check if there is a constraint for Group satisfied or not.
  - If it is not satisfied, then we backtrack the value and change the value for the previous cell.
  - After the whole matrix generated, we will check all group conditions using the match\_solution method.
  - If the solution matrix is correct, then it will store in solution string and set solved flag to true.
- Match\_solution:
  - This method will contain the matrix and ArrayList of Group as a parameter.
  - It will verify all conditions and if all condition satisfies then return true, otherwise return false.
- Match\_group:
  - This method will check particular group constraints.
  - This will return true if constraint for Group satisfies, otherwise it will return false.
- Clear\_all:
  - This method will clear all the variables before the solve method invoked.
  - It set choices to 0, solution string to empty and clear all ArrayList of Group.
- Check\_possible:
  - This method will check that same value is present in same row/column or not.
  - If value present then it will return false, otherwise it will return false.

# Assumptions

- There will be no duplication for constraints allowed.
- Characters for Group is case sensitive.
- If the solution is not exist, then solve method will return false.
- If the solution is not exist, then print method will return character groups.
- Solve method will give a solution, will not provide all possible solution.
- No negative value allowed in constraint.

# Limitations

- This program is only valid for 5 operators such as +, -, \*, /, =.
- When the size of puzzle will be more than 10 then print method will not return string properly because professor mentioned that no space is allowed in print method between cell so double-digit number such as 10,11,12 could create confusion.

# Test Cases:

**Input validation**

- loadPuzzle:
  - BufferedReader object is empty.
  - BufferedReader input data is empty.
  - BufferedReader is null.

**Boundary Case**

- loadPuzzle:

  - Size of Puzzle matrix 10\*10.
  - The size of the Puzzle matrix is 2\*2.
  - Number of groups are 4.
  - Number of groups are 100.
  - Number of constraints are 1.
  - Number of constraints are 100.
- Solve:
  - No solution is possible.
  - More than one solution is possible.
- Print:
  - Return string of matrix size 2.
  - Return string of matrix size 10.

**Control Flow**

- loadPuzzle:

  - If the input matrix is not square.
  - Puzzle is not square matrix.
  - Group character for a particular cell is missing.
  - Constraints are missing for a particular group character.
  - The format of the constraint is not valid. For example, the first character should be the correct group letter, the second should be a valid integer, and the third should be a logical operator.
  - There is a constraint whose Group is not exist in puzzle.
  - Constraint duplication for same group.
  - There is operator which is not valid such as % (Modulo).
  - If Group contains more than one cell points and operator is &#39;=&#39;.
  - If subtraction and division operator contain more than two cell points.
- readyToSolve:
  - loadPuzzle returns false.
  - loadPuzzle returns true.
  - Constraints are not available.
  - Group for matrix are not available.
- Solve:
  - readyToSolve returns false.
  - readyToSolve returns true.
  - Solution is not possible.
  - Size of puzzle matrix is 4\*4.
- Print:
  - Solve method return true.
  - Solve method return false.
  - Solution is empty.
  - Solution has single correct output.
- Choices:
  - Very large number of guesses required.
  - Output comes in one guess.

**Data Flow**

- User call readyToSolve method without invoking loadPuzzle.
- User call solve method without invoking readyToSolve method.
- User call print method without invoking solve method.
- User call solve method twice in a row for the same input.
- User first, LoadPuzzle with BufferedReader Stream and then solve the whole puzzle. Second, with the same object and different BufferedReader, users call all methods in normal flow and solve the puzzle.
- User invokes LoadPuzzle method twice with same BufferedReader.

**Expected Data flow should be:**

- Invoke loadPuzzle.
- Invoke ReadyToSolve.
- Invoke Solve.
- Invoke Print.
- Invoke Choices.
