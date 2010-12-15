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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnimalElements extends Activity {

    Map<Animals, Button[]> animalButtons;
    Map<Animals, AnimalModel> animalModels;
    Map<Animals, TextView> scores;
    Set<Animals> relevantAnimals;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_elements);
        animalButtons = new HashMap<Animals, Button[]>();

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
        animalButtons.put(null, new Button[] {
                (Button) findViewById(R.string.HE1),
                (Button) findViewById(R.string.HE2),
                (Button) findViewById(R.string.HE3),
                (Button) findViewById(R.string.HE4),
                (Button) findViewById(R.string.HE5),
                (Button) findViewById(R.string.HE6),
        });

        scores = new HashMap<Animals, TextView>();
        scores.put(Animals.Mammals,    (TextView) findViewById(R.string.text_score_mammals));
        scores.put(Animals.Reptiles,   (TextView) findViewById(R.string.text_score_reptiles));
        scores.put(Animals.Birds,      (TextView) findViewById(R.string.text_score_birds));
        scores.put(Animals.Amphibians, (TextView) findViewById(R.string.text_score_amphibians));
        scores.put(Animals.Arachnids,  (TextView) findViewById(R.string.text_score_arachnids));
        scores.put(Animals.Insects,    (TextView) findViewById(R.string.text_score_insects));

        animalModels = new HashMap<Animals, AnimalModel>();
        animalModels.put(Animals.Mammals,    new AnimalModel(Animals.Mammals,    Elements.Meat,  Elements.Meat,  null, null, null, null));
        animalModels.put(Animals.Reptiles,   new AnimalModel(Animals.Reptiles,   Elements.Sun,   Elements.Sun,   null, null, null, null));
        animalModels.put(Animals.Birds,      new AnimalModel(Animals.Birds,      Elements.Seeds, Elements.Seeds, null, null, null, null));
        animalModels.put(Animals.Amphibians, new AnimalModel(Animals.Amphibians, Elements.Water, Elements.Water, Elements.Water, null, null, null));
        animalModels.put(Animals.Arachnids,  new AnimalModel(Animals.Arachnids,  Elements.Grub,  Elements.Grub,  null, null, null, null));
        animalModels.put(Animals.Insects,    new AnimalModel(Animals.Insects,    Elements.Grass, Elements.Grass, null, null, null, null));
        animalModels.put(null,               new AnimalModel(null,               null,           null,           null, null, null, null));

        for (Map.Entry<Animals, Button[]> entry : animalButtons.entrySet()) {
            final Animals animal = entry.getKey();
            Button[] buttons = entry.getValue();
            for (int i = buttons.length-1; i >=0  ; i--) {
                final Button button = buttons[i];
                final int buttonPos = i;
                final DialogInterface.OnClickListener callback = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int index) {
                        Elements element;
                        if (index < 0 || index >= Elements.values().length) {
                            element = null;
                        } else {
                            element = Elements.values()[index];
                        }
                        animalModels.get(animal).elements[buttonPos] = element;
                        button.setText(shortForm(element));
                        recalculateDominance();
                    }
                };
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        showElementChooser(callback);
                    }
                });
            }
        }

        relevantAnimals = EnumSet.noneOf(Animals.class);
        bindAnimalsButton(Animals.Mammals,    (Button) findViewById(R.string.button_mammals));
        bindAnimalsButton(Animals.Reptiles,   (Button) findViewById(R.string.button_reptiles));
        bindAnimalsButton(Animals.Birds,      (Button) findViewById(R.string.button_birds));
        bindAnimalsButton(Animals.Amphibians, (Button) findViewById(R.string.button_amphibians));
        bindAnimalsButton(Animals.Arachnids,  (Button) findViewById(R.string.button_arachnids));
        bindAnimalsButton(Animals.Insects,    (Button) findViewById(R.string.button_insects));
    }

    private void bindAnimalsButton(final Animals animal, Button viewById) {
        viewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (relevantAnimals.contains(animal)) {
                    relevantAnimals.remove(animal);
                    Button[] buttons = animalButtons.get(animal);
                    for (int i = 2; i < buttons.length; i++) {
                        buttons[i].setEnabled(false);
                    }
                } else {
                    relevantAnimals.add(animal);
                    Button[] buttons = animalButtons.get(animal);
                    for (int i = (Animals.Amphibians.equals(animal) ? 3 : 2); i < buttons.length; i++) {
                        buttons[i].setEnabled(true);
                    }
                }
                recalculateDominance();
            }
        });
    }

    private void recalculateDominance() {
        Animals dominantAnimal = null;
        int dominantScore = -1;
        Elements[] hexElements = animalModels.get(null).elements;
        for (Animals animal : Animals.values()) {
            int thisScore;
            if (relevantAnimals.contains(animal)) {
                thisScore = animalModels.get(animal).scoreDominance(hexElements);
                if (thisScore > dominantScore) {
                    dominantAnimal = animal;
                    dominantScore = thisScore;
                } else if (thisScore == dominantScore) {
                    dominantAnimal = null;
                    dominantScore = thisScore;
                }
            } else {
                thisScore = 0;
            }
            scores.get(animal).setText(Integer.toString(thisScore));
        }
        String resultText;
        if (relevantAnimals.isEmpty()) {
            resultText = "";
        } else if (dominantAnimal == null) {
            resultText = "No Dominant Animal - " + dominantScore;
        } else {
            resultText = dominantAnimal.name() + " dominates with a score of " + dominantScore;
        }
        ((TextView)findViewById(R.string.text_dominance)).setText(resultText);
    }

    public void showElementChooser(DialogInterface.OnClickListener callback) {
        final CharSequence[] items = {

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Pick a color");
        builder.setItems(R.array.element_popups, callback);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public int shortForm(Elements element) {
        if (element == null) {
            return R.string.empty_short;
        }
        switch (element) {
            case Meat:  return R.string.Meat_short;
            case Sun:   return R.string.Sun_short;
            case Seeds: return R.string.Seeds_short;
            case Water: return R.string.Water_short;
            case Grub:  return R.string.Grub_short;
            case Grass: return R.string.Grass_short;
            default:    return R.string.empty_short;
        }
    }
}