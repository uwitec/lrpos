package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
public class ClientInfo {
	MyPoint[] point;//瀹㈡埛鍦板潃
	int numberOfClient;
	ClientInfo(){
		point=new MyPoint[0];
	}
	ClientInfo(int n){
		numberOfClient=n;
		point=new MyPoint[n];
	}
	public int getNumberOfClient(){
		return numberOfClient;
	}
	void getFileLines(String filename){//璇诲彇txt鏂囦欢鐨勮鏁�		
		try{
			BufferedReader reader=new BufferedReader(new FileReader(filename));
			int i=0;
			while((reader.readLine())!=null)
				i++;
			reader.close();
			numberOfClient=i;
			point=new MyPoint[numberOfClient];
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.print("璇诲彇鏂囦欢鍑洪敊\n");
		}
	}
public void setClientPointByTxt(String filename){//璇诲彇txt鏂囦欢
		getFileLines(filename);
		try{
			BufferedReader reader=new BufferedReader(new FileReader(filename));
			String lineString="";
			int i=0;
			while((lineString=reader.readLine())!=null){
				if(lineString=="")
					continue;
				String[] str=lineString.split(",");
				double x=Double.parseDouble(str[0]);
				double y=Double.parseDouble(str[1]);
				MyPoint p=new MyPoint();
				p.setpoint(x, y);
				point[i]=p;
				i++;
			}
			reader.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO: handle exception
			//System.out.print("璇诲彇鏂囦欢鍐呭鍑洪敊\n");
			e.printStackTrace();
		}
		
	}
	public void setClientPointByExcel(String filename){//璇诲彇excel鏂囦欢
		Workbook workbook=null;
		try{
			workbook=Workbook.getWorkbook(new File(filename));
			Sheet sheet=workbook.getSheet(0);
			int rows=sheet.getRows();
			numberOfClient=rows;
			point=new MyPoint[numberOfClient];
			for(int i=0;i<rows;i++){
				Cell cell1=sheet.getCell(i,0);
				Cell cell2=sheet.getCell(i,1);
				point[i].setpoint(Double.parseDouble(cell1.getContents()), Double.parseDouble(cell2.getContents()));
			}
			workbook.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	MyPoint[] getClientPoint(){
		return point;
	}
}