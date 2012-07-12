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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ricordo.owlkb.bean.Query;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 22/06/12
 * Time: 15:53
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:ricordo-owlkb-config.xml"})
public class QueryTemplateServiceImplTest {

    @Autowired
    private QueryTemplateService queryTemplateService;

    @Test
    public void testGetQueryTemplateList() throws Exception {
        ArrayList<Query> queries = queryTemplateService.getQueryTemplateList();
        assertEquals("term", queries.get(0));
        assertEquals("relation some term",queries.get(1));
        assertEquals("relation some term and relation some term",queries.get(2));
        assertEquals("relation some term or relation some term",queries.get(3));
        assertEquals("term and relation some term",queries.get(4));
        assertEquals("term and relation some ( relation some term )",queries.get(5));
    }
}
