package uk.ac.ebi.ricordo.owlkb.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 22/06/12
 * Time: 15:36
 */

@XmlRootElement
public class Query {
    private String query;

    public Query() {}

    public Query(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
