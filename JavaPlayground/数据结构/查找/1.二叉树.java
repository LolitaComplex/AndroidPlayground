class BST<Key extends Comparable<Key>, Value>{

    private Node root;

    private class Node{
        private Key key;
        private Value val;
        private Node left, right;
        private int N;
        
        public Node(Key key, Value value, int N){
            this.key = key;
            this.val = value;
            this.N = N;
        }
    }

    public int size(){
        return size(root);
    }

    private int size(Node node){
        if (node == null){
            return 0;
        } else {
            return node.N;
        }
    }

    public Value get(Key key){
        return get(root, key);
    }

    private Value get(Node node, Key key){
        if (node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0){
            return get(node.right, key);
        } else if (cmp < 0){
            return get(node.left, key);
        } else {
            return node.val;
        }
    }

    public void put(Key key, Value val){
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val){
        // 队尾节点
        if (node == null){
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0){
            node.right = put(node.right, key, val);
        } else if(cmp < 0){
            node.left =  put(node.left, key, val);
        } else {
            node.val = val;
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public Key min(){
        return min(root).key;
    }

    // 返回最小节点
    private Node min(Node node){
        if (node.left == null){
            return node;
        }
        return min(node.left);
    }

    public Key max(){
        return max(root).key;
    }

    // 返回最大节点
    private Node max(Node node){
        if (node.right == null){
            return node;
        }
        return max(node.right);
    }

    // 向上取整
    public Key floor(Key key){
        Node node = floor(root, key);
        if (node == null){
            return null;
        }
        return node.key;
    }

    private Node floor(Node node, Key key){
        if (node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0){
            return node;
        } else if (cmp < 0){
            return floor(node.left, key);
        }
        Node x = floor(node.right, key);
        if ( x != null){
            return x;
        } else {
            return node;
        }
    }

    //向下取整
    public Key celling(Key key){
        Node node = celling(root, key);
        if (node == null){
            return null;
        }
        return node.key;
    }

    private Node celling(Node node, Key key){
        if (node == null){
            return null;
        }
        //遍历右子树
        int cmp = key.compareTo(node.key);
        if (cmp == 0){
            return node;//找到返回即可
        } else if (cmp > 0){
            return celling(node.right, key);
        }
        //遍历左子树，直到相等或者为null
        Node x = celling(node.left, key);
        if (x != null){
            return x;
        } else {
            return node;
        }
    }

    public Key select(int k){
        return select(root, k).key;
    }

    private Node select(Node node, int k){
        if (node == null){
            return null;
        }
        int t = size(node.left);
        if (t > k){
            return select(node.left, k);
        } else if (t < k){
            //因为实在右边这个二叉树中寻找了。左边的二叉树以及root已经没用了
            return select(node.right, k-t-1);
        } else {
            return node;
        }
    }

    public int rank(Key key){
        return rank(key, root);
    }

    private int rank(Key key, Node node){
        if (node == null){
            return 0;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0){
            // 右子树节点索引对应的是root的左子树与root节点的数量和
            return 1 + size(node.left) + rank(key, node.right);
        } else if (cmp < 0){
            return rank(key, node.left);
        } else {
            return size(node.left);
        }
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    /**
     * 原理：找到删除的目标最小节点，把最小节点的右子树赋值给最小节点的父结点的左链接。重新计算索引值 
     */
    private Node deleteMin(Node node){
        if (node.left == null){
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMax(){
        root = deleteMax(root);
    }

    /**
     * 原理：找到删除的目标最大节点，把最大节点的左子树赋值给最大节点的父结点的右链接。重新计算索引值 
     */
    private Node deleteMax(Node node){
        if (node.right == null){
            return node.left;
        }
        node.right = deleteMax(node.right);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delete(Key key){
        root = delete(root, key);
    }

    private Node delete(Node node, Key key){
        if (node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0){
            // 替换链接，让父节点的右链接指向我们新的节点
            node.right = delete(node.right, key);
        } else if (cmp < 0){
            // 替换链接，让父节点的左链接指向我们新的节点
            node.left = delete(node.left, key);
        } else {
            if (node.right == null){
                return node.left;
            }
            if (node.left == null){
                return node.right;
            }
            // 重要逻辑1,目标节点右子树中最小的节点删除，并且把这个最小节点右链接指向目标节点的右链接，
            // 最小节点的左链接指向目标节点的左链接
            Node x = node;
            node = min(x.right);
            node.right = deleteMin(x.right);
            node.left = x.left;
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public static void main(String[] args) {
        BST<Double,String> map = new BST<>();
        // map.put("键1", "斯卡雷特");
        // map.put("键2", "芙兰朵露");

        for (double x = 100; x >= 0; x--){
            map.put(x, "value" + x);
        }
        // System.out.println(map.get("key55"));
        // System.out.println(map.get("键2"));
        
        System.out.println(map.floor(545.6));
        System.out.println(map.celling(66.5));
        System.out.println(map.get(100.0));
        System.out.println(map.get(0.0));
        System.out.println(map.get(50.0));
        map.deleteMin();
        map.deleteMax();
        map.delete(50.0);
        System.out.println(map.get(100.0));
        System.out.println(map.get(0.0));
        System.out.println(map.get(50.0));

        System.out.println(map.select(map.rank(66.0)));
    }
}