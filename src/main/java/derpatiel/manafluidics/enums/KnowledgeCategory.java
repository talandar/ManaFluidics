package derpatiel.manafluidics.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * Created by Jim on 11/7/2016.
 */
public enum KnowledgeCategory {
    ALTAR_CRAFTED,
    WAND_CRAFTED;

    public static final KnowledgeCategory[] VALUES;


    static{
        KnowledgeCategory[] vals = values();
        VALUES = new KnowledgeCategory[vals.length];
        for(KnowledgeCategory m : vals){
            VALUES[m.ordinal()]=m;
        }
    }



    public static Map<KnowledgeCategory,Boolean> getDefaultKnowledgeMap(){
        Map<KnowledgeCategory,Boolean> map = new HashMap<>();
        for(KnowledgeCategory cat : VALUES){
            map.put(cat,false);
        }
        return map;
    }

}
