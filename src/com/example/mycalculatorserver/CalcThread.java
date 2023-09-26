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
			// NOTE: ���ο� ��Ʈ���� �����մϴ�.
			BufferedReader in = getReader(m_clientSocket);
            BufferedWriter out = getWriter(m_clientSocket);
         
            // NOTE: Ŭ���̾�Ʈ�κ��� ����� �����޽��ϴ�
            String inputString = in.readLine();

			// NOTE: Ŭ���̾�Ʈ�� ����� �����մϴ�.
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
			System.out.println("Ŭ���̾�Ʈ���� ��ſ� �����Ͽ����ϴ�.");
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
		// TODO: ������ parsing�ϴ� �˰����� �� �Լ��� �����ϼ���.
		// input)
		//		String _expression => ���� ���ڿ��Դϴ�.
		// output)
		//		1. "." => ���ڿ� parsing�� �����ϸ� �� ���ڸ� ��ȯ�մϴ�.
		//		2. ���ڿ� parsing�� �����ϸ� ��� ������ ������ ����� ���ڿ��� ��ȯ�մϴ�.
		// ������ �԰��� ������ �����ϴ�.
		// 1) ������ �����ϴ� ���� ����: { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, +, -, x, / }
		// 2) Ŭ���̾�Ʈ�� �Ϲ������� ���Ǵ� ������ ��Ģ�� �����ϰ� ������� �ۼ��� �� ����.
		//		����1. ������ 2�� �̻��� �ߺ��Ͽ� �� �� ����. => 1++2-x4 ��..
		//		����2. �ֻ��� �ڸ����� 0�� ������ �� ����. => 0123, 000000002, 00009898, 00000 ��..
		//		����3. 0���� �����⸦ �Է¹��� ���� ����. => 0/0, 1/0 ��..
		//		����4. ������ ���ڿ��� �� ó�� �Ǵ� �� ���� �������� ���� ����. => x125+36, 1+2/, x33-62/ ��..

		// TODO: ������ parsing�ϴ� �˰����� �����Ͽ����� ���ǿ� �°� return���� �����ϼ���.
		// NOTE: Ŭ���̾�Ʈ ���α׷��� .�� �����ϴ� ������ �߸��� �������� �ν��ϹǷ�, .�� ���Ե� �׽�Ʈ return���� ������� ������.
		return String.format("Server received expression %s", _expression);
	}

	private void onCommunicationFailure()
    {
        m_isCommunicationSuccessed = false;
        m_outputString = "."; // NOTE: �߸��� ������ �������� �� . ���ڸ� �۽��մϴ�.
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
