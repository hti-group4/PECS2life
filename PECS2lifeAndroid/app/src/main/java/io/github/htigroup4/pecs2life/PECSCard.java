

package io.github.htigroup4.pecs2life;

/**
 * Data model for each row of the RecyclerView.
 */
class PECSCard {

    // Member variables representing the title and information about the PECSCard.
    private String title;
    private String info;
    private final int imageResource;

    /**
     * Constructor for the PECSCard data model.
     *
     * @param title The name if the PECSCard.
     * @param info  Information about the PECSCard.
     */
    PECSCard(String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
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

    /**
     * Gets the info about the PECSCard.
     *
     * @return The info about the PECSCard.
     */
    String getInfo() {
        return info;
    }

    int getImageResource() {
        return imageResource;
    }

}
