
import java.io.*;
public class fileControlOut {
	
	DataOutputStream dataouttoH;
	
	fileControlOut(DataOutputStream dataoutto){
		dataouttoH=dataoutto;
	}
	
	fileControlOut(){
		
	}
	   File file=null;
	   public File[]  see_root(){
	      return File.listRoots();
	}
	   //public String  see_root(){
		//	File file[]=File.listRoots();    
		//    String ms="";
		  //   for(int i=0;i<file.length;i++)
		 //   	 ms=ms+file[i].getAbsolutePath()+"\n";
		//   return ms ; 
	 //  }
	   public String[] see_file(File f) throws FileNotFoundException{//���ļ��� ��Ŀ¼���ļ�
		   if(!f.exists())
		      return null;
		   String a="";
	       String b=""; 
	       if (f.isDirectory())  
		     return f.list(); 
		    
		  if(f.isFile()){
		 	System.out.print("file....."+file.length());
		     BufferedReader datain = new BufferedReader(new FileReader(f));
				
		    	while(true){
		    		   a="";
		    		try {
		    			//if(datain.)
						a=datain.readLine();
				       a=a.toString();
		    		} catch (Exception e) {
					   // e.printStackTrace(); 
					    //e22.printStackTrace();
					    break;
		    		}
					System.out.print("\n=="+a);//Ӧ�������﷢�ͳ�ȥ~~~~~~~~
		    	  if(a=="")
		    	    break;
		    	  b=b.concat(a); //????????????????
		    	
		    	}
		    	
		    	String c[]=new String[1];
		    	c[0]=b;
		    	  return c;
		       
		        }
		    
		    return f.list(); 
	   }
	   public String[] see_file(String f) throws FileNotFoundException{//�ļ������ļ����� Ŀ¼�����ļ�������
	           
		       file=new File(f);
		       information(f+"  exits?="+file.exists()+"   t="+file.getAbsolutePath()+"   "+file.length()+"  \n");
		       information("isfile??="+file.isFile());
		       return see_file(file);
	           
	   }
	   
	   public boolean copyfile(File f) throws FileNotFoundException{//Ӧ�� ���ֽڿ���
		    int i;
		   byte b[]=new byte[800];
		   if (!f.exists()){
		    	 information("File does not exits!!");
		         return false;
		     }
		   information("\n"+f.getAbsolutePath()+"  length="+f.length());
		     if (f.isFile()){
		    	 DataInputStream datain=new DataInputStream(new FileInputStream(f));  	 
		    	 information("\n"+f.getAbsolutePath()+"is a file!!  length="+f.length());
		    	 try{
		    		 if (f.length()<b.length){//����ļ���С�����軺���С
		    			 byte c[]=new byte[(int) f.length()];
		    			 datain.read(c);
		    			 send_data(c);
		    		 }else{
			    	      for(i=0;i<f.length()/b.length;i++){
			    	          datain.read(b);
			                  send_data(b);
			              }
			    	      //��β
			           byte c[]=new byte[ (int)(f.length()-b.length*(f.length()/b.length))];
			        	 datain.read(c);
				         send_data(c);
			        
		    		       }//else
			        }catch(IOException e){
			        	
			        }
		     }
		     return false;
		    	 
	   }
	   public boolean copyfile(String filename) throws FileNotFoundException{
		   //information("\nfilename");
		   return copyfile(new File(filename));
		   
	   }
	  /* public DataOutputStream getOUTputstream(){
		     return new DataOutputStream();
	   }*/
	   public void send_data(byte b[]) throws IOException{
		  // information(dataouttoH.);
		   dataouttoH.write(b);
	   }
	   public DataOutputStream getoutputstream(){
		   return dataouttoH;
	   }
	   public void information(String dw){
		   System.out.print(dw);
	   }
	   
}
