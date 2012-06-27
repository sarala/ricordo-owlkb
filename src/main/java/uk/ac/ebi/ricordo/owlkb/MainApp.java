package uk.ac.ebi.ricordo.owlkb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.ac.ebi.ricordo.owlkb.bean.Term;
import uk.ac.ebi.ricordo.owlkb.service.OwlKbService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 04/04/12
 * Time: 14:08
 */
public class MainApp {
    public static void main(String [] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("src/main/resources/ricordo-owlkb-config.xml");
        OwlKbService owlKbService = (OwlKbService)ctx.getBean("owlLinkKBService");
        owlKbService.startService();
        ArrayList<Term> list = owlKbService.getSubTerms("part_of some FMA_7088");
        for(Term term: list){
            System.out.println(term.getId());
        }
        owlKbService.stopService();
    }
}
