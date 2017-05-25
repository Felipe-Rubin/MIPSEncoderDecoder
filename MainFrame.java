import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.filechooser.*;
import java.io.*;
//
import javax.print.DocFlavor.*;
//

public class MainFrame extends JFrame {
	
	private JTextArea asmArea;
	private JTextArea hexArea;
	
	private JScrollPane scrollAsm;
	private JScrollPane scrollHex;
	
	private JButton encodeButton;
	private JButton decodeButton;
	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem openASM;
	private MenuItem openTXT;
	private MenuItem saveASM;
	private MenuItem saveTXT;
	
	private JFrame labelMemoryFrame;
	private JPanel labelMemoryPanel;
	private JFrame instructionMemoryFrame;
	private JPanel instructionMemoryPanel;

	
	private Menu viewMenu;
	private MenuItem viewLabel;
	private MenuItem viewInstruction;

	
	public MainFrame(){
		initUI();
	}

	private void initUI(){
		//

		//
		JPanel panel = new JPanel(new BorderLayout());

		setTitle("MIPSEncoderDecoder");

		asmArea = new JTextArea(20,25);

		
		hexArea = new JTextArea(20,25);

		
		scrollAsm = new JScrollPane(asmArea);
		
		scrollHex = new JScrollPane(hexArea);
		
		scrollAsm.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollHex.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		encodeButton = new JButton("Encode");
		encodeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				hexArea.setText("");
				Parser p = new Parser(asmArea.getText());
				try{
					hexArea.append(p.parseASMString());
					//testando FTABLE
					Map<String,String> labelMap = p.getLabelMemory();

					String labels[][] = new String[labelMap.size()][2];
					int cont = 0;
					for(Map.Entry<String,String> k : labelMap.entrySet()){
						labels[cont][0] = k.getKey();
						labels[cont][1] = k.getValue();
						cont++;
					}

					labelMemoryPanel = new TablePanel(new FTable(labels,new String[]{"Label","Memory"}));

					//
					Map<String,String> textMap = p.getTextMemory();
					String instr[][] = new String[textMap.size()][2];
					cont = 0;
					for(Map.Entry<String,String> k : textMap.entrySet()){
						instr[cont][1] = k.getKey();
						instr[cont][0] = k.getValue();
						cont++;
					}					

					instructionMemoryPanel = new TablePanel(new FTable(instr,new String[]{"Instruction","Memory"}));

					//

					viewLabel.setEnabled(true);
					viewInstruction.setEnabled(true);
				}catch(Exception exc){
					viewLabel.setEnabled(false);
					viewInstruction.setEnabled(false);
					JOptionPane.showMessageDialog(null,"Please verify asm\n"+exc.getMessage(),"Failed to Encode",JOptionPane.ERROR_MESSAGE);
					System.out.println("Error! \n"+exc.getMessage());
				}
			}
		});
		decodeButton = new JButton("Decode");
		//decodeButton.setPreferredSize(new Dimension(50,50));


		decodeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				asmArea.setText("");
				Parser p = new Parser(hexArea.getText());
				try{
				//asmArea.append(p.parseCodeString());
					String decodedASM = p.parseCodeString();
					asmArea.append(decodedASM);

					Map<String,String> labelMap = p.getLabelMemory();
					String labels[][] = new String[labelMap.size()][2];
					int cont = 0;
					for(Map.Entry<String,String> k : labelMap.entrySet()){
						labels[cont][1] = k.getKey();
						labels[cont][0] = k.getValue();
						cont++;
					}
					labelMemoryPanel = new TablePanel(new FTable(labels,new String[]{"Label","Memory"}));			
					Map<String,String> textMap = p.getTextMemory();
					String instr[][] = new String[textMap.size()][2];
					cont = 0;
					for(Map.Entry<String,String> k : textMap.entrySet()){
						instr[cont][1] = k.getKey();
						instr[cont][0] = k.getValue();
						cont++;
					}					

					instructionMemoryPanel = new TablePanel(new FTable(instr,new String[]{"Instruction","Memory"}));
					viewLabel.setEnabled(true);
					viewInstruction.setEnabled(true);					
					//



					//


				}catch(Exception exc){
					viewLabel.setEnabled(false);
					viewInstruction.setEnabled(false);
					JOptionPane.showMessageDialog(null,"Please verify hex codes\n"+exc.getMessage(),"Failed to Decode",JOptionPane.ERROR_MESSAGE);
					System.out.println("Error! \n"+exc.getMessage());
				}
			}
		});		

		JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setLayout(new GridLayout(4,0,25,25));
		buttonPanel.add(new JPanel());
		buttonPanel.add(encodeButton);
		buttonPanel.add(decodeButton);
		buttonPanel.add(new JPanel());
		//buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));



		panel.add(scrollAsm,BorderLayout.LINE_START);
		panel.add(buttonPanel,BorderLayout.CENTER);
		

		panel.add(scrollHex,BorderLayout.LINE_END);
		//panel.add(scrollLog,BorderLayout.PAGE_END);

		//Menus
		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		openASM = new MenuItem("Open .asm");
		openASM.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"ASM Files", "asm");
        		chooser.setFileFilter(filter);
        		int returnVal = chooser.showOpenDialog(getParent());
        		if(returnVal == JFileChooser.APPROVE_OPTION){
        			File selectedFile = chooser.getSelectedFile();

        			asmArea.setText("");
        			try{
        				String line = "";
        			//
        			//

        				BufferedReader br = new BufferedReader(new FileReader(selectedFile));
        			while((line = br.readLine()) != null){
        				asmArea.append(line+"\n");
        			}
        			viewLabel.setEnabled(false);
        			viewInstruction.setEnabled(false);
        			}catch(Exception ex){
        				viewLabel.setEnabled(false);
        				viewInstruction.setEnabled(false);
						JOptionPane.showMessageDialog(null,"Please verify ASM File\n"+ex.getMessage(),"Failed to Open ASM",JOptionPane.ERROR_MESSAGE);
						System.out.println("Error! \n"+ex.getMessage());
        			}
        		}

			}
		});
		openTXT = new MenuItem("Open .txt");
		openTXT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"TXT Files", "txt");
        		chooser.setFileFilter(filter);
        		int returnVal = chooser.showOpenDialog(getParent());
        		if(returnVal == JFileChooser.APPROVE_OPTION){
        			File selectedFile = chooser.getSelectedFile();
        			hexArea.setText("");
        			try{
        				String line = "";
        				BufferedReader br = new BufferedReader(new FileReader(selectedFile));
        			while((line = br.readLine()) != null){
        				hexArea.append(line+"\n");
        			}
        			viewLabel.setEnabled(false);
        			viewInstruction.setEnabled(false);
        			}catch(Exception ex){
        				viewLabel.setEnabled(false);
        				viewInstruction.setEnabled(false);
						JOptionPane.showMessageDialog(null,"Please verify Code File\n"+ex.getMessage(),"Failed to Open Code File",JOptionPane.ERROR_MESSAGE);
						System.out.println("Error! \n"+ex.getMessage());
        				
        			}
        		}

			}
		});	

		saveASM = new MenuItem("Save .asm");
		saveASM.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(getParent());
				if(returnVal  == JFileChooser.APPROVE_OPTION){
					try{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					FileWriter fw = new FileWriter(filePath+".asm");
					fw.write(asmArea.getText());
					fw.close();
					}catch(Exception fwexc){
						System.out.println("Failed writing file");
						JOptionPane.showMessageDialog(null,"Could not save ASM File\n"+fwexc.getMessage(),"Failed to Save ASM File",JOptionPane.ERROR_MESSAGE);
						System.out.println(fwexc.getMessage());

					}
				}


			}
		});

		saveTXT = new MenuItem("Save .txt");
		saveTXT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(getParent());
				if(returnVal  == JFileChooser.APPROVE_OPTION){
					try{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					FileWriter fw = new FileWriter(filePath+".txt");
					fw.write(hexArea.getText());
					fw.close();
					}catch(Exception fwexc){
						JOptionPane.showMessageDialog(null,"Could not save Code File\n"+fwexc.getMessage(),"Failed to Save Code File",JOptionPane.ERROR_MESSAGE);
						System.out.println("Failed writing file");
						System.out.println(fwexc.getMessage());
					}
				}
			}
		});
		fileMenu.add(openASM);
		fileMenu.add(openTXT);
		fileMenu.add(saveASM);
		fileMenu.add(saveTXT);
		menuBar.add(fileMenu);


	
		// Testando Debugging
		labelMemoryPanel = new JPanel();
		labelMemoryFrame = new JFrame();
		labelMemoryFrame.setTitle("Label Memory");
		labelMemoryFrame.setSize(200,300);
		viewMenu = new Menu("View");
		viewLabel = new MenuItem("Label Memory");
		viewLabel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				labelMemoryFrame.setContentPane(labelMemoryPanel);
				labelMemoryFrame.setVisible(true);
				
			}
		});

		instructionMemoryFrame = new JFrame();
		instructionMemoryPanel = new JPanel();
		instructionMemoryFrame.setTitle("Instruction Memory");
		instructionMemoryFrame.setSize(200,300);
		viewInstruction = new MenuItem("Instruction Memory");
		viewInstruction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				instructionMemoryFrame.setContentPane(instructionMemoryPanel);
				instructionMemoryFrame.setVisible(true);
			}
		});


		viewMenu.add(viewLabel);
		viewMenu.add(viewInstruction);
		viewLabel.setEnabled(false);
		viewInstruction.setEnabled(false);
		menuBar.add(viewMenu);
		//
		setMenuBar(menuBar);

		setContentPane(panel);
		pack();
		//setResizable(false);
		setSize(800,500);
		setMinimumSize(new Dimension(800,500));

		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}

}