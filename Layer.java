import java.util.LinkedList;
import java.util.List;

public class Layer {
	
	private List<Node> _nodes;
	  
	  public Layer (int count) {
	  	_nodes = new LinkedList<>();
	    for (int i = 0; i != count; i++) {
	    	_nodes.add(new HiddenNode());
	    }
	  }
	  
	  public void addNode (Node node) {
	  	_nodes.add(node);
	  }
	  
	  public void connectLayer (Layer downstream) {
	  	for (Node up: _nodes) {
	    	for (Node down: downstream._nodes) {
	      	up.addDownstreamNode(down);
	      }
	    }
	  }
	  
	  public void addUpstreamNode(Node node) {
		  for (Node up: _nodes) {
			  up.addUpstreamNode(node);
		  }
	  }
	  
	  public void addDownstreamNode(Node node) {
		  for (Node up: _nodes) {
			  up.addDownstreamNode(node);
		  }
	  }
	  
	  public Node getNode(int i) {
		  return _nodes.get(i);
	  }
}
