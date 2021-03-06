/**
 * Copyright 2010 Daniel Ferrin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shemnon.dominator;

public class AnimalModel {
    Animals animal;
    Elements[] elements;

    public AnimalModel(Animals theAnimal, Elements... theElements) {
        animal = theAnimal;
        elements = theElements;
    }

    public int scoreDominance(Elements... tileElements) {
        int count = 0;
        for (Elements myElement : elements) {
            for (Elements theirElement : tileElements) {
                if (myElement != null && myElement.equals(theirElement)) {
                    count++;
                }
            }
        }
        return count;
    }

    public String write() {
        StringBuilder sb = new StringBuilder();
        for (Elements element : elements) {
            if (element != null) {
                sb.append(element.name());
            } else {
                sb.append(" ");
            }
            sb.append(";");
        }
        return sb.toString();
    }

    public void read(String elementsAsString) {
        String[] strings = elementsAsString.split(";");
        Elements[] newE = new Elements[strings.length];
        for (int i = 0; i < strings.length; i++) {
            Elements e;
            if (" ".equals(strings[i])) {
                e = null;
            } else {
                try {
                    e = Elements.valueOf(strings[i]);
                } catch (IllegalArgumentException iae) {
                    e = null;
                }
            }
            newE[i] = e;
        }
        elements = newE;
    }
}
