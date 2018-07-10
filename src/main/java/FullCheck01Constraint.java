import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;


public class FullCheck01Constraint implements Constraint<Variable, Integer> {
    
    private int type;
    private int index;
    private List<Integer> clues;
    private List<Variable> scope;
    private int maxClue;

    public FullCheck01Constraint(int type, int index, List<Integer> clues, List<Variable> scope) {
        this.type = type;
        this.index = index;
        this.clues = clues;
        this.scope = scope;
        
        maxClue = 0;
        for (Integer val : clues) {
            if (val > maxClue) {
                maxClue = val;
            }
        }
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
        
        List<Integer> actualInts = new ArrayList<>();
        int sectionCountOf1 = 0;
        int maxSectionCountOf1 = 0;
        
        boolean hasNull = false;
        for (Variable var: scope) {
            Integer value = assignment.getValue(var);
            if (value == null) {
                hasNull = true;
            }
        }
        
        
        for (Variable var: scope) {
            Integer value = assignment.getValue(var);
            if (value == null || value == 0) {
                if (sectionCountOf1 != 0) {
                    if (sectionCountOf1 > maxSectionCountOf1) {
                        maxSectionCountOf1 = sectionCountOf1;
                        if (maxSectionCountOf1 > maxClue) {
                            //System.out.println("maxSectionCountOf1="+maxSectionCountOf1+"clues="+clues);
                            return false;
                        }
                    }
                    actualInts.add(sectionCountOf1);
                }
                sectionCountOf1 = 0;
            } else {
                sectionCountOf1++;
            }
        }
        
        if (sectionCountOf1 != 0) {
            if (sectionCountOf1 > maxSectionCountOf1) {
                maxSectionCountOf1 = sectionCountOf1;
                if (maxSectionCountOf1 > maxClue) {
                    //System.out.println("maxSectionCountOf1="+maxSectionCountOf1+"clues="+clues);
                    return false;
                }
            }
            actualInts.add(sectionCountOf1);
        }
        
        if (hasNull) {
            return true;
        }
        
        return actualInts.equals(clues);
    }

}
