package kyr1;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.io.Serializable; 
import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * 
 * @author User df
 * 
 * @param <V>
 *            dfsdfsda
 * @param <E>
 *            dfdsafasd
 */
public class BAGenerator<V, E> implements GraphGenerator<V, E> {

	private int numVertices;
	private int m;
	Factory<Graph<V, E>> graphFactory;
	Factory<V> vertexFactory;
	Factory<E> edgeFactory;
	Graph<V, E> g;

	/**
	 * 
	 * @param graphFactory
	 * @param vertexFactory
	 *            -
	 * @param edgeFactory
	 * @param numVertices
	 * @param m
	 */
	public BAGenerator(Factory<Graph<V, E>> graphFactory,
			Factory<V> vertexFactory, Factory<E> edgeFactory, int numVertices,
			int m) {

		this.graphFactory = graphFactory;
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.numVertices = numVertices;
		this.m = m;
	}

	// class MyClass implements Serializable{
	// }
	public Graph<V, E> create() {
		//MyClass testObject = new MyClass();
		g = graphFactory.create();
		// создать граф-затравку на m вершинах
		V[] mass = (V[]) new Object[m];
		for (int i = 0; i < mass.length; i++) {
			mass[i] = vertexFactory.create();
			g.addVertex(mass[i]);
		}
		for (int i = 0; i < mass.length - 1; i++) {
			for (int j = i + 1; j < mass.length; j++) {
				g.addEdge(edgeFactory.create(), mass[i], mass[j]);
			}
		}
		// выполнить (numVertices-m) шагов по добавлению вершин с m инцидентными
		// ребрами
		for (int i = 0; i < numVertices - m; i++) {
			V newVertex = vertexFactory.create();
			Set<V> setV = new HashSet();
			do {
				V v = getRandomVertex();
				setV.add(v);
				// try{
					//  FileOutputStream fos = new FileOutputStream("temp.txt");
					//  ObjectOutputStream outStream = new ObjectOutputStream((OutputStream) v);
					  //MyClass testObject = new MyClass();
					 // oos.writeObject(testObject);
					//  outStream.flush();
					 // outStream.close();
				//	}catch(Exception e)
					//{
					//System.out.println("Error+e.getMessage()");
					//}
		    
			} while (setV.size() < m);
////
			
			
			g.addVertex(newVertex);

			for (V v : setV) {
				g.addEdge(edgeFactory.create(), newVertex,v);
			}
			
		}

		// TODO Auto-generated method stub
		return g;
	}

	/*
	 * выбор вершины пропорциональное ее степени связности
	 */
	private V getRandomVertex() {
		V v = null;
		double sum = 0;
		for (V vv : g.getVertices()) {
			sum = sum + g.degree(vv);
		}
		double d = Math.random();
		double t = 0;
		for (V vv : g.getVertices()) {
			double pVV=g.degree(vv)/sum;
			t=pVV+t;
			//try(FileWriter writer = new FileWriter("2.txt", false))
	       // {
	           // запись всей строки
	           // String text = "Hello !";
	           // writer.write((int) t);
	            // запись по символам
	            //writer.append('\n');
	           // writer.append('E');
	             
	          //  writer.flush();
	      //  }
	       // catch(IOException ex){
	             
	          //  System.out.println(ex.getMessage());
	       // } 
			if(d<t) return vv;
		}

		return null;
	}

}