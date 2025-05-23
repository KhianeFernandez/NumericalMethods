import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SimpsonsAbstractTableModel extends AbstractTableModel {

    ArrayList<SimpsonsAnswer> simpsonsAnswers;

    public SimpsonsAbstractTableModel(){
        simpsonsAnswers = new ArrayList<>();
    }
    String []columns = {"", "x", "f(x)", "mod 2", "1/3 rule", "f(xi)"};



    @Override
    public int getRowCount() {
        return simpsonsAnswers.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int index){
        return columns[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        SimpsonsAnswer simpsonsAnswer = simpsonsAnswers.get(rowIndex);

        int counter = 0;

        if(columnIndex == 0){
            counter++;
            return STR."x\{counter}";

        }else if (columnIndex == 1){
            return simpsonsAnswer.getX();
        }else if (columnIndex == 2){
            return simpsonsAnswer.getFx();
        }else if (columnIndex == 3){
            return simpsonsAnswer.getMod2();
        }else if (columnIndex == 4){
            return simpsonsAnswer.getRule();
        }else if (columnIndex == 5){
            return simpsonsAnswer.getFxi();
        }else{
            return null;
        }

    }
}
