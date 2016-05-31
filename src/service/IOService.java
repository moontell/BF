//閺堝秴濮熼崳鈫朞Service閻ㄥ嚪tub閿涘苯鍞寸�瑰湱娴夐崥锟�
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String readFileList(String userId)throws RemoteException;
	public boolean createNewProject(String userId,String fileName) throws RemoteException;
	public String read_File( String fileName) throws RemoteException;
}
