

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
