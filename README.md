# AdvancedDSRedBlackEventCounter

Description: 
The aim of the project is to implement an event counter using red-black tree.  Each event has two fields: ID and count, where count is the number of active events with the given ID. 

Data Structure Explained:
Red-Black Tree is a self-balancing Binary Search Tree (BST) where every node follows following rules.

1) Every node has a color either red or black.
2) Root of tree is always black.
3) There are no two adjacent red nodes (A red node cannot have a red parent or red child).
4) Every path from root to a NULL node has same number of black nodes.


Why Red-Black Trees for the event counter?
Most of the BST operations (e.g., search, max, min, insert, delete.. etc) take O(h) time where h is the height of the BST. The cost of these operations may become O(n) for a skewed Binary tree. If we make sure that height of the tree remains O(Log n) after every insertion and deletion, then we can guarantee an upper bound of O(Log n) for all these operations. The height of a Red Black tree is always O(Logn) where n is the number of nodes in the tree.

System Requirements:

1. Hardware 
- Processor: Intel® Core™ i7-4760HQ CPU @ 2.10GHz × 8
- Memory: 16 GB Ram
- Disk: 219GB SSD

2. Operating System:
Ubuntu 15.10 Precise

3. Compiler:
javac 1.8.0_60 (Oracle)


Input and Output The zip folder contains the report, makefile and the source program java file. 
To run the java file type the following commands in the cmd 
java bbst test.txt command.txt > myoutput.txt 

This would compile the source program. The output of the program can be seen in the myoutput.txt file.
user@galago:~/Desktop/project/prerna/adsPrerna$ time /usr/lib/jvm/java-8-oracle/jre/bin/java -Xms1024M bbst ../../test_100.txt < ../../commands.txt > my_100 

real    0m0.490s 
user    0m0.188s 
sys     0m0.088s 
