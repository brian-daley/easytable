package org.vandeseer.easytable.bmd;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Recommendation
 */
@Data
public class RecommendationData {
    private String imagePath;  // TODO: Need a classpath lookup
    private String title;
    private String subTitle;
    private List<RecCheckedItem> items = new ArrayList<>();

    public void addItem(String text, boolean checked) {
        items.add(new RecCheckedItem(text, checked));
    }

}

