package view.editors;

public interface DataEditor {

    /**
     * Remove the selected row from the data editor table.
     */
    void removeSelectedRow();

    /**
     * Add a new row to the data editor table.
     */
    void addNewRow();

    /**
     * Move the selected row up in the data editor table.
     */
    void moveRowUp();

    /**
     * Move the selected row down in the data editor table.
     */
    void moveRowDown();

}
