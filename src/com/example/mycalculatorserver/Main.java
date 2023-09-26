package com.example.mycalculatorserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
	public static void main(String[] args)
	{
		int acceptCount = 10;
		int leftAcceptCount = acceptCount;
		CalcThread[] threads = new CalcThread[leftAcceptCount];

		try
		{
			ServerSocket serverSocket = new ServerSocket(25565);
			
			System.out.println("클라이언트를 대기합니다.");
			
			while(leftAcceptCount > 0)
			{
				Socket clientSocket = serverSocket.accept();
				CalcThread thread = new CalcThread(clientSocket);
				thread.start();
				threads[--leftAcceptCount] = thread;
				System.out.println("연결 성공.");
			}
			
			serverSocket.close();
		}
		catch(IOException _ioEx)
		{
			System.out.println("서버 구동에 실패하였습니다.");
		}
		
		for(int i = 0; i < acceptCount; ++i)
		{
			String communicationResult = threads[i].isCommunicationSuccessed() ? "Success" : "Failure";
			System.out.println(String.format("Thread #%d, Communication %s", i, communicationResult));
		}
	}
}