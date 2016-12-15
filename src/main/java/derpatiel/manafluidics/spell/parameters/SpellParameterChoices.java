package derpatiel.manafluidics.spell.parameters;

public class SpellParameterChoices {

    public final SpellParameter options;
    public final int selectedOption;

    public SpellParameterChoices(SpellParameter options, int selectedOption){
        this.options=options;
        this.selectedOption=selectedOption;
    }

    @Override
    public int hashCode() {
        return options.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return options.equals(obj);
    }

    @Override
    public String toString() {
        return options.toString()+ " SELECTED: "+selectedOption;
    }
}
