import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;

public class Logi5Csp extends CSP<Variable, Integer> {

    public int rowCount;
    public int colCount;
    
    public List<List<Variable>> variableList;
    public Integer[][] cluesArray;
    
    public Logi5Csp(int rowCount, int colCount, Integer[][] cluesArray) {
        
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.cluesArray = cluesArray;
        
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

        Domain<Integer> colors = new Domain<>(1, 2, 3, 4, 5);
        
        for (Variable var : getVariables()) {
            setDomain(var, colors);
        }
        
        for (int i = 0 ; i < cluesArray.length; i++) {
            for (int j = 0 ; j < cluesArray[i].length; j++) {
                if (cluesArray[i][j] != null) {
                    setDomain(variableList.get(i).get(j), new Domain<>(cluesArray[i][j]));
                }
            }
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
            addConstraint(new Each1To5OnceConstraint(0, i, rowScope));
        }
        
        for (int i = 0; i < colCount; i++) {
            List<Variable> colScope = new ArrayList<>();
            for (int j = 0; j < rowCount; j++) {
                List<Variable> oneRow = variableList.get(j);
                colScope.add(oneRow.get(i));
            }
            addConstraint(new Each1To5OnceConstraint(1, i, colScope));
        }
        
    }
}
