package uk.ac.ebi.ricordo.owlkb.service;

import uk.ac.ebi.ricordo.owlkb.bean.Query;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 22/06/12
 * Time: 15:44
 */
public class QueryTemplateServiceImpl implements QueryTemplateService {

    private File queryTemplateFile;

    public QueryTemplateServiceImpl(File queryTemplateFile) {
        this.queryTemplateFile = queryTemplateFile;
    }

    @Override
    public ArrayList<Query> getQueryTemplateList() {
        ArrayList queryList = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(queryTemplateFile));
            String strLine;
            while ((strLine = reader.readLine()) != null)   {
                queryList.add(strLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryList;
    }
}
