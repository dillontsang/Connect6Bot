package dillonbill.connect6.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import dillonbill.connect6.game.Board;

public abstract class EvaluateNode implements Node {
	private double _accumulator;
	private Weights _weights;
	private List<Node> _downstream;
	private List<Node> _upstream;
	public static double NEUTRAL = 1.6;
	private static int NEXT_ID = 0;
	private int _id;
	
	public EvaluateNode() {
		_downstream = new LinkedList<Node>();
		_upstream = new LinkedList<Node>();
		_id = NEXT_ID++;
	}
	
	public int getID () {
		return _id;
	}
		
	public void setThreshold (double d) {
		_weights.set(this, this, d);
	}
	
	protected void copyFrom (EvaluateNode input) {
		_id = input._id;
	}
	
	protected double getAccumulator() {
		return _accumulator;
	}
	
	protected void setAccumulator(double d) {
		_accumulator = d;
	}
	
	public void evaluate() {
		if (_accumulator >= _weights.get(this, this)) {
			for (Node n: getDownstreamNodes()) {
				double d = getValue() * _weights.get(this, n);
				n.fire(d);
			}
		}
	}
	
	public void reset() {
		_accumulator = 0.0;
	}
	
	public void fire(double d) {
		_accumulator = _accumulator + d;
	}
	
	public void setWeights(Weights weights) {
		_weights = weights;
	}
	
	public void writeSpecific (DataOutputStream os) throws IOException {
		
	}
	
	public void addDownstreamNode(Node node) {
		if(_downstream.contains(node)) {
			return;
		}
		_weights = null;
		_downstream.add(node);
		node.addUpstreamNode(this);
	}
	
	public void addUpstreamNode(Node node) {
		if(_upstream.contains(node)) {
			return;
		}
		
		_upstream.add(node);
		 /* if(node.getDownstreamNodes().contains(this)) {
			return;
		}
		node.addDownstreamNode(this); */
		
		//_upstream.add(node);
		node.addDownstreamNode(this);
	}
	
	public List<Node> getDownstreamNodes() {
		return _downstream;
	}
	
	public List<Node> getUpstreamNodes() {
		return _upstream;
	}
	
	public void clearConnections() {
		_downstream = new LinkedList<Node>();
		_upstream = new LinkedList<Node>();
	}
	
	public double getValue() {
		// TODO Auto-generated method stub	
		return squash(getAccumulator());
		// return Math.atan(getAccumulator()) + NEUTRAL;
	}
	
	public static double squash(double d) {
		return Math.atan(d);
	}
}
