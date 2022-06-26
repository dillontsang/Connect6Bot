import java.util.LinkedList;
import java.util.List;

public abstract class EvaluateNode implements Node {
	private double _accumulator;
	private Weights _weights;
	private List<Node> _downstream;
	private List<Node> _upstream;
	public static double NEUTRAL = 1.6;
	
	public EvaluateNode() {
		_downstream = new LinkedList<Node>();
		_upstream = new LinkedList<Node>();
	}
	
	public void setThreshold (double d) {
		_weights.set(this, this, d);
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
		node.addDownstreamNode(this);
	}
	
	public List<Node> getDownstreamNodes() {
		return _downstream;
	}
	
	public List<Node> getUpstreamNodes() {
		return _upstream;
	}
	
	public double getValue() {
		// TODO Auto-generated method stub	
		return squash(getAccumulator());
		// return Math.atan(getAccumulator()) + NEUTRAL;
	}
	
	public static double squash(double d) {
		return Math.atan(d) + NEUTRAL;
	}
}
