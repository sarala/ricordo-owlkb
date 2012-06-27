package uk.ac.ebi.ricordo.owlkb.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 02/03/12
 * Time: 04:34
 */
@XmlRootElement
public class Term {
    private String id;

    public Term() {}

    public Term(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
