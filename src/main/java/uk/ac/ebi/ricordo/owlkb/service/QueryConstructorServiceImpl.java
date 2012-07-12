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

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: sarala
 * Date: 14/03/12
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class QueryConstructorServiceImpl implements QueryConstructorService{

    private OWLOntologyManager owlOntologyManager = null;
    private OWLOntology queryOntology = null;
    private IRI docIRI = null;

    public QueryConstructorServiceImpl(IRI docIRI, OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
        this.docIRI = docIRI;
        try {
            queryOntology = owlOntologyManager.loadOntology(docIRI);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }


    public OWLClassExpression runManchesterQuery(String manchesterQuery){
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(owlOntologyManager.getOWLDataFactory(), manchesterQuery);
        parser.setDefaultOntology(queryOntology);
        Set<OWLOntology> importsClosure = queryOntology.getImportsClosure();
        BidirectionalShortFormProvider bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(owlOntologyManager, importsClosure, new SimpleShortFormProvider());
        OWLEntityChecker entityChecker = new ShortFormEntityChecker(bidiShortFormProvider);
        parser.setOWLEntityChecker(entityChecker);

        OWLClassExpression classExp=null;
        try {
            classExp = parser.parseClassExpression();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        return classExp;
    }

    public void addAxioms(Set<OWLAxiom> axiomSet) {
        owlOntologyManager.addAxioms(queryOntology, axiomSet);
        try {
            owlOntologyManager.saveOntology(queryOntology, docIRI);
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    public void deleteAxioms(Set<OWLAxiom> axiomSet) {
        owlOntologyManager.removeAxioms(queryOntology, axiomSet);
        try {
            owlOntologyManager.saveOntology(queryOntology, docIRI);
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    public OWLOntology getQueryOntology() {
        return queryOntology;
    }
}
