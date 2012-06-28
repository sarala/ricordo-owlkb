package uk.ac.ebi.ricordo.owlkb.service;

import uk.ac.ebi.ricordo.owlkb.bean.Term;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 13/03/12
 * Time: 21:39
 */
public interface OwlKbService {

    /**
     * Queries for all subclasses
     * @param query following Manchester Query Syntax
     * @return list of terms
     */
    public ArrayList<Term> getSubTerms(String query);

    /**
     * Queries for all equivalent classes
     * @param query following Manchester Query Syntax
     * @return list of terms
     */
    public ArrayList<Term> getEquivalentTerms(String query);

    /**
     * Queries for all subclasses and equivalent classes
     * @param query following Manchester Query Syntax
     * @return list of terms
     */
    public ArrayList<Term> getTerms(String query);

    /**
     * Add a new term into the knowledge base using Manchester Query Syntax if the term does not exist otherwise return equivalent terms
     * @param query following Manchester Query Syntax
     * @return
     */
    public ArrayList<Term> addTerm(String query);

    /**
     * todo - only deletes axioms and class need to be deleted.
     * Delete axioms from the knowledge base using Manchester Query Syntax.
     * Deletion only happens if the class expression created by this query is not anonymous and an empty list is returned.
     * Otherwise equivalent terms are returned.
     * @param query following Manchester Query Syntax
     * @return
     */

    public ArrayList<Term> deleteTerm(String query);

    /**
     * Initialise the service (load ontologies and classify)
     */
    public void startService();

    /**
     * Stop the services
     */
    public void stopService();
}