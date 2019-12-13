package io.github.htigroup4.pecs2life;

public class DataSetFire {

    private String name;
    private int imageResource;

    public DataSetFire() {
    }

    public DataSetFire(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
