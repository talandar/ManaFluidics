package derpatiel.manafluidics.spell;

public class SpellParameterOptions {

    public final String name;
    public final String[] options;

    public SpellParameterOptions(String name, String... options){
        this.name=name;
        this.options=options;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  SpellParameterOptions){
            return ((SpellParameterOptions)obj).name.equals(this.name);
        }else if(obj instanceof SpellParameterChoices){
            return ((SpellParameterChoices)obj).options.name.equals(this.name);
        }
        return false;
    }

    @Override
    public String toString() {
        String ts = "SpellParameterOptions: "+name+", [";
        String sep = "";
        for(String option : options){
            ts = ts + sep + option;
            sep = ", ";
        }
        ts = ts + "]";
        return ts;
    }
}
