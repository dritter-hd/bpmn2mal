package Config;

import java.util.ArrayList;

public class ConfigDAO {
    private ArrayList<String> nvdSearchQueries;

    public ConfigDAO(){
        this.nvdSearchQueries = new ArrayList<String>();
    }

    public void addToDAOList(String s){
        this.nvdSearchQueries.add(s);
    }

    public boolean daoEmpty(){
        return this.nvdSearchQueries.isEmpty();
    }

    public int daoSize(){
        return this.nvdSearchQueries.size();
    }

    public String getFromDAO(int i){
        return this.nvdSearchQueries.get(i);
    }

    public void setNvdSearchQueries(int i, String s){
        this.nvdSearchQueries.set(i, s);
    }

    public ArrayList<String> getDAO(){
        return nvdSearchQueries;
    }


}
