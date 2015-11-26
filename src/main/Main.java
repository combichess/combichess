package main;

// http://www.pageresource.com/clipart/entertainment/games/chess/
// http://zetcode.com/tutorials/javaswingtutorial/

/**/


import gui.Gui;
import system.board.BoardWrapper;

/*

För att kunna pusha till combichess så måste man ta itu med:

"Warning: Permanantly added ´github.com,192.30.252.131´ (RSA to the list of known hosts.
Error: Permission to combichess/combichess.git denied to gralm.
fatal: Could not read from remote repository.

Please make sure you have the correct access rights and the repository exists."

Repot existerar, SSH-keyn är validerad, problemet har uppstått tidigare, men nu är jag där igen och det går inte att lösa.
Jag är först i världen med det här problemet och det finns ingen som någonsin tidigare har frågat om detta.





git config user.name
git config user.email
git config --global user.name "Malm"
git config --global user.email "kfmalm@gmail.com"

https://help.github.com/articles/error-permission-denied-publickey/

Efter "ssh -T git@github.com" skickar det här förbannade jävla gitskämtet ur sig:
Hi annatUserName!

Ett försök att logga in via github-windows-skiten falerade. Tog först 5min att hitta logout-knappen, därefter logga in och 
sen blev det "login failed 
Unable to retrieve your user info from the server. A proxy server might be interfering with the request"

Tyvärr vet jag inte vad en proxy server är och vad den måste interfera med.
https://github.com/gitextensions/gitextensions/issues/2044
Nä, jag ska inte ominstallera .net. Finns inga möjligheter att jag gör det. Min bandbredd är för liten för 
såna gigantiska filer. Jag minns nu att det här problemet redan har dykt upp tidigare och att jag har installerat om .net, 
men varför återuppstår skiten för?

Jag lyckades från denna:
http://stackoverflow.com/questions/15551409/how-to-change-git-ssh-user-for-a-remote-push-temporarily
ge mig på ett hokuspokustest att pusha med:
git push https://github.com/combichess/combichess.git master
och det fungerade, fy fan vad jag avskyr git.

git push origin master 
där "origin" = "git@github.com:combichess/combichess.git" 
blir fel

istället måste tydligen "origin" vara = "https://github.com/combichess/combichess.git"
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
		
		System.out.println("MAIN: Trådar skapade");
		
		threadBoard.start();
		System.out.println("MAIN: Tråd board startad");
		
		threadGui.start();
		System.out.println("MAIN: Tråd gui startad, vänta på avslut");
		
		threadBoard.join();
		System.out.println("MAIN: Tråd board joinad");
		
		threadGui.join();
		System.out.println("MAIN: Tråd Gui joinad");
		System.out.println("MAIN: Programmet avslutas");
	}
}
