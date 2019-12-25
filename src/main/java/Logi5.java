import java.util.List;
import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import aima.core.search.csp.Variable;



public class Logi5 {

    public static int ROW_SIZE = 5 ;
    public static int COLUMN_SIZE = 5 ;
    public boolean isBackTracking = false ;

    public static Integer [][] CLUES_ARRAY =
//        {
//        {null,null,null,null,null},
//        {null,null,null,null,null},
//        {5,2,4,null,null},
//        {null,4,3,1,null},
//        {1,5,2,null,null}
//        };
            
//    {
//    {1,2,null,null,5},
//    {null,null,null,null,null},
//    {null,null,null,null,null},
//    {null,null,null,null,null},
//    {null,null,4,null,null}
//    };
            
    {
    {null,null,1,3,null},
    {null,null,4,null,1},
    {null,4,5,null,null},
    {null,null,2,null,null},
    {1,null,3,null,null}
    };
    
    public static void main(String[] args) {
        new Logi5().play();
    }

    public void play() {

        Logi5Csp csp = new Logi5Csp(ROW_SIZE, COLUMN_SIZE, CLUES_ARRAY);
        
        CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
        CspSolver<Variable, Integer> solver = new MinConflictsSolver<>(2000000);
//        solver.addCspListener(stepCounter);
//        stepCounter.reset();
//        System.out.println("(Minimum Conflicts)");
//        System.out.println(solver.solve(csp));
//        System.out.println(stepCounter.getResults() + "\n");
        
        solver = new FlexibleBacktrackingSolver<Variable, Integer>().setAll();
        solver.addCspListener(stepCounter);
        stepCounter.reset();
        System.out.println("(Backtracking + MRV & DEG + LCV + AC3)");
        Optional<Assignment<Variable, Integer>> solve = solver.solve(csp);
        if (!solve.isPresent()) {
            System.out.println("no solution");
        } else {
            int curIndex = 0;
            Assignment<Variable, Integer> assignment = solve.get();
            List<Variable> variables = assignment.getVariables();
            StringBuilder result = new StringBuilder();
//            for (Variable var : variables) {
//                Integer value = assignment.getValue(var);
//                result.append(value);
//                curIndex++;
//                if (curIndex == COLUMN_SIZE) {
//                    result.append("\n");
//                    curIndex = 0;
//                }
//                
//            }
            for (int i = 0; i < ROW_SIZE; i++) {
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    String name = "Cell Row:" + (i+1) + ",Column:" + (j+1);
                    Variable var = new Variable(name);
                    Integer value = assignment.getValue(var);
                    result.append(value);
                }
                result.append("\n");
            }
            System.out.println("Solution");
            System.out.println(result);
        }
        
        System.out.println(stepCounter.getResults() + "\n");

    }

}
