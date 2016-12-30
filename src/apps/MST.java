package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		if (graph == null){
			return null;
		}
		PartialTreeList pt = new PartialTreeList();
		for (Vertex v : graph.vertices){
			PartialTree T = new PartialTree(v);
			for (Vertex.Neighbor nbr = v.neighbors; nbr != null; nbr = nbr.next){
				T.getArcs().insert(new PartialTree.Arc(v, nbr.vertex, nbr.weight));
			}
			pt.append(T);
		}
		return pt;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param graph Graph for which MST is to be found
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(Graph graph, PartialTreeList ptlist) {
		
		ArrayList<PartialTree.Arc> components = new ArrayList<PartialTree.Arc>(); 
	
		while (ptlist.size() > 1){
			MinHeap<PartialTree.Arc> pqx;
			PartialTree ptx = ptlist.remove();
			//System.out.println("L after removing ptx: ");
			Iterator<PartialTree> ir = ptlist.iterator();
			/*
			while (ir.hasNext()){
				System.out.println(ir.next());
			}
			*/
			PartialTree.Arc hP;
			Vertex v1, v2;
			do{
				pqx = ptx.getArcs();
				hP = pqx.deleteMin();							//highest priority arc from pq
				//System.out.println("highest priority arc = " + hP);  				
				v1 = hP.v1;										//belongs to ptx
				//System.out.println("v1 = " + v1);
				v2 = hP.v2;
				//System.out.println("v2 = " + v2);
			} while (v2.getRoot() == (ptx.getRoot()));
			components.add(hP);
			PartialTree pty = ptlist.removeTreeContaining(v2);
			//System.out.println("L after removing pty: ");
			//System.out.println("L size: " + ptlist.size());
			Iterator<PartialTree> is = ptlist.iterator();
			/*
			while (is.hasNext()){
				System.out.println(is.next());
			}
			*/

			//System.out.println("ptx = " + ptx);
			//System.out.println("pty = " + pty);
			ptx.merge(pty);
			//System.out.println("after merging ptx = " + ptx);
			ptlist.append(ptx);
			//System.out.println("L after appending: ");
			
			Iterator<PartialTree> it = ptlist.iterator();
			/*
			while (it.hasNext()){
				//System.out.println(it.next());
			}
			*/
		}

		return components;
	}
	
	
}
