package gestion.cabinetdoctor.Controles;
public class BDInfo {
	protected static String bdName = "cabinetdoctor";
	protected static String protocol = "jdbc:mysql:";
	protected static String user = "root";
	protected static String password = "4545";
	protected static String ip = "127.0.0.1";
	protected static int port = 3306;
	protected static String url = protocol + "//" + ip + ":" + port + "/" + bdName;
        protected static String filesPath = System.getProperty("user.dir") + "/src/gestion/cabinetdoctor/Files/";
}
