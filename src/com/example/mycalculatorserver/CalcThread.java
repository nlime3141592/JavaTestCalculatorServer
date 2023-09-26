package com.example.mycalculatorserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CalcThread extends Thread
{
	private Socket m_clientSocket;
	private boolean m_isCommunicationSuccessed;
    private String m_outputString;

	public CalcThread(Socket _clientSocket)
	{
		m_clientSocket = _clientSocket;
	}

	@Override
	public void run()
	{
		try
		{
			// NOTE: 새로운 스트림을 생성합니다.
			BufferedReader in = getReader(m_clientSocket);
            BufferedWriter out = getWriter(m_clientSocket);
         
            // NOTE: 클라이언트로부터 결과를 제공받습니다
            String inputString = in.readLine();

			// NOTE: 클라이언트에 결과를 제공합니다.
			String outputString = checkExpression(inputString);
			out.write(outputString + "\n");
			out.flush();

			onCommunicationSuccess(outputString);
			m_clientSocket.close();
		}
		catch (IOException e)
		{
			// e.printStackTrace();
			onCommunicationFailure();
			System.out.println("클라이언트와의 통신에 실패하였습니다.");
		}
	}
	
	public String getOutputString()
	{
		return m_outputString;
	}
	
	public boolean isCommunicationSuccessed()
	{
		return m_isCommunicationSuccessed;
	}

	private String checkExpression(String _expression)
	{
		// TODO: 수식을 parsing하는 알고리즘을 이 함수에 구현하세요.
		// input)
		//		String _expression => 수식 문자열입니다.
		// output)
		//		1. "." => 문자열 parsing에 실패하면 이 문자를 반환합니다.
		//		2. 문자열 parsing에 성공하면 산술 연산을 수행한 결과를 문자열로 반환합니다.
		// 수식의 규격은 다음과 같습니다.
		// 1) 수식을 구성하는 문자 집합: { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, +, -, x, / }
		// 2) 클라이언트는 일반적으로 통용되는 수식의 규칙을 무시하고 마음대로 작성할 수 있음.
		//		예시1. 연산자 2개 이상을 중복하여 쓸 수 있음. => 1++2-x4 등..
		//		예시2. 최상위 자릿수에 0을 포함할 수 있음. => 0123, 000000002, 00009898, 00000 등..
		//		예시3. 0으로 나누기를 입력받을 수도 있음. => 0/0, 1/0 등..
		//		예시4. 수신한 문자열의 맨 처음 또는 맨 끝이 연산자일 수도 있음. => x125+36, 1+2/, x33-62/ 등..

		// TODO: 수식을 parsing하는 알고리즘을 구현하였으면 조건에 맞게 return문을 수정하세요.
		// NOTE: 클라이언트 프로그램은 .이 존재하는 문장을 잘못된 수식으로 인식하므로, .이 포함된 테스트 return문을 사용하지 마세요.
		return String.format("Server received expression %s", _expression);
	}

	private void onCommunicationFailure()
    {
        m_isCommunicationSuccessed = false;
        m_outputString = "."; // NOTE: 잘못된 수식을 수신했을 때 . 문자를 송신합니다.
    }

    private void onCommunicationSuccess(String _message)
    {
        m_isCommunicationSuccessed = true;
        m_outputString = _message;
    }
    
    private BufferedReader getReader(Socket _socket)
    {
        try
        {
            return new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        }
        catch(IOException _ioEx)
        {

        }

        return null;
    }

    private BufferedWriter getWriter(Socket _socket)
    {
        try
        {
            return new BufferedWriter(new OutputStreamWriter(_socket.getOutputStream()));
        }
        catch(IOException _ioEx)
        {

        }

        return null;
    }
}
