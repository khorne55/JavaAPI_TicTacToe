package tictactoe;


import tictactoe.TTTWebService;
import tictactoe.TTTWebService_Service;

public class TicTacToe {

    private TTTWebService proxy;
    private TTTWebService_Service hm;

    public TicTacToe() {
        hm = new TTTWebService_Service();
        proxy = hm.getTTTWebServicePort();
    }

    public TTTWebService getProxy() {
        return proxy;
    }

    public static void main(String[] args) {

        TicTacToe game = new TicTacToe();
        TTTWebService myLink = game.getProxy();
//        int pid = myLink.login("jmurphy", "jmurphy1");
//        System.out.println(pid);

        LoginWindow login = new LoginWindow();
        login.show();
    }

}
