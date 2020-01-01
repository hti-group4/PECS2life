/*
 * Copyright (C) of the original layout file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019-2020 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: use word "Card" instead of "Word".
 * Use two variables / entities (title and imageResource) instead of only one entity.
 * Removed unnecessary ColumnInfo qualifier.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.htigroup4.pecs2life;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 * <p>
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity(tableName = "card_table")
public class Card {

    @PrimaryKey
    @NonNull
    private String title;

    private int imageResource;

    private String id;

    public Card(@NonNull String title, int imageResource, String id) {
        this.title = title;
        this.imageResource = imageResource;
        this.id = id;
    }

    @NonNull
    String getTitle() {
        return title;
    }

    int getImageResource() {
        return imageResource;
    }

    public String getId() {
        return id;
    }
}
