import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;


public class Each1To5OnceConstraint implements Constraint<Variable, Integer> {
    
    private int type;
    private int index;
    private List<Variable> scope;

    public Each1To5OnceConstraint(int type, int index, List<Variable> scope) {
        this.type = type;
        this.index = index;
        this.scope = scope;
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
        boolean hasNullValue = false;
        Set<Integer> oneRowSet = new HashSet<>();
        for (Variable var: scope) {
            Integer value = assignment.getValue(var);
            if (value == null) {
                hasNullValue = true;
            } else {
                if (oneRowSet.contains(value)) {
                    return false;
                }
                oneRowSet.add(value);
            }
        }
        //System.out.println("isSatisfiedWith="+oneRowSet.size());
        
        if (hasNullValue) {
            return true;
        }
        
        return true;
    }

}
