package com.labs.algorithm.dijkstra.matrix;

/**
 * 迪杰斯特拉算法
 * @author panlf
 * @date 2021/9/9
 */
public class DijkstraMatrix {

    public static void main(String[] args) {
        int max = 1000;
        int[][]weight = {
                {0,1,12,max,max,max},
                {1,0,9,3,max,max},
                {12,9,0,4,5,max},
                {max,3,4,0,13,15},
                {max,max,5,13,0,4},
                {max,max,max,15,4,0}
        } ;
        int start = 0;
        Dijkstra(weight,start);

    }

    //接受一个有向图的权重矩阵，和一个起点编号start（从0编号，顶点存在数组中)
    //返回一个int[] 数组，表示从start到它的最短路径长度
    public static int[] Dijkstra(int[][]weight,int start){
        int length = weight.length;
        int[] shortPath = new int[length];//存放从start到各个点的最短距离
        shortPath[0] = 0;//start到他本身的距离最短为0
        String path[] = new String[length];//存放从start点到各点的最短路径的字符串表示
        for(int i=0;i<length;i++){
            path[i] = start+"->"+i;
        }
        int visited[] = new int[length];//标记当前该顶点的最短路径是否已经求出，1表示已经求出
        visited[0] = 1;//start点的最短距离已经求出
        for(int count = 1;count<length;count++){
            int k=-1;
            int dmin = Integer.MAX_VALUE;
            for(int i=0;i<length;i++){
                if(visited[i]==0 && weight[start][i]<dmin){
                    dmin = weight[start][i];
                    k=i;
                }
            }
            //选出一个距离start最近的未标记的顶点
            // 将新选出的顶点标记为以求出最短路径，且到start的最短路径为dmin。
            shortPath[k] = dmin;
            visited[k] = 1;
            //以k为中间点，修正从start到未访问各点的距离
            for(int i=0;i<length;i++){
                if(visited[i]==0 && weight[start][k]+weight[k][i]<weight[start][i]){
                    weight[start][i] = weight[start][k]+weight[k][i];
                    path[i] = path[k]+"->"+i;
                }
            }
        }
        for(int i=0;i<length;i++){
            System.out.println("从"+start+"出发到"+i+"的最短路径为："+path[i]+"="+shortPath[i]);
        }
        return shortPath;

    }
}

