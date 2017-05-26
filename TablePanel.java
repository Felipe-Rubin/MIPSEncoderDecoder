/*
	Autor: Felipe Pfeifer Rubin
	Matricula: 151050853
	Email: felipe.rubin@acad.pucrs.br

	Deve ser utilizado juntamente com a classe FTable, responsavel
	pela utilizacao da tabela junto aos campos de busca
*/
import javax.swing.JPanel;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComponent;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.RowFilter;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
public class TablePanel extends JPanel{
	private JPanel filterGroups[];
	private JPanel filters;
	private JCheckBox sensitive[];
	private JTable table;
	private JTextField queryFields[];
	private List<RowFilter<Object,Object>> sortList;
	/*
		Cria a partir dos dados
	*/
	public TablePanel(Object[][] rows, String columns[]){
		table = new FTable(rows,columns);
		table.getTableHeader().setReorderingAllowed(false);
		sortList = new ArrayList<RowFilter<Object,Object>>();
		initUI();
	}
	/*
		Encapsula uma tabela ja existente
	*/
	public TablePanel(JTable table){
		this.table = table;
		table.getTableHeader().setReorderingAllowed(false);
		sortList = new ArrayList<RowFilter<Object,Object>>();
		initUI();
	}
	@SuppressWarnings("unchecked")
	/*
		Percebe que a busca do usuario mudou
	*/
	public void queryChanged(int index){
		String text = queryFields[index].getText();
		if(sensitive[index].isSelected())
			sortList.set(index,RowFilter.regexFilter(text,index));
		else
			sortList.set(index,RowFilter.regexFilter("(?i)"+text,index));
		
		((TableRowSorter<TableModel>)table.getRowSorter()).setRowFilter(RowFilter.andFilter(sortList));
	}
	/*
		Inicia os listeners
	*/
	public void initListeners(JComponent component,int index){
		if(component instanceof JTextField){
			((JTextField)component).getDocument().addDocumentListener(new DocumentListener(){
				final int i = index;
				public void changedUpdate(DocumentEvent e){
					queryChanged(i);
				}
				public void removeUpdate(DocumentEvent e){
					queryChanged(i);
				}
				public void insertUpdate(DocumentEvent e){
					queryChanged(i);
				}

			});
		}else if(component instanceof JCheckBox){
			((JCheckBox)component).addItemListener(new ItemListener(){
				final int i = index;
				public void itemStateChanged(ItemEvent e){
					//Trigger change on JTextField
					queryChanged(i);
				}
			});
		}
	}

	/*
		Faz a configuracao
	*/
	public void initUI(){
		queryFields = new JTextField[table.getColumnCount()];
		sensitive = new JCheckBox[table.getColumnCount()];
		filterGroups = new JPanel[table.getColumnCount()];
		filters = new JPanel();
		filters.setLayout(new BoxLayout(filters,BoxLayout.X_AXIS));
		//filters = new JPanel(new BoxLayout(filters,BoxLayout.X_AXIS));
		for(int i = 0; i < table.getColumnCount();i++){
			queryFields[i] = new JTextField();
			sensitive[i] = new JCheckBox();
			sortList.add(RowFilter.regexFilter(queryFields[i].getText(),i));

			initListeners(queryFields[i],i);
			initListeners(sensitive[i],i);
			//filterGroups[i] = new JPanel(/*new FlowLayout()*/);
			//filterGroups[i].add(sensitive[i]);
			//filterGroups[i].add(queryFields[i]);
			//filters.add(filterGroups[i]);
			filters.add(sensitive[i]);			
			filters.add(queryFields[i]);


		}

		setLayout(new BorderLayout());
		JScrollPane scrollpane = new JScrollPane(table);
		add(filters,BorderLayout.NORTH);
		add(scrollpane,BorderLayout.CENTER);

	}


}