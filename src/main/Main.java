package main;

// http://www.pageresource.com/clipart/entertainment/games/chess/
// http://zetcode.com/tutorials/javaswingtutorial/

/**/


import gui.Gui;
import system.board.BoardWrapper;

/*

F�r att kunna pusha till combichess s� m�ste man ta itu med:

"Warning: Permanantly added �github.com,192.30.252.131� (RSA to the list of known hosts.
Error: Permission to combichess/combichess.git denied to gralm.
fatal: Could not read from remote repository.

Please make sure you have the correct access rights and the repository exists."

Repot existerar, SSH-keyn �r validerad, problemet har uppst�tt tidigare, men nu �r jag d�r igen och det g�r inte att l�sa.
Jag �r f�rst i v�rlden med det h�r problemet och det finns ingen som n�gonsin tidigare har fr�gat om detta.





git config user.name
git config user.email
git config --global user.name "Malm"
git config --global user.email "kfmalm@gmail.com"

https://help.github.com/articles/error-permission-denied-publickey/

Efter "ssh -T git@github.com" skickar det h�r f�rbannade j�vla gitsk�mtet ur sig:
Hi annatUserName!

Ett f�rs�k att logga in via github-windows-skiten falerade. Tog f�rst 5min att hitta logout-knappen, d�refter logga in och 
sen blev det "login failed 
Unable to retrieve your user info from the server. A proxy server might be interfering with the request"

Tyv�rr vet jag inte vad en proxy server �r och vad den m�ste interfera med.
https://github.com/gitextensions/gitextensions/issues/2044
N�, jag ska inte ominstallera .net. Finns inga m�jligheter att jag g�r det. Min bandbredd �r f�r liten f�r 
s�na gigantiska filer. Jag minns nu att det h�r problemet redan har dykt upp tidigare och att jag har installerat om .net, 
men varf�r �teruppst�r skiten f�r?

Jag lyckades fr�n denna:
http://stackoverflow.com/questions/15551409/how-to-change-git-ssh-user-for-a-remote-push-temporarily
ge mig p� ett hokuspokustest att pusha med:
git push https://github.com/combichess/combichess.git master
och det fungerade, fy fan vad jag avskyr git.

git push origin master 
d�r "origin" = "git@github.com:combichess/combichess.git" 
blir fel

ist�llet m�ste tydligen "origin" vara = "https://github.com/combichess/combichess.git"
*/


public class Main {
	
	//public static int String cmdMove;
	public static void main(String [] args) throws InterruptedException
	{
		startGame();
	}
	
	public static void startGame() throws InterruptedException
	{
		BoardWrapper bord = new BoardWrapper();
		Gui gui = new Gui();

		Thread threadBoard = new Thread(bord, "t-board");
		Thread threadGui = new Thread(gui, "t-gui");
		
		System.out.println("MAIN: Tr�dar skapade");
		
		threadBoard.start();
		System.out.println("MAIN: Tr�d board startad");
		
		threadGui.start();
		System.out.println("MAIN: Tr�d gui startad, v�nta p� avslut");
		
		threadBoard.join();
		System.out.println("MAIN: Tr�d board joinad");
		
		threadGui.join();
		System.out.println("MAIN: Tr�d Gui joinad");
		System.out.println("MAIN: Programmet avslutas");
	}
}
