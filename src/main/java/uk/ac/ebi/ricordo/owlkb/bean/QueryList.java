package uk.ac.ebi.ricordo.owlkb.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 22/06/12
 * Time: 15:36
 */
@XmlRootElement(name="queries")
public class QueryList {
    private int count;
    private List<Query> queries;

    public QueryList() {}

    public QueryList(List<Query> queries) {
        this.queries = queries;
        this.count = queries.size();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @XmlElement(name="query")
    public List<Query> getQueries() {
        return queries;
    }
    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
}
