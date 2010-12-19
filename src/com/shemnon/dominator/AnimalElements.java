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
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnimalElements extends Activity {

    Map<Animals, ImageButton[]> animalButtons;
    Map<Animals, AnimalModel> animalModels;
    Map<Animals, TextView> scores;
    Set<Animals> relevantAnimals;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_elements);
        animalButtons = new HashMap<Animals, ImageButton[]>();

        animalButtons.put(Animals.Mammals, new ImageButton[] {
                (ImageButton) findViewById(R.id.ME1),
                (ImageButton) findViewById(R.id.ME2),
                (ImageButton) findViewById(R.id.ME3),
                (ImageButton) findViewById(R.id.ME4),
                (ImageButton) findViewById(R.id.ME5),
                (ImageButton) findViewById(R.id.ME6),
        });
        animalButtons.put(Animals.Reptiles, new ImageButton[] {
                (ImageButton) findViewById(R.id.RE1),
                (ImageButton) findViewById(R.id.RE2),
                (ImageButton) findViewById(R.id.RE3),
                (ImageButton) findViewById(R.id.RE4),
                (ImageButton) findViewById(R.id.RE5),
                (ImageButton) findViewById(R.id.RE6),
        });
        animalButtons.put(Animals.Birds, new ImageButton[] {
                (ImageButton) findViewById(R.id.BE1),
                (ImageButton) findViewById(R.id.BE2),
                (ImageButton) findViewById(R.id.BE3),
                (ImageButton) findViewById(R.id.BE4),
                (ImageButton) findViewById(R.id.BE5),
                (ImageButton) findViewById(R.id.BE6),
        });
        animalButtons.put(Animals.Amphibians, new ImageButton[] {
                (ImageButton) findViewById(R.id.AE1),
                (ImageButton) findViewById(R.id.AE2),
                (ImageButton) findViewById(R.id.AE3),
                (ImageButton) findViewById(R.id.AE4),
                (ImageButton) findViewById(R.id.AE5),
                (ImageButton) findViewById(R.id.AE6),
        });
        animalButtons.put(Animals.Arachnids, new ImageButton[] {
                (ImageButton) findViewById(R.id.SE1),
                (ImageButton) findViewById(R.id.SE2),
                (ImageButton) findViewById(R.id.SE3),
                (ImageButton) findViewById(R.id.SE4),
                (ImageButton) findViewById(R.id.SE5),
                (ImageButton) findViewById(R.id.SE6),
        });
        animalButtons.put(Animals.Insects, new ImageButton[] {
                (ImageButton) findViewById(R.id.IE1),
                (ImageButton) findViewById(R.id.IE2),
                (ImageButton) findViewById(R.id.IE3),
                (ImageButton) findViewById(R.id.IE4),
                (ImageButton) findViewById(R.id.IE5),
                (ImageButton) findViewById(R.id.IE6),
        });
        animalButtons.put(null, new ImageButton[] {
                (ImageButton) findViewById(R.id.HE1),
                (ImageButton) findViewById(R.id.HE2),
                (ImageButton) findViewById(R.id.HE3),
                (ImageButton) findViewById(R.id.HE4),
                (ImageButton) findViewById(R.id.HE5),
                (ImageButton) findViewById(R.id.HE6),
        });

        scores = new HashMap<Animals, TextView>();
        scores.put(Animals.Mammals,    (TextView) findViewById(R.id.text_score_mammals));
        scores.put(Animals.Reptiles,   (TextView) findViewById(R.id.text_score_reptiles));
        scores.put(Animals.Birds,      (TextView) findViewById(R.id.text_score_birds));
        scores.put(Animals.Amphibians, (TextView) findViewById(R.id.text_score_amphibians));
        scores.put(Animals.Arachnids,  (TextView) findViewById(R.id.text_score_arachnids));
        scores.put(Animals.Insects,    (TextView) findViewById(R.id.text_score_insects));

        animalModels = new HashMap<Animals, AnimalModel>();
        animalModels.put(Animals.Mammals,    new AnimalModel(Animals.Mammals,    Elements.Meat,  Elements.Meat,  null, null, null, null));
        animalModels.put(Animals.Reptiles,   new AnimalModel(Animals.Reptiles,   Elements.Sun,   Elements.Sun,   null, null, null, null));
        animalModels.put(Animals.Birds,      new AnimalModel(Animals.Birds,      Elements.Seeds, Elements.Seeds, null, null, null, null));
        animalModels.put(Animals.Amphibians, new AnimalModel(Animals.Amphibians, Elements.Water, Elements.Water, Elements.Water, null, null, null));
        animalModels.put(Animals.Arachnids,  new AnimalModel(Animals.Arachnids,  Elements.Grub,  Elements.Grub,  null, null, null, null));
        animalModels.put(Animals.Insects,    new AnimalModel(Animals.Insects,    Elements.Grass, Elements.Grass, null, null, null, null));
        animalModels.put(null,               new AnimalModel(null,               null,           null,           null, null, null, null));

        relevantAnimals = EnumSet.noneOf(Animals.class);
        restoreFromPreferences();

        bindButtons();
    }

    private void restoreFromPreferences() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String s = preferences.getString("", null);
        if (s != null) {
            animalModels.get(null).read(s);
        }
        for (Animals animal : Animals.values()) {
            s = preferences.getString(animal.name(), null);
            if (s != null) {
                animalModels.get(animal).read(s);
            }
        }
        for (String animalString : preferences.getString("relevantAnimals", "").split(";")) {
            try {
                relevantAnimals.add(Animals.valueOf(animalString));
            } catch (IllegalArgumentException iae) {
                // safety bumper
            }
        }

        // bind to buttons
        bindButtonsFromModel(null);
        for (Animals animal : Animals.values()) {
            bindButtonsFromModel(animal);
        }

    }

    private void bindButtons() {
        for (Map.Entry<Animals, ImageButton[]> entry : animalButtons.entrySet()) {
            final Animals animal = entry.getKey();
            int switchPoint = Animals.Amphibians.equals(animal) ? 3 : (animal == null ? 0 : 2);

            ImageButton[] ImageButtons = entry.getValue();
            for (int i = ImageButtons.length-1; i >= switchPoint  ; i--) {
                final ImageButton ImageButton = ImageButtons[i];
                final int ImageButtonPos = i;
                final DialogInterface.OnClickListener callback = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int index) {
                        Elements element;
                        if (index < 0 || index >= Elements.values().length) {
                            element = null;
                        } else {
                            element = Elements.values()[index];
                        }
                        animalModels.get(animal).elements[ImageButtonPos] = element;
                        ImageButton.setImageResource(shortForm(element));
                        recalculateDominance();
                    }
                };
                ImageButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        showElementChooser(callback);
                    }
                });
            }
        }

        bindAnimalsButton(Animals.Mammals,    (ImageButton) findViewById(R.id.button_mammal));
        bindAnimalsButton(Animals.Reptiles,   (ImageButton) findViewById(R.id.button_reptile));
        bindAnimalsButton(Animals.Birds,      (ImageButton) findViewById(R.id.button_bird));
        bindAnimalsButton(Animals.Amphibians, (ImageButton) findViewById(R.id.button_amphibian));
        bindAnimalsButton(Animals.Arachnids,  (ImageButton) findViewById(R.id.button_arachnid));
        bindAnimalsButton(Animals.Insects, (ImageButton) findViewById(R.id.button_insect));
    }

    private void bindAnimalsButton(final Animals animal, ImageButton viewById) {
        viewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (relevantAnimals.contains(animal)) {
                    relevantAnimals.remove(animal);
                } else {
                    relevantAnimals.add(animal);
                    bindButtonsFromModel(animal);
                }
                recalculateDominance();
            }
        });
    }

    private void bindButtonsFromModel(Animals animal) {
        int switchPoint = Animals.Amphibians.equals(animal) ? 3 : 2;
        ImageButton[] imageButtons = animalButtons.get(animal);
        if (animal == null || relevantAnimals.contains(animal)) {
            AnimalModel model = animalModels.get(animal);
            for (int i = 0; i < imageButtons.length; i++) {
                imageButtons[i].setImageResource(shortForm(model.elements[i]));
            }
            for (int i = switchPoint; i < imageButtons.length; i++) {
                imageButtons[i].setEnabled(true);
            }
        } else {
            for (int i = 0; i < imageButtons.length; i++) {
                imageButtons[i].setImageResource(R.drawable.none);
            }
            for (int i = switchPoint; i < imageButtons.length; i++) {
                imageButtons[i].setEnabled(false);
            }
        }
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
        ((TextView)findViewById(R.id.text_dominance)).setText(resultText);
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
            return R.drawable.empty;
        }
        switch (element) {
            case Meat:  return R.drawable.meat;
            case Sun:   return R.drawable.sun;
            case Seeds: return R.drawable.seed;
            case Water: return R.drawable.water;
            case Grub:  return R.drawable.grub;
            case Grass: return R.drawable.grass;
            default:    return R.drawable.empty;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences storage = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        for (Map.Entry<Animals, AnimalModel> entry : animalModels.entrySet()) {
            String name = (entry.getKey() == null) ? "" : entry.getKey().name();
            editor.putString(name, entry.getValue().write());
        }
        StringBuilder sb = new StringBuilder();
        for (Animals relevantAnimal : relevantAnimals) {
            sb.append(relevantAnimal.name());
            sb.append(";");
        }
        editor.putString("relevantAnimals", sb.toString());
        editor.commit();
    }
}