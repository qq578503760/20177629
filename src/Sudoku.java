import java.io.*;
import java.util.*;

public class Sudoku {
	public static void main(String[] args) {
		int rank=Integer.valueOf(args[1]).intValue();
		int problem=Integer.valueOf(args[3]).intValue();
		String filein=args[5];
		String fileout=args[7];
		ArrayList<int[][]> problems=new ArrayList<>();
		ArrayList<int[][]> results=new ArrayList<>();
		try {//输入问题
			File file=new File(filein);
			BufferedReader bw=new BufferedReader(new FileReader(file));
			for(int times=0;times<problem;times++) {
				int[][] prob=new int[9][9];
				for(int i=0;i<rank;i++) {
					String toint=bw.readLine();
					String str = toint.replaceAll(" ","");
					for(int j=0;j<rank;j++) {
						prob[i][j]=Integer.valueOf(str.charAt(j)).intValue()-48;
					}
				}
				bw.readLine();
				problems.add(prob);
			}
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<problem;i++) {//解决问题
			results.add(solve(problems.get(i),rank));
		}
		try {//输出结果
			File file=new File(fileout);
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			for(int times=0;times<results.size();times++) {
				int[][] result=results.get(times);
				for(int i=0;i<rank;i++) {
					for(int j=0;j<rank;j++) {
						bw.write(result[i][j]+"");
						bw.write(" ");
					}
					bw.newLine();
					bw.flush();
				}
				bw.newLine();
				bw.flush();
			}
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static int[][] solve(int [][] problem,int rank){
		int [][] result=problem;
		int[] numX=new int[81];
		int[] numY=new int[81];
		int[] numNow=new int[81];
		int tosolve=0;
		numX[0]=0;
		numY[0]=0;
		numNow[0]=0;
		for(int i=0;i<rank;i++) {//存储需要更改的位置
			for(int j=0;j<rank;j++) {
				if(result[i][j]==0) {
					numX[tosolve]=i;
					numY[tosolve]=j;
					tosolve++;
				}
			}
		}
		int count=0;
		int x=0,y=0;
		for(;count<tosolve;) {
			x=numX[count];
			y=numY[count];
			int i=numNow[count]+1;
			for(;i<=rank;i++) {
				result[x][y]=i;
				if(test(result,x,y,rank)) {//替换成功
					numNow[count]=i; 
					count++;
					for(int clear=count;clear<tosolve;clear++) {
						numNow[clear]=0;
					}
					//log(result);
					break;
				}
			}
			if(i==rank+1) {
				for(int clear=count;clear<tosolve;clear++) {//错误清除之后所有改变的项
					int changedx=numX[clear];
					int changedy=numY[clear];
					result[changedx][changedy]=0;
				}
				count--;
			}
		}
		return result;
	}
	public static boolean test(int[][] problem,int x,int y,int rank) {
		int flagx=0;
		int flagy=0;
		int xr=0;
		int yr=0;
		int flagr=0;
		for(int i=0;i<rank;i++) {//行测试
			if(problem[x][y]==problem[i][y]) {
				flagx++;
			}
		}
		if(flagx==2) {
			return false;
		}
		for(int i=0;i<rank;i++) {//列测试
			if(problem[x][y]==problem[x][i]) {
				flagy++;
			}
		}
		if(flagy==2) {
			return false;
		}
		switch(rank) {//宫测试初始化
			case 4:
				xr=2;
				yr=2;
				break;
			case 6:
				xr=2;
				yr=3;
				break;
			case 8:
				xr=4;
				yr=2;
				break;
			case 9:
				xr=3;
				yr=3;
				break;
			default:
 		}
		if(xr!=0) {//宫格测试
			for(int i=x-x%xr;i<x+xr-x%xr;i++) {
				for(int j=y-y%yr;j<y+yr-y%yr;j++) {
					if(problem[x][y]==problem[i][j]) {
						flagr++;
					}
				}
			}
			if(flagr==2) {
				return false;
			}
		}
		return true;
	}
	public static void log(int[][] result) {
		for(int[] in:result) {
			for(int i:in) {
				System.out.print(i);
			}
			System.out.println();
		}
	}
	public static void log(int[] resultx,int[] resulty) {
		for(int i=0;i<resultx.length;i++) {
			System.out.println("("+resultx[i]+","+resulty[i]+")");
		}
	}
}
