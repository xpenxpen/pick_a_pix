import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import aima.core.search.csp.Variable;



public class PicPix {

    public static int ROW_SIZE = 10 ;
    public static int COLUMN_SIZE = 10 ;
    public boolean isBackTracking = false ;

    public static Integer [][] COLUMN_CLUES_ARRAY =
            {{4}, {2,1,3},{3,1,1}, {3,1,1,1}, {3,1,1,1},{3,2},{6},{5,4},{7},{4}};

    public static Integer [][] ROW_CLUES_ARRAY = {
            {6},
            {8},
            {9},
            {1,4},
            {2,2,4},
            {1,1,2},
            {1,1,3},
            {3,2},
            {1,1,1},
            {4,1}
    };

    public static Integer [][] COLUMN_CLUES_ARRAY2 =
            {{1}, {1,1},{5}, {1,1}, {1}};

    public static Integer [][] ROW_CLUES_ARRAY2 = {
            {1},
            {1},
            {3},
            {1},
            {5}
    };
    
    public static void main(String[] args) {
        new PicPix().playPickPix();
    }

    public void playPickPix(){

        List<List<Integer>> columnClues = new ArrayList<List<Integer>>();
        List<List<Integer>> rowClues = new ArrayList<List<Integer>>();

        initializeColumnClues(columnClues);
        initializeRowClues(rowClues);

        PickPixCSP csp = new PickPixCSP(ROW_SIZE, COLUMN_SIZE, columnClues,rowClues);
        
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
            for (Variable var : variables) {
                Integer value = assignment.getValue(var);
                result.append(value);
                curIndex++;
                if (curIndex == COLUMN_SIZE) {
                    result.append("\n");
                    curIndex = 0;
                }
                
            }
            System.out.println("Solution");
            System.out.println(result);
        }
        
        System.out.println(stepCounter.getResults() + "\n");

    }

    private void initializeColumnClues( List<List<Integer>> columnClues) {
        for(int i = 0; i< COLUMN_CLUES_ARRAY.length; i++){
            List<Integer> currentColumnClue = Arrays.asList(COLUMN_CLUES_ARRAY[i]);
            columnClues.add(currentColumnClue);
        }
    }

    private void initializeRowClues( List<List<Integer>> rowClues) {
        for(int i = 0; i< ROW_CLUES_ARRAY.length; i++){
            List<Integer> currentRowClue = Arrays.asList(ROW_CLUES_ARRAY[i]);
            rowClues.add(currentRowClue);
        }
    }

}
