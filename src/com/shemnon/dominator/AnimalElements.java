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

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Map;

public class AnimalElements extends Activity {

    Map<Animals, Button[]> animalButtons;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_elements);

        animalButtons.put(Animals.Mammals, new Button[] {
                (Button) findViewById(R.string.ME1),
                (Button) findViewById(R.string.ME2),
                (Button) findViewById(R.string.ME3),
                (Button) findViewById(R.string.ME4),
                (Button) findViewById(R.string.ME5),
                (Button) findViewById(R.string.ME6),
        });
        animalButtons.put(Animals.Reptiles, new Button[] {
                (Button) findViewById(R.string.RE1),
                (Button) findViewById(R.string.RE2),
                (Button) findViewById(R.string.RE3),
                (Button) findViewById(R.string.RE4),
                (Button) findViewById(R.string.RE5),
                (Button) findViewById(R.string.RE6),
        });
        animalButtons.put(Animals.Birds, new Button[] {
                (Button) findViewById(R.string.BE1),
                (Button) findViewById(R.string.BE2),
                (Button) findViewById(R.string.BE3),
                (Button) findViewById(R.string.BE4),
                (Button) findViewById(R.string.BE5),
                (Button) findViewById(R.string.BE6),
        });
        animalButtons.put(Animals.Amphibians, new Button[] {
                (Button) findViewById(R.string.AE1),
                (Button) findViewById(R.string.AE2),
                (Button) findViewById(R.string.AE3),
                (Button) findViewById(R.string.AE4),
                (Button) findViewById(R.string.AE5),
                (Button) findViewById(R.string.AE6),
        });
        animalButtons.put(Animals.Arachnids, new Button[] {
                (Button) findViewById(R.string.SE1),
                (Button) findViewById(R.string.SE2),
                (Button) findViewById(R.string.SE3),
                (Button) findViewById(R.string.SE4),
                (Button) findViewById(R.string.SE5),
                (Button) findViewById(R.string.SE6),
        });
        animalButtons.put(Animals.Insects, new Button[] {
                (Button) findViewById(R.string.IE1),
                (Button) findViewById(R.string.IE2),
                (Button) findViewById(R.string.IE3),
                (Button) findViewById(R.string.IE4),
                (Button) findViewById(R.string.IE5),
                (Button) findViewById(R.string.IE6),
        });
    }
}