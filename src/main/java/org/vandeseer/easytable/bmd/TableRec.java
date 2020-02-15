package org.vandeseer.easytable.bmd;

import org.vandeseer.easytable.structure.Table;

public class TableRec {

    private Table table;
    private String title;

    public TableRec(Table table, String title) {
        this.table = table;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Table getTable() {
        return table;
    }

    public String getTitle() {
        return title;
    }
}

