package gestion.cabinetdoctor.Controles;
public class BDInfo {
	protected static String bdName = "cabinetdoctor";
	protected static String protocol = "jdbc:mysql:";
	protected static String user = "your username";
	protected static String password = "****";
	protected static String ip = "127.0.0.1";
	protected static int port = 3306;
	protected static String url = protocol + "//" + ip + ":" + port + "/" + bdName+"?useSSL=false&serverTimezone=UTC";
}
