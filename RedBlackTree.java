import java.util.ArrayList;
//import java.util.LinkedHashMap;
import java.util.List;
/**
 * 
 * @author Prerna (email : prernamandal@ufl.edu)
 * cop 5536 programming project - advanced data structures
 * Guided by : Professor Sartaj Sahni, Serdar Ayaz @ University of Florida
 *
 * An event counter using red-black tree. 
 * Each event has two fields: eventId and eventCount, where eventCount is the number of active events with the given eventId.
 * 
 */

enum Color {Red, Black};

public class RedBlackTree {
	
	public class RedBlackNode{
		private int eventCount;
		private int eventId;
		private RedBlackNode Left,Right,Parent;
		private Color nodeColor;

		
		public RedBlackNode(int value,int key, RedBlackNode left, RedBlackNode right,RedBlackNode parent){
			this.eventCount = value;
			this.eventId = key;
			this.Left = left;
			this.Right = right;
			this.Parent = parent;
			this.nodeColor = Color.Red;
		}

		
		public RedBlackNode(int value,int key, RedBlackNode parent){
			this(value, key, null, null, parent);
			this.Left = createNilNode(this);
			this.Right = createNilNode(this);
		}
	}
	
	public RedBlackNode Root;

	public RedBlackTree(){
		this.Root = createParentRootNode();
	}

	public RedBlackTree(RedBlackNode root){
		this.Root = createInfinityNode(root);;
	}

	private RedBlackNode createParentRootNode(){
		return createInfinityNode(createNilNode(null));
	}

	private RedBlackNode createInfinityNode(RedBlackNode leftchild){
		RedBlackNode node = new RedBlackNode(Integer.MAX_VALUE,Integer.MAX_VALUE,leftchild,null,null);
		node.Left.Parent = node;
		node.nodeColor = Color.Black;
		return node;
	}

	private RedBlackNode createNilNode(RedBlackNode parent){
		RedBlackNode newNode = new RedBlackNode(Integer.MIN_VALUE,Integer.MIN_VALUE,null,null,parent);
		newNode.nodeColor = Color.Black;
		return newNode;
	}

	public boolean empty() {
		return isNilNode(this.Root.Left);
	}
	
	public int inrange(int id1, int id2){
		List<Integer> listRange = new ArrayList<Integer>();
		inrangeRecur(this.Root,id1,id2,listRange);
		return listRange.stream().mapToInt(Integer::intValue).sum();
	}
	
	public List<Integer> inrangeRecur(RedBlackNode root,int k1,int k2,List<Integer> listRange){
		if(isNilNode(root))
	        return listRange;
	    if(root.eventId >= k1)
	    	inrangeRecur(root.Left, k1, k2,listRange);
	    if(root.eventId >=k1 && root.eventId <=k2)
	    listRange.add(root.eventCount);
	    if(root.eventId < k2)
	    	inrangeRecur(root.Right,k1,k2,listRange);
	    return listRange;
	}
	
	RedBlackNode minValue(RedBlackNode node) {
		RedBlackNode current = node;
        while (!isNilNode(current.Left)) {
            current = current.Left;
        }
        return current;
    }
	
	public String next(int id1){
		RedBlackNode n = findNodeElseParent(id1,this.Root);
		RedBlackNode k;
		if(n.eventId > id1){
			return n.eventId + " " + n.eventCount;			
		}else{
			if (!isNilNode(n.Right)) {
				k = minValue(n.Right);
				return k.eventId+" "+k.eventCount;
	        }else{
	        	// step 2 of the above algorithm
	    		RedBlackNode p = n.Parent;
	            while (!isNilNode(p) && !isParentRootNode(p) && n == p.Right) {
	                n = p;
	                p = p.Parent;
	            }                  
	            if(isNilNode(p) || isParentRootNode(p)){
	            	return "0 0";
	            }else{
	            	return p.eventId+" "+p.eventCount;
	            }
	        }
	        	
		}
	}
	
	public String previous(int id1){
		RedBlackNode n = findNodeElseParent(id1,this.Root);
		RedBlackNode k;
		if(n.eventId < id1){
			return n.eventId + " " + n.eventCount;			
		}else{
			if (!isNilNode(n.Left)) {
				k = maximumNode(n.Left);
				return k.eventId+" "+k.eventCount;
	        }else{
	        	// step 2 of the above algorithm
	    		RedBlackNode p = n.Parent;
	            while (!isNilNode(p) && !isParentRootNode(p) && n == p.Left) {
	                n = p;
	                p = p.Parent;
	            }
	            if(isNilNode(p) || isParentRootNode(p)){
	            	return "0 0";
	            }else{
	            	return p.eventId+" "+p.eventCount;
	            }
	            
	        }
		}
		
        
	}
	
	public int count(int keyId)
	{
		RedBlackNode currNode = findNodeElseParent(keyId,this.Root);
		if(currNode == null){
			return 0; //empty tree
		}
		if(isNilNode(currNode) || currNode.eventId != keyId){
			return 0;//not found
		}
		//return the value of the node with the key = k
		return currNode.eventCount;
	}

	private RedBlackNode findNodeElseParent(int keyId,RedBlackNode node){
		//if the node that was given is the sentinel recursively call the function with the tree root
		if (isParentRootNode(node)){
			return findNodeElseParent(keyId,node.Left);
		}

		if(isNilNode(node)){
			//the node is not exist in the tree. we will return null if the tree is empty.
			if(isParentRootNode(node.Parent)){
				return null;
			}else{
				//we could not find the node so we will return the parent of the imaginary node if it was exist in the tree 
				return node.Parent;
			}
		}

		if(node.eventId > keyId){
			//if the current node key is bigger then the key we look for
			//recursively call searchNode with the left child of the current node 
			return findNodeElseParent(keyId,node.Left);
		}

		if(node.eventId < keyId){
			//if the current node key is lower then the key we look for
			//recursively call searchNode with the right child of the current node
			return findNodeElseParent(keyId,node.Right);
		}

		//if the current node key is not bigger and not lower it equals to the key we are looking for
		return node;
	}

	public void sortedArrayToRBTree(int[] listId,int[] listCount, int start, int end) {
		RedBlackNode node = sortedArrayToRBTreeRecur(listId,listCount, start, end, this.Root);
    }
	
	public RedBlackNode sortedArrayToRBTreeRecur(int[] listId,int[] listCount, int start, int end,RedBlackNode parent) {
		 
        /* Base Case */
        if (start > end) {
            return createNilNode(parent);
        }
 
        	/* Get the middle element and make it root */
        int mid = (start + end) / 2;
        
        RedBlackNode node = new RedBlackNode(listCount[mid],listId[mid],parent);
        node.nodeColor = Color.Black;
        	/* Recursively construct the left subtree and make it
        	left child of root */
		node.Left = sortedArrayToRBTreeRecur(listId,listCount, start, mid-1, node);
			/* Recursively construct the right subtree and make it
        	right child of root */
		node.Right = sortedArrayToRBTreeRecur(listId,listCount, mid+1, end, node);
		this.Root = node;
		return node;		
        
    }
	
	public List<String> inorderTraversal() {
		List<String> result = new ArrayList<String>();
        if(!isNilNode(this.Root)){
            helper(this.Root,result);
        }
 
        return result;
    }
 
    private void helper(RedBlackNode p,List<String> result){
        if(!isNilNode(p.Left))
            helper(p.Left,result);
 
        result.add(p.eventId + ":" + p.eventCount + ":" + p.nodeColor);
 
        if(!isNilNode(p.Right))
            helper(p.Right,result);
    }
    
	public void colorAllLeafRed(){
		List<RedBlackNode> listOfNodes = findDeepestNodes();
		for(RedBlackNode node : listOfNodes){
			if(node != this.Root)
			node.nodeColor = Color.Red;
		}
	}
    
    public List findDeepestNodes(){
  	  Object[] levelNodes = new Object[2];
  	  levelNodes[0] = 0;
  	  levelNodes[1] = new ArrayList();
  	  findDeepestNodes(this.Root, 1, levelNodes);
  	  return (List) levelNodes[1];
  	 }
  	
  	public void findDeepestNodes(RedBlackNode root, int level, Object[] levelNodes){
  		if (isNilNode(root))
  			return;
  		if((Integer)levelNodes[0]<=level)
  		{
  			if((Integer)levelNodes[0] < level)
  				((List)levelNodes[1]).clear();
  			levelNodes[0]=level;
  			((List)levelNodes[1]).add(root);
  		}
  		findDeepestNodes(root.Left, level+1, levelNodes);
  		findDeepestNodes(root.Right, level+1, levelNodes); 
  	}
    
   
	public int increase(int keyId, int valueCount) { 
		//find the place we want to insert the new node
		RedBlackNode y = findNodeElseParent(keyId,this.Root);
		//create new node which y is its parent
		RedBlackNode z = new RedBlackNode(valueCount,keyId,y);
		//counter counts the number of color changes
		if(y == null){
			//empty tree
			this.Root.Left = z;
			z.nodeColor = Color.Black;
			z.Parent = this.Root;
		}else{
			if(y.eventId == z.eventId){
				//an item with the key k is  already exist in the tree
				//return -1;
				y.eventCount = y.eventCount + z.eventCount;
				return y.eventCount;
			}

			if(z.eventId < y.eventId){
				//z will be a left child
				y.Left = z;
			}else{
				//z will be a right child
				y.Right = z;
			}

			//calling the method that will check if the tree is valid and if not fix it
			int counter = rbInsertFixUp(z);
		}
		return z.eventCount;
	}

	private int rbInsertFixUp (RedBlackNode node){
		//counter will count the number of color changes
		int counter = 0;
		//run until there is no problem with the red rule
		while(!(node.Parent.nodeColor==Color.Black)){
			if(node.Parent == node.Parent.Parent.Left){
				//z parent is a left child
				RedBlackNode node1 = node.Parent.Parent.Right;
				if(!(node1.nodeColor==Color.Black)){
					//case 1: z'w parent and uncle are red
					node.Parent.nodeColor = Color.Black;
					node1.nodeColor = Color.Black;
					node.Parent.Parent.nodeColor = Color.Red;
					node = node.Parent.Parent;

					//case 1 cost 3 color changes
					counter += 3;
				}else{
					if(node == node.Parent.Right){
						//case 2: z is a right child and its uncle is red. need to left rotate
						node = node.Parent;
						rbLeftRotation(node);
					}
					//case 3: z is a left child and its uncle is red. need to right rotate
					node.Parent.nodeColor = Color.Black;
					node.Parent.Parent.nodeColor = Color.Red;
					rbRightRotation(node.Parent.Parent);
					counter += 2;
				}
			}else{
				//z parent is a right child
				RedBlackNode y = node.Parent.Parent.Left;
				if(!(y.nodeColor==Color.Black)){
					//case 1: z'w parent and uncle are red
					node.Parent.nodeColor = Color.Black;
					y.nodeColor = Color.Black;
					node.Parent.Parent.nodeColor = Color.Red;
					node = node.Parent.Parent;

					//case 1 cost 3 color changes
					counter += 3;
				}else{
					if(node == node.Parent.Left){
						//case 2: z is a left child and its uncle is red. need to right rotate
						node = node.Parent;
						rbRightRotation(node);
					}
					//case 3: z is a right child and its uncle is red. need to left rotate
					node.Parent.nodeColor = Color.Black;
					node.Parent.Parent.nodeColor = Color.Red;
					rbLeftRotation(node.Parent.Parent);

					counter += 2;
				}
			}
		}

		if(!(this.Root.Left.nodeColor==Color.Black)){
			counter++;
			this.Root.Left.nodeColor = Color.Black;
		}

		return counter;
	}

	private void rbLeftChild(RedBlackNode node1,RedBlackNode node2){
		node1.Left = node2;
		node2.Parent = node1;
	}

	private void rbRightChild(RedBlackNode node1,RedBlackNode node2){
		node1.Right = node2;
		node2.Parent = node1;
	}

	private void rbTransplant(RedBlackNode node1, RedBlackNode node2){
		if (node1 == node1.Parent.Left){
			rbLeftChild(node1.Parent,node2);
		}else{
			rbRightChild(node1.Parent,node2);
		}
	}

	private void rbLeftRotation(RedBlackNode node1){
		RedBlackNode y = node1.Right;
		rbTransplant(node1,y);
		rbRightChild(node1,y.Left);
		rbLeftChild(y,node1);
	}

	private void rbRightRotation(RedBlackNode node2){
		RedBlackNode x = node2.Left;
		rbTransplant(node2,x);
		rbLeftChild(node2,x.Right);
		rbRightChild(x,node2);
	}

	public int reduce(int keyId,int valueCount)
	{	
		RedBlackNode node = findNodeElseParent(keyId, this.Root);
		if(node.eventId != keyId){
			//item with the key k could not be found
			return 0;
		}else{
			node.eventCount = node.eventCount - valueCount;
			if(node.eventCount <= 0){
				RedBlackNode node1;
				RedBlackNode node2 = node;
				boolean isBlackOriginalY = (node2.nodeColor == Color.Black ? true : false);

				//z is the node we want to delete
				if(isNilNode(node.Left)){
					node1 = node.Right;
					rbTransplant(node,node.Right);
				}else if(isNilNode(node.Right)){
					node1 = node.Left;
					rbTransplant(node,node.Left);
				}else{
					node2 = minimumNode(node.Right);
					isBlackOriginalY = (node2.nodeColor == Color.Black ? true : false);
					node1 = node2.Right;
					if(node2.Parent == node){
						node1.Parent = node2;
					}else{
						rbTransplant(node2,node2.Right);
						node2.Right = node.Right;
						node2.Right.Parent = node2;
					}
					rbTransplant(node,node2);
					node2.Left = node.Left;
					node2.Left.Parent = node2;
					node2.nodeColor = node.nodeColor;
				}

				if(isBlackOriginalY){
					//we have a problem with the black rule that needs to be fixed
					int counter = rbDeleteFixup(node1);
				}

				return 0;
			}else{
				return node.eventCount;
			}
				
		}

	}

	private int rbDeleteFixup(RedBlackNode node1){
		//number of color changes
		int counter = 0;
		//run until x is the tree root and as long as x is black
		while(node1 != this.Root.Left && (node1.nodeColor == Color.Black)){
			if(node1 == node1.Parent.Left){
				//x is a left child
				RedBlackNode node3 = node1.Parent.Right;
				if(!(node3.nodeColor == Color.Black)){
					//case 1
					node3.nodeColor = Color.Black;
					node1.Parent.nodeColor = Color.Red;
					rbLeftRotation(node1.Parent);
					node3 = node1.Parent.Right;
					counter += 2;
				}
				if((node3.Left.nodeColor == Color.Black) && (node3.Right.nodeColor == Color.Black)){
					//case 2
					node3.nodeColor = Color.Red;
					node1 = node1.Parent;
					counter += 1;
				}else 
				{
					if((node3.Right.nodeColor == Color.Black)){
						//case 3 
						node3.Left.nodeColor = Color.Black;
						node3.nodeColor = Color.Red;
						rbRightRotation(node3);
						node3 = node1.Parent.Right;
						counter +=2;
					}
					//case 4
					node3.nodeColor = node1.Parent.nodeColor;
					node1.Parent.nodeColor = Color.Black;
					node3.Right.nodeColor = Color.Black;
					rbLeftRotation(node1.Parent);
					node1 = this.Root.Left;
					counter += 1;
				}
			}else{
				//x is a right child
				RedBlackNode node3 = node1.Parent.Left;
				if(!(node3.nodeColor == Color.Black)){
					//case 1
					node3.nodeColor = Color.Black;
					node1.Parent.nodeColor = Color.Red;
					rbRightRotation(node1.Parent);
					node3 = node1.Parent.Left;
					counter += 2;
				}
				if((node3.Left.nodeColor == Color.Black) && (node3.Right.nodeColor == Color.Black)){
					//case 2
					node3.nodeColor = Color.Red;
					node1 = node1.Parent;
					counter += 1;
				}else 
				{
					if(node3.Left.nodeColor == Color.Black){
						//case 3 
						node3.Right.nodeColor = Color.Black;
						node3.nodeColor = Color.Red;
						rbLeftRotation(node3);
						node3 = node1.Parent.Left;
						counter +=2;
					}
					//case 4
					node3.nodeColor = node1.Parent.nodeColor;
					node1.Parent.nodeColor = Color.Black;
					node3.Left.nodeColor = Color.Black;
					rbRightRotation(node1.Parent);
					node1 = this.Root.Left;
					counter +=1;
				}
			}
		}
		if(node1.nodeColor == Color.Red){
			node1.nodeColor = Color.Black;
			counter += 1;
		}

		return counter;
	}


	private static RedBlackNode minimumNode(RedBlackNode node){
		if(isNilNode(node)){
			return null;
		}
		//next smaller node is null node so this is the minimal node
		if(isNilNode(node.Left)){
			return node;
		}

		return minimumNode(node.Left);
	}

	private boolean isParentRootNode(RedBlackNode node){
		return node.eventId == Integer.MAX_VALUE;
	}

	private static boolean isNilNode(RedBlackNode node){
		return node.eventId == Integer.MIN_VALUE;
	}

	public static RedBlackNode maximumNode(RedBlackNode node){
		//next node is null node so return this node
		if(isNilNode(node.Right)){
			return node;
		}
		return maximumNode(node.Right);
	}
		
}