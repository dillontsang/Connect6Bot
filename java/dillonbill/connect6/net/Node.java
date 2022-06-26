import java.util.List;

public interface Node {
	
	void evaluate();
	
	void fire(double d);
	
	void addDownstreamNode (Node node);
	
	void addUpstreamNode (Node node);
	
	void reset();
	
	void setWeights (Weights weights);
	
	void setThreshold (double d);
	
	double getValue();
	
	List<Node> getDownstreamNodes();
	
	List<Node> getUpstreamNodes();
}

