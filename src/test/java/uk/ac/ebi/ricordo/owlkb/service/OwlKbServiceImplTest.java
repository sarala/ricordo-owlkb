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

    @Qualifier("owlKbElkService")
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
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_2",list.get(0).getId());
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_3",list.get(1).getId());
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
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_32",list.get(1).getId());
        assertEquals("http://www.ricordo.eu/ricordo.owl#RICORDO_31",list.get(2).getId());
    }

    @Test
    public void testAddandDeleteTerm() throws Exception {
        ArrayList<Term> list = owlKbService.addTerm("RICORDO_1 and part-of some RICORDO_2");
        System.out.println(list.get(0).getId());
        assertEquals(1, list.size());
        list = owlKbService.deleteTerm(list.get(0).getId());
        assertEquals(0,list.size());
    }

}
