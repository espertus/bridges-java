package bridges;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class BSTNode extends AbstractVertex {
	
	private BSTEdge leftEdge, rightEdge;	
	private int val;//temp till I research comparable
	
	/**
	 * Constructor for a new node.
	 * 
	 * @param identifier Name of the node.
	 * @param val The value so it can be sorted(temporary).
	 */
	public BSTNode(String identifier, int val){
		super(identifier);		
		this.val = val;
		/*
		outgoing = new ArrayList<AbstractEdge>();//creates empty list of connected edges
		//adding this vertex to the map for the JSON file.
		tree.vertices.put(identifier, this);
		*/
	}	
	
	public int getVal(){
		return val;
	}
	public void setVal(int val){
		this.val = val;
	}
	/**
	 * Creates the left Edge connecting the calling node to the passed node.
	 * 
	 * @param n The left child of the calling node.
	 */
	public void setLeftChild(BSTNode n) throws Exception{
		if(n == null){
			this.leftEdge = null;
		}
		else this.setLeftEdge(n);
	}
	/**
	 * 
	 * @return The left child of the calling node.
	 */
	public BSTNode getLeftChild(){
		if(this.getLeftEdge() == null){
			return null;
		}else return this.getLeftEdge().getOutgoing();
	}
	//This shouldn't have to be used by the student
	/**
	 * Creates and sets the left edge for the calling node.
	 * 
	 * @param n2 The second node that makes up the edge.
	 */
	private void setLeftEdge(BSTNode n2){
		//removing the edgeIdentifier would make removal easier
		
		this.leftEdge = new BSTEdge(this, n2);//creates the edge, and has the left pointer reference it
	}
	/**
	 * Returns the left connected edge for property manipulation.
	 * 
	 * @return The left edge from the calling node.
	 */
	public BSTEdge getLeftEdge(){
		return leftEdge;//returns the left connected edge
	}
	/**
	 * Creates the right Edge connecting the calling node to the passed node.
	 * 
	 * @param n The right child of the calling node.
	 * @throws Exception 
	 */
	public void setRightChild(BSTNode n) throws Exception{
		if(n == null){
			this.rightEdge = null;
		}
		else this.setRightEdge(n);		
	}
	/**
	 * 
	 * @return The right child of the calling node.
	 */
	public BSTNode getRightChild(){
		if(this.getRightEdge() == null){
			return null;
		}else return this.getRightEdge().getOutgoing();
	}
	/**
	 * Creates and sets the right edge for the calling node.
	 * 
	 * @param n2 The second node that makes up the edge.
	 */
	private void setRightEdge(BSTNode n2){

		this.rightEdge = new BSTEdge(this, n2);		
	}
	/**
	 * Returns the right connected edge.
	 * 
	 * @return The right edge from the calling node.
	 */
	public BSTEdge getRightEdge(){
		return rightEdge;
	}
	/**
	 * Internal code for getting the properties of an AbstractVertex.
	 * 
	 * It produces (without the spaces or newlines):
	 * <tt>
	 * {
	 *  "name": "Some identifier",
	 *  "other CSS properties like color": any_JSON_value
	 * }
	 * @returns the encoded JSON string
	 */
	void getNodeRepresentation(StringBuilder node_json, StringBuilder link_json) {
		getNodeRepresentation(0, node_json, link_json);
	}
	
	/**
	 * Internal code for getting the properties of an AbstractVertex.
	 * 
	 * It produces (without the spaces or newlines):
	 * <tt>
	 * {
	 *  "name": "Some identifier",
	 *  "other CSS properties like color": any_JSON_value
	 * }
	 * @returns the encoded JSON string
	 */
	int getNodeRepresentation(int node_index, StringBuilder node_json, StringBuilder link_json) {
		node_json.append(super.getRepresentation() + ","); //json rep of properties

		int left_child_index = node_index + 1;
		int right_child_index = left_child_index;
		
		if (getLeftEdge() != null) {
			link_json.append(getEdgeRepresentation(getLeftEdge(), node_index, left_child_index) + ",");
			right_child_index = getLeftChild().getNodeRepresentation(left_child_index, node_json, link_json);//json rep of node
					}
		
		
		int next_open_slot = right_child_index;
		if (getRightEdge() != null) {
			link_json.append(getEdgeRepresentation(getRightEdge(), node_index, right_child_index) + ",");
			next_open_slot = getRightChild().getNodeRepresentation(right_child_index, node_json, link_json);
		}
		return next_open_slot;
	}
	
	
	String getEdgeRepresentation(AbstractEdge edge, int source_index, int target_index) {
		String json = "{";
		for (Entry<String, String> entry : properties.entrySet()) {
			json += String.format("\"%s\": \"%s\", ", entry.getKey(), entry.getValue());
		}
		json += String.format("\"source\":%s,", source_index);
		json += String.format("\"target\":%s", target_index);
		return json + "}";
	}
}
