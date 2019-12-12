package io.github.htigroup4.pecs2life;

public class DataSetFire {

    private String teamone;
    private String teamtwo;

    public DataSetFire() {
    }

    public DataSetFire(String teamone, String teamtwo) {
        this.teamone = teamone;
        this.teamtwo = teamtwo;
    }

    public String getTeamone() {
        return teamone;
    }

    public void setTeamone(String teamone) {
        this.teamone = teamone;
    }

    public String getTeamtwo() {
        return teamtwo;
    }

    public void setTeamtwo(String teamtwo) {
        this.teamtwo = teamtwo;
    }
}
