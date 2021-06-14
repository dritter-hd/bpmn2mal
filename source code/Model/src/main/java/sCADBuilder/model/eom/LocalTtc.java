package sCADBuilder.model.eom;

import java.util.HashMap;

public abstract class LocalTtc {
    protected HashMap<String,Double> parameters = new HashMap<String, Double>();
    protected Type type;
    
    private String printParameters() {
        StringBuilder sb = new StringBuilder();

        for (String name: parameters.keySet()) {
            sb.append("<parameters name=\""+name+"\" value=\""+parameters.get(name)+"\"/>"+System.lineSeparator());
        }
        
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<localTtcDistribution type=\""+type.toString()+"\">" + System.lineSeparator());
        sb.append(printParameters());
        sb.append("</localTtcDistribution>" + System.lineSeparator());
        return sb.toString();
    }

    public enum Type {
        TRUNCATED {
            @Override
            public String toString() {
                return "TruncatedNormal";
            }
        },
        EXPONENTIAL {
            @Override
            public String toString() {
                return "Exponential";
            }
        },
        GAMMA {
            @Override
            public String toString() {
                return "Gamma";
            }
        },
        NORMAL {
            @Override
            public String toString() {
                return "LogNormal";
            }
        },
        PARETO {
            @Override
            public String toString() {
                return "Pareto";
            }
        }
    }
}
