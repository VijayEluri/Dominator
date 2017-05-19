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

internal class AnimalModel(theElements: Array<Elements>) {
    var elements: Array<Elements>

    init {
        elements = theElements
    }

    fun scoreDominance(tileElements: Array<Elements>): Int {
        var count = 0
        for (myElement in elements) {
            tileElements
                    .filter { myElement != Elements.Empty}
                    .filter { myElement == it }
                    .forEach { count++ }
        }
        return count
    }

    fun write(): String {
        val sb = StringBuilder()
        for (element in elements) {
            sb.append(element.name)
            sb.append(";")
        }
        return sb.toString()
    }

    fun read(elementsAsString: String) {
        val strings = elementsAsString.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val newE = arrayOf<Elements>()
        for (i in strings.indices) {
            var e: Elements
            try {
                e = Elements.valueOf(strings[i])
            } catch (iae: IllegalArgumentException) {
                e = Elements.Empty
            }
            newE[i] = e
        }
        elements = newE
    }

}
