import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;

public class PickPixCSP extends CSP<Variable, Integer> {

    public int rowCount;
    public int colCount;
    
    public List<List<Variable>> variableList;
    public List<List<Integer>> colClues;
    public List<List<Integer>> rowClues;
    
    public PickPixCSP(int rowCount, int colCount, List<List<Integer>> colClues,List<List<Integer>> rowClues) {
        
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.colClues = colClues;
        this.rowClues = rowClues;
        
        variableList = new ArrayList<List<Variable>>();

        for (int i = 0 ; i < rowCount; i++) {
            List<Variable> oneRow = new ArrayList<Variable>();
            for (int j = 0 ; j < colCount; j++) {
                String rowNumber = Integer.toString(i+1);
                String columnNumber = Integer.toString(j+1);
                String name = "Cell Row:" + rowNumber + ",Column:" + columnNumber;
                Variable var = new Variable(name);
                oneRow.add(var);
                
                addVariable(var);
            }
            variableList.add(oneRow);
        }

        Domain<Integer> colors = new Domain<>(1, 0);
        
        for (Variable var : getVariables()) {
            setDomain(var, colors);
        }

        initializeConstraints();

    }

    private void initializeConstraints() {
        //0:row, 1:col 
        for (int i = 0; i < rowCount; i++) {
            List<Variable> oneRow = variableList.get(i);
            List<Variable> rowScope = new ArrayList<>();
            for (int j = 0; j < colCount; j++) {
                rowScope.add(oneRow.get(j));
            }
            addConstraint(new CountOf1Constraint(0, i, rowClues.get(i), rowScope));
            addConstraint(new FullCheck01Constraint(0, i, rowClues.get(i), rowScope));
        }
        
        for (int i = 0; i < colCount; i++) {
            List<Variable> colScope = new ArrayList<>();
            for (int j = 0; j < rowCount; j++) {
                List<Variable> oneRow = variableList.get(j);
                colScope.add(oneRow.get(i));
            }
            addConstraint(new CountOf1Constraint(1, i, colClues.get(i), colScope));
            addConstraint(new FullCheck01Constraint(1, i, colClues.get(i), colScope));
        }
        
    }
}
