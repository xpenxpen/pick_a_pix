import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;


public class CountOf1Constraint implements Constraint<Variable, Integer> {
    
    private int type;
    private int index;
    private List<Integer> clues;
    private List<Variable> scope;
    private int expectedCountOf1 = 0;

    public CountOf1Constraint(int type, int index, List<Integer> clues, List<Variable> scope) {
        this.type = type;
        this.index = index;
        this.clues = clues;
        this.scope = scope;
        for (Integer clue: clues) {
            this.expectedCountOf1 += clue;
        }
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
        int countOf1 = 0;
        boolean hasNullValue = false;
        for (Variable var: scope) {
            Integer value = assignment.getValue(var);
            if (value == null) {
                hasNullValue = true;
            } else if (value == 1) {
                countOf1++;
            }
        }
        if (hasNullValue) {
            return countOf1 <= expectedCountOf1;
        }
        
        return countOf1 == expectedCountOf1;
    }

}
