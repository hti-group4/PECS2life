/*
 * Copyright (C) of the original layout file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: removed the "info" String variable. Renamed the class name from Sport to PECSCard.
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

package io.github.htigroup4.pecs2life;

/**
 * Data model for each row of the RecyclerView.
 */
class PECSCard {

    // Member variables representing the title and information about the PECSCard.
    private String title;
    private final int imageResource;

    /**
     * Constructor for the PECSCard data model.
     *
     * @param title The name if the PECSCard.
     */
    PECSCard(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    /**
     * Gets the title of the PECSCard.
     *
     * @return The title of the PECSCard.
     */
    String getTitle() {
        return title;
    }


    int getImageResource() {
        return imageResource;
    }

}
