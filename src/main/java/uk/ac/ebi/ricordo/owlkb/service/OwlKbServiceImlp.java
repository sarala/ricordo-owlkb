/*
 * Copyright 2012 EMBL-EBI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.ricordo.owlkb.service;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import uk.ac.ebi.ricordo.owlkb.bean.Term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 14/06/12
 * Time: 14:38
 */
public class OwlKbServiceImlp implements OwlKbService {

    private String kbNs;
    private QueryConstructorService queryConstructorService;
    private OWLOntologyManager owlOntologyManager;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;

    public OwlKbServiceImlp(String kbNs, QueryConstructorService queryConstructorService, OWLOntologyManager owlOntologyManager, OWLReasonerFactory reasonerFactory) {
        this.kbNs = kbNs;
        this.queryConstructorService = queryConstructorService;
        this.owlOntologyManager = owlOntologyManager;
        this.reasonerFactory = reasonerFactory;
    }

    public ArrayList<Term> getSubTerms(String query) {
        OWLClassExpression exp = queryConstructorService.runManchesterQuery(query);
        return getSubTerms(exp);
    }

    /**
     * Queries for all subclasses
     * @param exp Owl class expression constructed from the manchester query
     * @return list of terms
     */
    private ArrayList<Term> getSubTerms(OWLClassExpression exp) {
        ArrayList<Term> idList = new ArrayList<Term>();
        if(exp!=null){
            NodeSet<OWLClass> subClasses = reasoner.getSubClasses(exp, true);
            if (subClasses!=null){
                for (Node<OWLClass> owlClassNode : subClasses) {
                    idList.add(new Term(owlClassNode.getEntities().iterator().next().toStringID()));
                }
            }
        }
        return idList;
    }


    public ArrayList<Term> getEquivalentTerms(String query){
        OWLClassExpression exp = queryConstructorService.runManchesterQuery(query);
        return getEquivalentTerms(exp);
    }

    /**
     * Queries for all equivalent
     * @param exp Owl class expression constructed from the manchester query
     * @return list of terms
     */
    private ArrayList<Term> getEquivalentTerms(OWLClassExpression exp){
        ArrayList<Term> idList = new ArrayList<Term>();
        if(exp!=null){
            Node<OWLClass> equivalentClasses = reasoner.getEquivalentClasses(exp);
            if(equivalentClasses != null)
                idList.add(new Term(equivalentClasses.getEntities().iterator().next().toStringID()));
        }
        return idList;
    }

    public ArrayList<Term> getTerms(String query) {
        ArrayList<Term> idList = new ArrayList<Term>();
        OWLClassExpression exp = queryConstructorService.runManchesterQuery(query);
        idList.addAll(getEquivalentTerms(exp));
        idList.addAll(getSubTerms(exp));
        return idList;
    }

    public ArrayList<Term> addTerm(String query) {
        OWLClassExpression exp = queryConstructorService.runManchesterQuery(query);
        ArrayList<Term> idList = getEquivalentTerms(exp);
        if(idList.isEmpty()){
            String ricordoid = String.valueOf(System.currentTimeMillis());
            OWLClass newowlclass = owlOntologyManager.getOWLDataFactory().getOWLClass(IRI.create(kbNs + "#RICORDO_" + ricordoid));

            OWLAxiom axiom = owlOntologyManager.getOWLDataFactory().getOWLEquivalentClassesAxiom(newowlclass, exp);
            Set<OWLAxiom> axiomSet = new HashSet<OWLAxiom>();
            axiomSet.add(axiom);

            //add to owlfile
            queryConstructorService.addAxioms(axiomSet);

            idList.add(new Term(newowlclass.toStringID()));
            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        }

        return idList;
    }



    public ArrayList<Term> deleteTerm(String query) {
        OWLClassExpression exp = queryConstructorService.runManchesterQuery(query);
        ArrayList<Term> idList = getEquivalentTerms(exp);

        if(!exp.isAnonymous()){
            OWLClass owlClass = exp.asOWLClass();

            Set<OWLClassAxiom> owlClassAxiomSet = owlOntologyManager.getOntology(org.semanticweb.owlapi.model.IRI.create(kbNs)).getAxioms(owlClass);
            Set<OWLAxiom> owlAxiomSet = new HashSet<OWLAxiom>(owlClassAxiomSet.size());

            for (OWLClassAxiom anOwlClassAxiomSet : owlClassAxiomSet) {
                owlAxiomSet.add(anOwlClassAxiomSet);
            }

            queryConstructorService.deleteAxioms(owlAxiomSet);
            idList.clear();

            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        }

        return idList;
    }

    public void startService() {
        reasoner = reasonerFactory.createReasoner(queryConstructorService.getQueryOntology());
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
    }

    public void stopService() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
