package derpatiel.manafluidics.capability.heat;

public class HeatProvider implements IHeatHandler {

    private int heatProvided;

    private boolean heatProvidedThisTick=false;

    public HeatProvider(int heatProvided){
        this.heatProvided=heatProvided;
        heatProvidedThisTick=false;
    }
    public HeatProvider(){
        this.heatProvided=0;
        heatProvidedThisTick=false;
    }

    @Override
    public int getHeatProvided() {
        return heatProvided;
    }

    @Override
    public int consumeHeat() {
        if(!heatProvidedThisTick) {
            heatProvidedThisTick = true;
            return heatProvided;
        }else{
            return 0;
        }
    }

    @Override
    public void generateHeat() {
        heatProvidedThisTick=false;
    }

    @Override
    public void setHeatProvided(int provided){
        this.heatProvided=provided;
    }
}
