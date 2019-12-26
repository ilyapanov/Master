package kyr1;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

import org.apache.commons.collections15.Factory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class MainOld implements ActionListener {
	static Factory<String> edgeFactory = new Factory<String>() {
		int i;

		public String create() {
			return new Integer(i++).toString();
		}
	};

	static Factory<Integer> vertexFactory = new Factory<Integer>() {
		int i;

		public Integer create() {
			return new Integer(i++);
		}
	};
	static Factory<Graph<Integer, String>> graphFactory = new Factory<Graph<Integer, String>>() {

		public Graph<Integer, String> create() {

			return new UndirectedSparseGraph();
		}

	};

	public static void main(String[] args) {
		/// Graph<Integer, String> g=getGraph(new File("florence2.net"));
		
		
		Graph<Integer, String> g = genereateBAGraph(2, 100);
		
    

		frame(g);

	}

	private static Graph<Integer, String> genereateBAGraph(int m, int numVertices) {
		GraphGenerator<Integer, String> gn = new BAGenerator(graphFactory, vertexFactory, edgeFactory, numVertices, m);

		return gn.create();
	}

	private static Graph<Integer, String> genereateERGraph(int numVertices, double p) {
		GraphGenerator<Integer, String> gn = new ErdosRenyiGenerator(graphFactory, vertexFactory, edgeFactory,
				numVertices, p);

		return gn.create();
	}

	public static void frame(Graph<Integer, String> g) {


		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(900, 900));
		VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
		vv.setPreferredSize(new Dimension(900, 900));
		// Show vertex and edge labels

		// Create a graph mouse and add it to the visualization component




		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.PICKING);
		vv.setGraphMouse(gm);




		JPanel panel = new JPanel();
		panel.add(vv);




		JFrame frame = new JFrame("Interactive Graph View 5");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	
	}

	private static JPanel createChartPanel(Graph<Integer, String> g) {
		ChartPanel chartPanel = getDegreeChart(g);

		JPanel params = new JPanel();

		Box controls = Box.createVerticalBox();

		controls.add(chartPanel);
		controls.add(params);

		JPanel resPanel = new JPanel();
		resPanel.add(controls);

		return resPanel;
	}

	private static ChartPanel getDegreeChart(Graph<Integer, String> g) {
		// TODO Auto-generated method stub
		XYSeries series1 = new XYSeries("temp");
		int max = 0;

		for (Integer v : g.getVertices()) {
			int k = g.degree(v);

			if (k > max)
				max = k;
		}

		int[] mass = new int[max + 1];
		for (Integer v : g.getVertices()) {
			int k = g.degree(v);
			mass[k] = mass[k] + 1;

		}
		/*
		 * int k =0; for(Integer v:g.getVertices()) { k =(int)
		 * ((int)g.degree(v)/2*(3)*Math.log(g.degree(v)));
		 * 
		 * if(k>max)max=k; } k =0; double[] mass=new double[max+1]; for(Integer
		 * v:g.getVertices()){ k =(int) ((int)g.degree(v)/2*(3)*Math.log(g.degree(v)));
		 * //k =(int) ((int)g.degree(v)/(6*Math.log( g.getVertices()))); mass[ k]=mass[
		 * k]+1.0; System.out.println(mass[k]);
		 * 
		 * }
		 */

		for (int i = 0; i < mass.length; i++) {

			series1.add(i, mass[i]);

		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		JFreeChart chart = ChartFactory.createHistogram("a", "x", "y", dataset, PlotOrientation.VERTICAL, true, false,
				false);

		return new ChartPanel(chart);
	}

	public void actionPerformed(ActionEvent e) {

	}
}