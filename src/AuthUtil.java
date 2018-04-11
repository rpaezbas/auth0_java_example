import com.auth0.client.auth.AuthAPI;

public class AuthUtil {
	static String clientKey = "7q8Owacjr2xKCOE7YwRnUBPwG7LJmRP31abbFPR-3oS7FAJOCSddJEybtjHK6Wcf";
	static String domain = "rpaezbas.auth0.com";
	static String clientId = "x6mEq1xqWkr730EKMD43N7gY227CZmpe";
	public static AuthAPI auth = new AuthAPI(domain,clientId,clientKey);
}
