public class Main {




    public static void main(String[] args) {


        DBConnection conn = new DBConnection();

        Explorer exp = conn.getExplorer("bobSlayer","bob");

        /*
        System.out.println(exp.getRoomId());

        conn.Move(exp,101);

        exp = conn.updateExplorer(exp);

        System.out.println(exp.getRoomId());


         */


        //conn.Drop(exp,100);

        //conn.Grab(exp,100);

    }



}