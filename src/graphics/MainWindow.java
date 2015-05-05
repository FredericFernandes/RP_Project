package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import loader.LoaderSimulation;

import simul.Simulation;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JRadioButton arrondiMethod;
	private JRadioButton exactMethod;
	private JRadioButton geneticsMethod;
	private final String aMString = "Methode par arrondie";
	private final String eMString = "Methode exacte";
	private final String gMString = "Methode génétique";
	private String pathInstance= null;
	private Simulation simul =null;
	private JCheckBox checkRoad;
	private JCheckBox checkFLP;
	private JFormattedTextField txtNbP;
	private JFormattedTextField txtNbPopulation;
	private JFormattedTextField txtNbIteration;
	JButton btnChooseFiler;
	JButton btnStartSimulation;
	JLabel lblFile;
	public MainWindow(String name)
	{
		super(name);
	}

	public void addComponentsToPane(final Container pane) {
		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout());

		exactMethod = new JRadioButton(eMString);
		exactMethod.setActionCommand(eMString);
		exactMethod.setSelected(true);

		arrondiMethod = new JRadioButton(aMString);
		arrondiMethod.setActionCommand(aMString);
		arrondiMethod.setEnabled(false);

		geneticsMethod = new JRadioButton(gMString);
		geneticsMethod.setActionCommand(gMString);

		//Add controls to set up the component orientation in the experiment layout
		final ButtonGroup group = new ButtonGroup();
		group.add(exactMethod);
		group.add(arrondiMethod);
		group.add(geneticsMethod);

		checkRoad = new JCheckBox("Afficher tous les arcs ? ");
		checkFLP = new JCheckBox("Activer le mode p-Median ?");
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		// If you want the value to be committed on each keystroke instead of focus lost
		formatter.setCommitsOnValidEdit(true);
		txtNbP = new JFormattedTextField(formatter);
		txtNbP.setColumns(4);
		txtNbP.setText("");
		txtNbP.setEditable(false);

		JLabel lblnbP = new JLabel("Nombre de population :");
		txtNbPopulation = new JFormattedTextField(formatter);
		txtNbPopulation.setColumns(4);
		txtNbPopulation.setText("");
		txtNbPopulation.setEditable(false);
		
		JLabel lblnbIter = new JLabel(" Nombre d'itération :");
		txtNbIteration = new JFormattedTextField(formatter);
		txtNbIteration.setColumns(4);
		txtNbIteration.setText("");
		txtNbIteration.setEditable(false);
		
		btnChooseFiler = new JButton("Choisir un fichier ");
		btnStartSimulation = new JButton("Lancer la simulation ");
		
		lblFile = new JLabel();
		controls.add(exactMethod);
		controls.add(arrondiMethod);
		controls.add(geneticsMethod);
		
		controls.add(lblnbP);
		controls.add(txtNbPopulation);
		
		controls.add(lblnbIter);
		controls.add(txtNbIteration);
		
		controls.add(checkRoad);
		controls.add(checkFLP);
		controls.add(txtNbP);
		controls.add(btnChooseFiler);
		btnStartSimulation.setEnabled(false);
		controls.add(btnStartSimulation);
		controls.add(lblFile);


		btnChooseFiler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser("./Instances utilisées/");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
				fileChooser.setFileFilter(new FileNameExtensionFilter("Instance Files", "flp"));
				pathInstance=null;
				File file=null;
				if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					pathInstance = file.getAbsolutePath();
				}
				if(pathInstance!=null){
					btnStartSimulation.setEnabled(true);
					lblFile.setText(file.getName());
				}else{
					btnStartSimulation.setEnabled(false);
					lblFile.setText("");
				}
			}
		});

		btnStartSimulation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String command = group.getSelection().getActionCommand();
				int p =-1;
				if(txtNbP.getText().isEmpty()){
					txtNbP.setEditable(false);
					checkFLP.setSelected(false);
					pane.repaint();
				}else{
					p = Integer.parseInt(txtNbP.getText());
				}
				//Check the selection
				if (command.equals(aMString)) {
					simul = LoaderSimulation.createSimulationArrondiMethod(pathInstance,checkRoad.isSelected(),p);	
				}else if(command.equals(eMString)) {	
					simul = LoaderSimulation.createSimulationExactMethod(pathInstance,checkRoad.isSelected(),p);
				}else{
					int nbPop = Integer.parseInt(txtNbPopulation.getText());
					int nbIter = Integer.parseInt(txtNbIteration.getText());
					simul = LoaderSimulation.createSimulationGeneticsMethod(pathInstance, checkRoad.isSelected(),p,nbIter,nbPop);
				}
				simul.startSimulation();
				simul.showResult();
			}
		});
		checkFLP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(checkFLP.isSelected()){
					txtNbP.setEditable(true);
					txtNbP.setText("1");
				}else{
					txtNbP.setEditable(false);
					txtNbP.setText("");
				}
			}
		});	
		geneticsMethod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtNbPopulation.setEditable(true);
				txtNbIteration.setEditable(true);
				txtNbIteration.setText("200");
				txtNbPopulation.setText("100");
			}
		});
		
		exactMethod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtNbPopulation.setEditable(false);
				txtNbIteration.setEditable(false);
				txtNbIteration.setText("");
				txtNbPopulation.setText("");
			}
		});
		pane.add(controls, BorderLayout.CENTER); ;
	}
}
