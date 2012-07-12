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

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: sarala
 * Date: 14/03/12
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public interface QueryConstructorService {
    /**
     * Run a query constructed in manchester query syntax to create an OWL class expression
     * @param manchesterQuery
     * @return OWLClassExpression
     */
    public OWLClassExpression runManchesterQuery(String manchesterQuery);

    /**
     * Add axioms to a OWL file
     * @param axiomSet
     */
    public void addAxioms(Set<OWLAxiom> axiomSet);

    /**
     * Delete axioms from a OWL file
     * @param owlAxiomSet
     */
    public void deleteAxioms(Set<OWLAxiom> owlAxiomSet);

    /**
     * Return OWLOntology
     * @return OWLOntology
     */
    public OWLOntology getQueryOntology();
}
