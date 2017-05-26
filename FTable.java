/*
	Autor: Felipe Pfeifer Rubin
	Matricula: 151050853
	Email: felipe.rubin@acad.pucrs.br

	Tabela utilizada na interface grafica
*/
import javax.swing.event.TableColumnModelEvent;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.RowFilter;
public class FTable extends JTable{

	private TableRowSorter<TableModel> sorter;
	private List<RowFilter<Object,Object>> sortList;

	/*
		Modelo interno que nao permite editar a celula
	*/
	private class ModdedTable extends DefaultTableModel{
		public ModdedTable(Object[][] rows, String[] columns){
			super(rows,columns);
		}
		
		@Override
		public boolean isCellEditable(int row, int column){
			return false;
		}
	}

	/*
		Recebe os dados e cria com o modelo interno
	*/
	public FTable(Object[][] rows, String columns[]){
			

		setModel(new ModdedTable(rows,columns));
		sorter = new TableRowSorter<>(getModel());
		setRowSorter(sorter);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		setGridColor(Color.BLACK);
		setFillsViewportHeight(true);
		sortList = new ArrayList<RowFilter<Object,Object>>();
	}
	/*
		Recebe um modelo
	*/
	public FTable(TableModel model){

		setModel(model);
		sorter = new TableRowSorter<>(getModel());
		setRowSorter(sorter);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		setGridColor(Color.BLACK);
		setFillsViewportHeight(true);
		sortList = new ArrayList<RowFilter<Object,Object>>();		
	}

	/*
		adiciona elem na lista 
	*/
	public void addToSortList(RowFilter<Object,Object> row){
		sortList.add(row);
	}
	/*
		troca a lista 
	*/
	public void setSortList(List<RowFilter<Object,Object>> sortList){
		this.sortList = sortList;
	}

	public List<RowFilter<Object,Object>> setSortList(){
		return sortList;
	}

	//OBS:
	//A posicao e a coluna devem ser a mesma

	public void updateSortList(int column, String text){
		sortList.set(column,RowFilter.regexFilter(text,column));

		sorter.setRowFilter(RowFilter.andFilter(sortList));
	}

}