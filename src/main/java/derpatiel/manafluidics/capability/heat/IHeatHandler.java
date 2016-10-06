package derpatiel.manafluidics.capability.heat;

public interface IHeatHandler {

    public void setHeatProvided(int provided);

    public int getHeatProvided();

    public int consumeHeat();

    public void generateHeat();
}
