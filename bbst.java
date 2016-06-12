import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
public class bbst {
	public static void main(String[] args){
		RedBlackTree tree = new RedBlackTree();
		//Code to take an input file and parse it
		//Path objPath = Paths.get("/Users/nogle/Downloads/test_2.txt");
		Path objPath = Paths.get(args[0]);
	    if (Files.exists(objPath)){
	    
	     File objFile = objPath.toFile();
	     try(BufferedReader in = new BufferedReader(new FileReader(objFile))){
	          String lineCount = in.readLine();//check if it is an integer or not
	          int listId[] = new int[Integer.parseInt(lineCount)];
	  	      int listCount[] = new int[Integer.parseInt(lineCount)];
	  	      int i = 0;
	          String line = in.readLine();
		           while(line != null){
			        	//if(!line.isEmpty() && line.contains(" ")){
			        		String[] linesFile = line.split(" ");
			        		int key = 0;int value = 0;
				        		//if(linesFile.length == 2){
					        			//if(isInteger(linesFile[0]))
						        			key = Integer.parseInt(linesFile[0]);
					        			//else
							            	//System.out.println("wrong input @" + line);
							            
						        		//if(isInteger(linesFile[1]))
						        			value = Integer.parseInt(linesFile[1]);
						        		//else
							            	//System.out.println("wrong input @" + line);
					        		
							            //if(value>0){
							            	listId[i] = key;
							            	listCount[i++] = value;
							            //}else{
							            //	System.out.println("wrong input @" + line);
							            //}
				        		//}
				        		//else{
					            //	System.out.println("wrong input @" + line);
					            //}
			        	//}
		            line = in.readLine();
		           }
	           
	           //if(listId.length == listCount.length && listId.length > 0){
		     		tree.sortedArrayToRBTree(listId,listCount, 0, listId.length - 1);
			     	tree.colorAllLeafRed();
		     	//}else{
		     		//System.out.println("wrong input leading to size difference of id and count");
		     	//}

	        }
	         catch(IOException e){
	             System.out.println(e.getMessage());
	         }
	     	
	     	
	     	
	     	
	     	//read the commands from input and process it according to the switch cases	     	
	     	try{
	    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	    			
	     		
	     		//Path commandPath = Paths.get("/Users/nogle/Downloads/commands.txt");
	     		//Path commandPath = Paths.get(args[1]);
	     		//BufferedReader br = new BufferedReader(new FileReader(commandPath.toFile()));
	     		String input;	    			
	    		while(!(input=br.readLine()).equals("quit")){
	    			//System.out.println("the input command is :" + input);
	    			String[] linesFile;
	    			int var1,var2;
	    			String function;
	    			//if(input!=null && input.contains(" ")){
	    				linesFile = input.split(" ");
	    				//if(linesFile.length >= 2){
	    					function = linesFile[0];	    	    			
	    	    			switch (function) {
	    	                case "increase":
	    	                	if(linesFile.length == 3 && isInteger(linesFile[1]) && isInteger(linesFile[2]) && Integer.parseInt(linesFile[2]) > 0){
	    	                		var1 = Integer.parseInt(linesFile[1]);
		    	                	var2 = Integer.parseInt(linesFile[2]);
		    	                	System.out.println(tree.increase(var1, var2));
	    	                	}
	    	                    break;
	    	                case "reduce":  
	    	                	if(linesFile.length == 3 && isInteger(linesFile[1]) && isInteger(linesFile[2]) && Integer.parseInt(linesFile[2]) > 0){
	    	                		var1 = Integer.parseInt(linesFile[1]);
	                   		 		var2 = Integer.parseInt(linesFile[2]);
	                   		 		System.out.println(tree.reduce(var1, var2));
	    	                	}
	    	                    break;
	    	                case "count":
	    	                	if(linesFile.length == 2 && isInteger(linesFile[1])){
	    	                		var1 = Integer.parseInt(linesFile[1]);
	    	                		System.out.println(tree.count(var1));
	    	                	}
	    	                    break;
	    	                case "inrange":
	    	                	if(linesFile.length == 3 && isInteger(linesFile[1]) && isInteger(linesFile[2]) && Integer.parseInt(linesFile[2]) > 0){
	    	                		var1 = Integer.parseInt(linesFile[1]);
	                   		 		var2 = Integer.parseInt(linesFile[2]);
	                   		 		System.out.println(tree.inrange(var1, var2));
	    	                	}
	    	                    break;
	    	                case "next":
	    	                	if(linesFile.length == 2 && isInteger(linesFile[1])){
	    	                		var1 = Integer.parseInt(linesFile[1]);
	    	                		System.out.println(tree.next(var1));
	    	                	}	
	    	                	break;
	    	                case "previous":
	    	                	if(linesFile.length == 2 && isInteger(linesFile[1])){
	    	                		var1 = Integer.parseInt(linesFile[1]);
	    	                		System.out.println(tree.previous(var1));
	    	                	}
	    	                	break;
	    	                default: break;
	    				}
	    			//}
	    			
	    			
	    			//}
	    		}
	    			
	    	}catch(Exception io){
	    		System.out.println("wrong input");
	    		io.printStackTrace();
	    	}	
	     	
//	     	System.out.println(tree.increase(3, 4));
//	     	System.out.println(tree.inorderTraversal());
//	     	System.out.println(tree.increase(13, 14));
//	     	System.out.println(tree.inorderTraversal());
//	     	
//	     	System.out.println(tree.reduce(3, 4));
//	     	System.out.println(tree.inorderTraversal());
//	     	System.out.println(tree.reduce(15, 14));
//	     	System.out.println(tree.inorderTraversal());
//	     	System.out.println(tree.reduce(13, 14));
//	     	System.out.println(tree.inorderTraversal());
//	     	
//	     	System.out.println(tree.count(9));
//	     	System.out.println(tree.inorderTraversal());
	     	
	     	//System.out.println(tree.inrange(12,13));
	     	//System.out.println(tree.inorderTraversal());
	     	
//	     	System.out.println(tree.next(11));
//	     	System.out.println(tree.inorderTraversal());
	     	
	     	//System.out.println(tree.previous(1));
	     	//System.out.println(tree.inorderTraversal());
	     	
	     	
	     	
	    }
	    else
	    {
	      System.out.println(
	              objPath.toAbsolutePath() + " doesn't exist");
	    }
	    
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}