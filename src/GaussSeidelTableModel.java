import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class GaussSeidelTableModel extends AbstractTableModel {
    private final List<GaussSeidelAnswer> data = new ArrayList<>();
    private int variablesCount;

    public GaussSeidelTableModel(int variablesCount) {
        this.variablesCount = variablesCount;
    }

    public void setVariablesCount(int count) {
        this.variablesCount = count;
    }

    @Override
    public int getColumnCount() {
        return variablesCount + 2; // Iteration, x-values, Error
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) return "n";
        else if (column == getColumnCount() - 1) return "Ea(%)";
        else return "x" + column;
    }

    @Override
    public Object getValueAt(int row, int col) {
        GaussSeidelAnswer answer = data.get(row);
        if (col == 0) return answer.getIteration();
        else if (col == getColumnCount() - 1) return String.format("%.6f", answer.getError());
        else return String.format("%.6f", answer.getValues()[col - 1]);
    }

    public void addRow(GaussSeidelAnswer answer) {
        data.add(answer);
        fireTableDataChanged();
    }

    public void clear() {
        data.clear();
        fireTableDataChanged();
    }
}
