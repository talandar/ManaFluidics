package derpatiel.manafluidics.spell;

public class SpellParameterChoices {

    public final SpellParameterOptions options;
    public final String selectedOption;

    public SpellParameterChoices(SpellParameterOptions options, String selectedOption){
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
