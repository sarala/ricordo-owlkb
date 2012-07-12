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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ricordo.owlkb.bean.Term;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 21/06/12
 * Time: 17:04
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:ricordo-owlkb-config.xml"})
public class OwlKbServiceImplTest {

    @Qualifier("owlLinkKBService")
    @Autowired
    private OwlKbService owlKbService;

    @Before
    public void setUp() throws Exception {
        owlKbService.startService();
    }

    @After
    public void tearDown() throws Exception {
        owlKbService.stopService();
    }

    @Test
    public void testGetSubTerms(){
        ArrayList<Term> list = owlKbService.getSubTerms("RICORDO_1");
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_36",list.get(0).getId());
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_21",list.get(1).getId());
        assertEquals(14,list.size());

    }

    @Test
    public void testGetEqTerms(){
        ArrayList<Term> list = owlKbService.getEquivalentTerms("RICORDO_1");
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_1",list.get(0).getId());
    }

    @Test
    public void testGetTerms(){
        ArrayList<Term> list = owlKbService.getTerms("RICORDO_3");
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_3",list.get(0).getId());
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_36",list.get(1).getId());
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_34",list.get(2).getId());
    }

    @Test
    public void testAddandDeleteTerm() throws Exception {
        ArrayList<Term> list = owlKbService.addTerm("RICORDO_1 and part-of some RICORDO_2");
        assertEquals(1, list.size());
        String id = list.get(0).getId();
        id = id.substring(id.indexOf('#')+1);
        System.out.println(id);
        list = owlKbService.deleteTerm(id);
        assertEquals(0,list.size());
    }

}
