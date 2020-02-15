package org.vandeseer.easytable.bmd;

import lombok.Data;

@Data
public class RecCheckedItem {
    private String text;
    private boolean checked;

    public RecCheckedItem() {
    }

    public RecCheckedItem(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }
}

