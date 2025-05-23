
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class GaussSeidelTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Iteration", "x₁", "x₂", "x₃", "Ea₁", "Ea₂", "Ea₃"};
    private final List<GaussSeidelAnswer> answers = new ArrayList<>();
    private int decimalPlaces = 3;

    public void addAnswer(GaussSeidelAnswer answer) {
        answers.add(answer);
        fireTableRowsInserted(answers.size() - 1, answers.size() - 1);
    }

    public void clearAnswers() {
        answers.clear();
        fireTableDataChanged();
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public int getRowCount() {
        return answers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GaussSeidelAnswer answer = answers.get(rowIndex);
        switch (columnIndex) {
            case 0: return answer.getIteration();
            case 1: return String.format("%." + decimalPlaces + "f", answer.getX()[0]);
            case 2: return String.format("%." + decimalPlaces + "f", answer.getX()[1]);
            case 3: return String.format("%." + decimalPlaces + "f", answer.getX()[2]);
            case 4: return String.format("%." + decimalPlaces + "f", answer.getEa()[0]);
            case 5: return String.format("%." + decimalPlaces + "f", answer.getEa()[1]);
            case 6: return String.format("%." + decimalPlaces + "f", answer.getEa()[2]);
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}