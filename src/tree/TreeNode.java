package tree;

public class TreeNode<T extends Comparable<T>> {
    //Properties
    private T Value;
    private TreeNode<T> Left;
    private TreeNode<T> Right;
    
    //Constructors
    public TreeNode(){
        this.Value=null;
        this.Left=null;
        this.Right=null;
    }
    public TreeNode(T val){
        this.Value=val;
        this.Left=null;
        this.Right=null;
    }
    
    //Get & Set
    public T getValue() {
        return Value;
    }
    public TreeNode getLeft() {
        return Left;
    }
    public TreeNode getRight() {
        return Right;
    }
    public void setLeft(TreeNode Left) {
        this.Left = Left;
    }
    public void setRight(TreeNode Right) {
        this.Right = Right;
    }
    public void setValue(T Value) {
        this.Value = Value;
    }
    public void setLeft(T left) {
        this.Left = new TreeNode(left);
    }
    public void setRight(T right) {
        this.Right = new TreeNode(right);
    }    

   /* @Override
    public String toString() {
        return this.Value.toString();
    }*/
    
    
}
