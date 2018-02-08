package tree;

import java.util.*;

public class Tree<T extends Comparable<T>> {
    
    //Properties
    private TreeNode<T> Root;
    private boolean LeftIsLess;
    
    //Constructors
    public Tree(){
        this.Root=new TreeNode();
        this.LeftIsLess=true;
    }
    public Tree(T val){
        this.Root=new TreeNode(val);
        this.LeftIsLess=true;
    }
    public Tree(boolean right){
        this.Root=new TreeNode();
        this.LeftIsLess=false;
    }
    public Tree(T val,boolean right){
        this.Root=new TreeNode(val);
        this.LeftIsLess=false;
    }
    
    //Get & Set
    public boolean getOrder() {
        return LeftIsLess;
    }
    public TreeNode<T> getRoot() {
        return Root;
        
    }
    public void setRoot(TreeNode<T> Root) {
        this.Root = Root;
    }
    public void setOrder(boolean LeftIsLess) {    
        this.LeftIsLess = LeftIsLess;
    }

    //Utilities
    public void add(TreeNode<T> node){
        TreeNode<T> current=this.Root;
        T val=node.getValue();
        //Find its Place
        while(current!=null){
            if(this.goLeft(current,val)){
                if(current.getLeft()==null){
                    break;
                }
                current=current.getLeft();
            }else{
                if(current.getRight()==null){
                    break;
                }
                current=current.getRight();
            }
        }
        //Assign
        if(this.goLeft(current, val)){
            current.setLeft(node);
        }else{
            current.setRight(node);
        }   
    }
    public void add(T val){
        TreeNode<T> current=this.Root;
        //Find its Place
        while(!isLast(current,val)){
            if(this.goLeft(current,val)){
                if(current.getLeft()==null || current.getLeft().getValue()==null){
                    break;
                }
                current=current.getLeft();
            }else{
                if(current.getRight()==null || current.getRight().getValue()==null){
                    break;
                }
                current=current.getRight();
            }
        }
        //Assign
        if(this.goLeft(current, val)){
            current.setLeft(val);
        }else{
            current.setRight(val);
        }   
    }
    public void Remove(T val){
        TreeNode<T> current=this.Root;
        if(!contains(val)){
            return;
        }
	//Save the lower elements.
        List<T> rearange=this.allBelow(val);
        //Find the needet value.
        while(current.getValue().compareTo(val)!=0){
            if(this.goLeft(current,val)){
                if(current.getLeft()==null || current.getLeft().getValue().compareTo(val)==0){
                    break;
                }
                current=current.getLeft();
            }else{
                if(current.getRight()==null || current.getRight().getValue().compareTo(val)==0){
                    break;
                }
                current=current.getRight();
            }
        }
        //Delete the single matching element
        if(goLeft(current,val)){
            current.setLeft(new TreeNode<T>());
        }else{
            current.setRight(new TreeNode<T>());
        }
        //Re-Add the other lower elements
        for(T item:rearange){
            this.add(item);
        }
    }
    public void RemoveBranch(T val){
        TreeNode<T> current=this.Root;
        //Find the needet branch
        while(current.getValue().compareTo(val)!=0){
            if(this.goLeft(current,val)){
                if(current.getLeft()==null || current.getLeft().getValue().compareTo(val)==0){
                    break;
                }
                current=current.getLeft();
            }else{
                if(current.getRight()==null || current.getRight().getValue().compareTo(val)==0){
                    break;
                }
                current=current.getRight();
            }
        }
        //Delete the element, with all its childs.
        if(goLeft(current,val)){
            current.setLeft(new TreeNode<T>());
        }else{
            current.setRight(new TreeNode<T>());
        }
    }
    public void Replace(T old,T nw){
        TreeNode<T> current=this.Root;
        //If it does not contains what we want to replace, exit.
        if(!contains(old)){
            return;
        }
        //Else find it.
        while(current.getValue().compareTo(old)!=0){
            if(this.goLeft(current,old)){
                if(current.getLeft().getValue().compareTo(old)==0){
                    break;
                }
                current=current.getLeft();
            }else{
                if(current.getRight().getValue().compareTo(old)==0){
                    break;
                }
                current=current.getRight();
            }
        }
        //Replace the single matching element
        if(goLeft(current,old)){
            current.getLeft().setValue(nw);
        }else{
            current.getRight().setValue(nw);
        }
    }
    public void ReplaceAll(T old,T nw){
        TreeNode<T> current=this.Root;
        TreeNode<T> last=current;
        while(!isLast(current,old)){
            if(last.getValue().compareTo(old)==0){
                last.setValue(nw);
            }
        }
    }
    
    /*
    Determines if this value must be positioned
    left=true OR right=false on the current node.
    */
    public boolean goLeft(TreeNode<T> node,T val){
        if(val.compareTo(node.getValue())<=0){
            if(this.LeftIsLess){     
                return true;
            }else{
                return false;
            }
        }else{
            if(this.LeftIsLess){     
                return false;
            }else{
                return true;
            }
        }
    }
    //Checks if this is the last suitable node for this value.
    public boolean isLast(TreeNode<T> node,T val){
        if(val.compareTo(node.getValue())<=0){
            if(this.LeftIsLess){
                return node.getLeft()==null;
            }else{
                return node.getRight()==null;
            }
        }else{
            if(this.LeftIsLess){
                return node.getRight()==null;
            }else{
                return node.getLeft()==null;
            }
        }
    }
    public boolean contains(T val){
        TreeNode<T> current=this.Root;
        while(!isLast(current,val)){
            if(current.getValue().compareTo(val)==0){
                return true;
            }
            if(goLeft(current,val)){
                current=current.getLeft();
            }else{
                current=current.getRight();
            }
        }
        return false;
    }
    public List<T> allBelow(T val){
        List<T> result=new ArrayList<T>();
	//We start from here.
        List<TreeNode<T>> last_depth=new ArrayList<TreeNode<T>>();
        //And work here.
	List<TreeNode<T>> current_depth=new ArrayList<TreeNode<T>>();
        TreeNode<T> current=this.Root;
        while(current.getValue().compareTo(val)!=0){
            if(this.goLeft(current,val)){
                if(current.getLeft()==null){
                    break;
                }
                current=current.getLeft();
            }else{
                if(current.getRight()==null){
                    break;
                }
                current=current.getRight();
            }
        }
	//This is the first occurence of the value.
        last_depth.add(current);
        while(!last_depth.isEmpty()){
	    //We save it's current childs (1:Left,1:Right)
            for(TreeNode<T> node:last_depth){
                if(node.getLeft()!=null){
                    current_depth.add(node.getLeft());
                }
                if(node.getRight()!=null){
                    current_depth.add(node.getRight());
                }
            }
	    //Make sure you do not save null's
            while(current_depth.contains(null)){
                current_depth.remove(null);
            }
            last_depth.clear();
	    //Prepare for finding the childs of the childs, in the next iteration.
            last_depth.addAll(current_depth);
            current_depth.clear();
	    //Add found childs to the result list.
            for(TreeNode<T> node:last_depth){
                if(node.getValue()!=null){
                    result.add(node.getValue());
                }
            }
        }
        return result;
    }
    public List<T> allElements(){
        List<T> result=new ArrayList<T>();
        result.add(this.Root.getValue());
        result.addAll(allBelow(this.Root.getValue()));
        return result;
    }
    public int depthOf(T val){
        int result=0;
        TreeNode<T> current=this.Root;
        //No such Element.
        if(!contains(val)){
            return -1;
        }
        //Search till you find the first.
        while(!isLast(current,val)){
            if(current.getValue().compareTo(val)==0){
                return result;
            }
            if(goLeft(current,val)){
                current=current.getLeft();
            }else{
                current=current.getRight();
            }
            result++;
        }
        return result==0?-1:result;
    }
    public int maxDepth(){
        int result=0;
        
        return result;
    }
    public int minDepth(){
        int result=0;
        
        return result;
    }
    public int averageDepth(){
        int result=0;
        
        return result;
    }
    
    @Override
    public String toString(){
        String result="";
        List<TreeNode<T>> last_depth=new ArrayList<TreeNode<T>>();
        List<TreeNode<T>> current_depth=new ArrayList<TreeNode<T>>();
        int depth=0;
        last_depth.add(this.Root);
        while(!last_depth.isEmpty()){
            result+="\n";
            for(TreeNode<T> node:last_depth){
                result+=node.getValue()+"\t";
            }
            for(TreeNode<T> node:last_depth){
                if(node.getLeft()!=null){
                    if(node.getLeft().getValue()!=null){
                        current_depth.add(node.getLeft());
                    }
                }
                if(node.getRight()!=null){
                    if(node.getRight().getValue()!=null){
                        current_depth.add(node.getRight());
                    }
                }
            }
            last_depth.clear();
            last_depth.addAll(current_depth);
            current_depth.clear();
        }
        return result;
    }
    
    public static void main(String[] args) {
        Random rnd=new Random();
        Tree<Integer> tree=new Tree<Integer>(22);
        
        //Populate with random integers.
        for(int i=0;i<20;i++){
            tree.add(rnd.nextInt(100));
        }
        System.out.println(tree);
        
        System.out.println("Number of elements: "+tree.allElements().size()
                            +"\nItems: "+tree.allElements());
        
    }  
}
