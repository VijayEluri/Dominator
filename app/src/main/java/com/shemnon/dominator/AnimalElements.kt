/*
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

package com.shemnon.dominator

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

import java.util.*

class AnimalElements : Activity() {

    internal var animalButtons: MutableMap<Animals, Array<ImageButton>> = HashMap()
    internal var animalModels: MutableMap<Animals, AnimalModel> = HashMap()
    internal var scores: MutableMap<Animals, TextView> = HashMap()
    internal var relevantAnimals: MutableSet<Animals> = HashSet()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animal_elements)

        animalButtons.put(Animals.Mammals, arrayOf(findViewById(R.id.ME1) as ImageButton, findViewById(R.id.ME2) as ImageButton, findViewById(R.id.ME3) as ImageButton, findViewById(R.id.ME4) as ImageButton, findViewById(R.id.ME5) as ImageButton, findViewById(R.id.ME6) as ImageButton))
        animalButtons.put(Animals.Reptiles, arrayOf(findViewById(R.id.RE1) as ImageButton, findViewById(R.id.RE2) as ImageButton, findViewById(R.id.RE3) as ImageButton, findViewById(R.id.RE4) as ImageButton, findViewById(R.id.RE5) as ImageButton, findViewById(R.id.RE6) as ImageButton))
        animalButtons.put(Animals.Birds, arrayOf(findViewById(R.id.BE1) as ImageButton, findViewById(R.id.BE2) as ImageButton, findViewById(R.id.BE3) as ImageButton, findViewById(R.id.BE4) as ImageButton, findViewById(R.id.BE5) as ImageButton, findViewById(R.id.BE6) as ImageButton))
        animalButtons.put(Animals.Amphibians, arrayOf(findViewById(R.id.AE1) as ImageButton, findViewById(R.id.AE2) as ImageButton, findViewById(R.id.AE3) as ImageButton, findViewById(R.id.AE4) as ImageButton, findViewById(R.id.AE5) as ImageButton, findViewById(R.id.AE6) as ImageButton))
        animalButtons.put(Animals.Arachnids, arrayOf(findViewById(R.id.SE1) as ImageButton, findViewById(R.id.SE2) as ImageButton, findViewById(R.id.SE3) as ImageButton, findViewById(R.id.SE4) as ImageButton, findViewById(R.id.SE5) as ImageButton, findViewById(R.id.SE6) as ImageButton))
        animalButtons.put(Animals.Insects, arrayOf(findViewById(R.id.IE1) as ImageButton, findViewById(R.id.IE2) as ImageButton, findViewById(R.id.IE3) as ImageButton, findViewById(R.id.IE4) as ImageButton, findViewById(R.id.IE5) as ImageButton, findViewById(R.id.IE6) as ImageButton))
        animalButtons.put(Animals.None, arrayOf(findViewById(R.id.HE1) as ImageButton, findViewById(R.id.HE2) as ImageButton, findViewById(R.id.HE3) as ImageButton, findViewById(R.id.HE4) as ImageButton, findViewById(R.id.HE5) as ImageButton, findViewById(R.id.HE6) as ImageButton))

        scores = HashMap<Animals, TextView>()
        scores.put(Animals.Mammals, findViewById(R.id.text_score_mammals) as TextView)
        scores.put(Animals.Reptiles, findViewById(R.id.text_score_reptiles) as TextView)
        scores.put(Animals.Birds, findViewById(R.id.text_score_birds) as TextView)
        scores.put(Animals.Amphibians, findViewById(R.id.text_score_amphibians) as TextView)
        scores.put(Animals.Arachnids, findViewById(R.id.text_score_arachnids) as TextView)
        scores.put(Animals.Insects, findViewById(R.id.text_score_insects) as TextView)

        animalModels.put(Animals.Mammals, AnimalModel(arrayOf(Elements.Meat, Elements.Meat, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty)))
        animalModels.put(Animals.Reptiles, AnimalModel(arrayOf(Elements.Sun, Elements.Sun, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty)))
        animalModels.put(Animals.Birds, AnimalModel(arrayOf(Elements.Seeds, Elements.Seeds, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty)))
        animalModels.put(Animals.Amphibians, AnimalModel(arrayOf(Elements.Water, Elements.Water, Elements.Water, Elements.Empty, Elements.Empty, Elements.Empty)))
        animalModels.put(Animals.Arachnids, AnimalModel(arrayOf(Elements.Grub, Elements.Grub, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty)))
        animalModels.put(Animals.Insects, AnimalModel(arrayOf(Elements.Grass, Elements.Grass, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty)))
        animalModels.put(Animals.None, AnimalModel(arrayOf(Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty, Elements.Empty)))

        restoreFromPreferences()

        bindButtons()
    }

    private fun restoreFromPreferences() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        var s = preferences.getString("", null)
        if (s != null) {
            animalModels[Animals.None]?.read(s)
        }
        for (animal in Animals.values()) {
            s = preferences.getString(animal.name, null)
            if (s != null) {
                animalModels[animal]?.read(s)
            }
        }
        for (animalString in preferences.getString("relevantAnimals", "")!!.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()) {
            try {
                relevantAnimals.add(Animals.valueOf(animalString))
            } catch (iae: IllegalArgumentException) {
                // safety bumper
            }

        }

        // bind to buttons
        bindButtonsFromModel(Animals.None)
        for (animal in Animals.values()) {
            bindButtonsFromModel(animal)
        }

        recalculateDominance()
    }

    private fun bindButtons() {
        for ((animal, ImageButtons) in animalButtons) {
            val switchPoint = if (Animals.Amphibians == animal) 3 else if (Animals.None == animal) 0 else 2

            for (i in ImageButtons.size - 1 downTo switchPoint) {
                val ImageButton = ImageButtons[i]
                val ImageButtonPos = i
                val callback = DialogInterface.OnClickListener { _, index ->
                    val element: Elements
                    if (index < 0 || index >= Elements.values().size) {
                        element = Elements.Empty
                    } else {
                        element = Elements.values()[index]
                    }
                    animalModels[animal]!!.elements[ImageButtonPos] = element
                    ImageButton.setImageResource(imageForElement(element))
                    recalculateDominance()
                }
                ImageButton.setOnClickListener { showElementChooser(callback) }
            }
        }

        bindAnimalsButton(Animals.Mammals, findViewById(R.id.button_mammal) as ImageButton)
        bindAnimalsButton(Animals.Reptiles, findViewById(R.id.button_reptile) as ImageButton)
        bindAnimalsButton(Animals.Birds, findViewById(R.id.button_bird) as ImageButton)
        bindAnimalsButton(Animals.Amphibians, findViewById(R.id.button_amphibian) as ImageButton)
        bindAnimalsButton(Animals.Arachnids, findViewById(R.id.button_arachnid) as ImageButton)
        bindAnimalsButton(Animals.Insects, findViewById(R.id.button_insect) as ImageButton)
    }

    private fun bindAnimalsButton(animal: Animals, viewById: ImageButton) {
        viewById.setOnClickListener {
            if (relevantAnimals.contains(animal)) {
                relevantAnimals.remove(animal)
            } else {
                relevantAnimals.add(animal)
            }
            bindButtonsFromModel(animal)
            recalculateDominance()
        }
    }

    private fun bindButtonsFromModel(animal: Animals) {
        val switchPoint = if (Animals.Amphibians == animal) 3 else 2
        val imageButtons = animalButtons[animal]
        if (animal == Animals.None || relevantAnimals.contains(animal)) {
            val model = animalModels[animal]
            for (i in imageButtons!!.indices) {
                imageButtons[i].setImageResource(imageForElement(model!!.elements[i]))
            }
            for (i in switchPoint..imageButtons.size - 1) {
                imageButtons[i].isEnabled = true
            }
        } else {
            for (imageButton in imageButtons!!) {
                imageButton.setImageResource(R.drawable.none)
            }
            for (i in switchPoint..imageButtons.size - 1) {
                imageButtons[i].isEnabled = false
            }
        }
    }

    private fun recalculateDominance() {
        var dominantAnimal: Animals? = null
        var dominantScore = -1
        val hexElements = animalModels[Animals.None]!!.elements
        for (animal in Animals.values()) {
            val thisScore: Int
            if (relevantAnimals.contains(animal)) {
                thisScore = animalModels[animal]!!.scoreDominance(hexElements)
                if (thisScore > dominantScore) {
                    dominantAnimal = animal
                    dominantScore = thisScore
                } else if (thisScore == dominantScore) {
                    dominantAnimal = null
                    dominantScore = thisScore
                }
            } else {
                thisScore = 0
            }
            val scoreString = Integer.toString(thisScore)
            scores[animal]?.text = scoreString
        }
        val resultText: String
        if (relevantAnimals.isEmpty()) {
            resultText = ""
        } else if (dominantAnimal == null) {
            resultText = "No Dominant Animal - " + dominantScore
        } else {
            resultText = dominantAnimal.name + " dominates with a score of " + dominantScore
        }
        (findViewById(R.id.text_dominance) as TextView).text = resultText
    }

    fun showElementChooser(callback: DialogInterface.OnClickListener) {

        val builder = AlertDialog.Builder(this)
        val listElements = arrayOf(Elements.Meat, Elements.Sun, Elements.Seeds, Elements.Water, Elements.Grub, Elements.Grass, null)
        builder.setAdapter(ElementPopupListAdapter(this, listElements), callback)
                .setTitle("Select Element")
        val alert = builder.create()
        alert.show()
    }

    override fun onPause() {
        super.onPause()
        val storage = getPreferences(Context.MODE_PRIVATE)
        val editor = storage.edit()
        for ((key, value) in animalModels) {
            val name = key.name
            editor.putString(name, value.write())
        }
        val sb = StringBuilder()
        for (relevantAnimal in relevantAnimals) {
            sb.append(relevantAnimal.name)
            sb.append(";")
        }
        editor.putString("relevantAnimals", sb.toString())
        editor.apply()
    }

    companion object {

        internal fun imageForElement(element: Elements?): Int {
            if (element == null) {
                return R.drawable.empty
            }
            when (element) {
                Elements.Meat -> return R.drawable.meat
                Elements.Sun -> return R.drawable.sun
                Elements.Seeds -> return R.drawable.seed
                Elements.Water -> return R.drawable.water
                Elements.Grub -> return R.drawable.grub
                Elements.Grass -> return R.drawable.grass
                else -> return R.drawable.empty
            }
        }
    }
}