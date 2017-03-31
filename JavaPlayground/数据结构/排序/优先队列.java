class TopM{

    public static void main(String[] args) {
        final int M = 5;
        //创建长度为M + 1的优先队列
        MinPQ<Transaction> pq = new MinPQ<>(M + 1);
        while(StdIn.hesNextLine()){
            pq.insert(new Transaction(StdIn.readLine()));
            //如果插入数据大于M，则删除最小的一条数据
            if(pg.size > M)
                pg.delMin();
        }
        Stack<Transaction> stack = new Stack<>();
        //把我们最大的5条数据插入栈中，我们获取就是正序排列的最大5条数据了
        while (!pg.isEmpty())
            stack.push(pg.delMin());
    }
}