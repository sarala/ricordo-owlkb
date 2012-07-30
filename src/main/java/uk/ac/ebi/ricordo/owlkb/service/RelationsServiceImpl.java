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

import uk.ac.ebi.ricordo.owlkb.bean.Query;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 13/07/12
 * Time: 13:53
 */
public class RelationsServiceImpl implements RelationsService{

    private File relationsFile;

    public RelationsServiceImpl(File relationsFile) {
        this.relationsFile = relationsFile;
    }

    @Override
    public ArrayList<Query> getRelationsList() {
        ArrayList<Query> relationsList = new ArrayList<Query>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(relationsFile));
            String strLine;
            while ((strLine = reader.readLine()) != null)   {
                relationsList.add(new Query(strLine));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relationsList;

    }
}