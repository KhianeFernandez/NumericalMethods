import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SecantAbstractTableModel extends AbstractTableModel {
    List<SecantAnswer> SecantAnswers;
    private final String[] columnNames = {"n", "x₀", "x₁", "f(x₀)", "f(x₁)", "x₂", "Ea(%)"};

    public SecantAbstractTableModel() {
        SecantAnswers = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return SecantAnswers.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SecantAnswer answer = SecantAnswers.get(rowIndex);
        switch (columnIndex) {
            case 0: return answer.getN();
            case 1: return answer.getX0();
            case 2: return answer.getX1();
            case 3: return answer.getFx0();
            case 4: return answer.getFx1();
            case 5: return answer.getX2();
            case 6: return answer.getEa();
            default: return null;
        }
    }

    public void addIterativeAnswer(SecantAnswer answer) {
        SecantAnswers.add(answer);
        fireTableDataChanged();
    }
}
