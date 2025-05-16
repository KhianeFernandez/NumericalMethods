
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BisectionAbstractTableModel extends AbstractTableModel {

    ArrayList<BisectionAnswer> BisectionAnswers;

    String []Columns = {"Iteration" , "X0" , "X1" , "X2", "fX2", "Ea"};

    public BisectionAbstractTableModel(){
        BisectionAnswers = new ArrayList<>();
    }

    public void addIterativeAnswer(BisectionAnswer Iterative){
        BisectionAnswers.add(Iterative);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return BisectionAnswers.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.length;
    }

    @Override
    public String getColumnName(int index){
        return Columns[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        BisectionAnswer Answers = BisectionAnswers.get(rowIndex);

        if(columnIndex == 0){
            return Answers.getN();
        }else if(columnIndex == 1){
            return Answers.getX0();
        }else if(columnIndex == 2){
            return Answers.getX1();
        }else if(columnIndex == 3){
            return Answers.getX2();
        }else if(columnIndex == 4){
            return Answers.getFx2();
        }else if(columnIndex == 5){
            return Answers.getEa();
        }else{
            return "";
        }

    }
}
