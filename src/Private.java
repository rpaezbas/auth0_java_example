
import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.net.AuthRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Servlet implementation class ServletSecured
 */
@WebServlet("/Private")
public class Private extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Private() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get the code from the queryString in the http header request
		if (request.getQueryString() != null) {
			
			String[] queryParams = request.getQueryString().split("=");
			
			// Auth.auth is the authentication end point of auth0. ExchangeCode changes a
			// code given when a user logs.
			AuthRequest req = AuthUtil.auth.exchangeCode(queryParams[1], "http://localhost:8080/auth0_3/ServletTok")
					.setScope("openid profile");

			try {
				//Get the toke in exchange iof the code
				TokenHolder holder = req.execute();
				String idToken = holder.getIdToken();
				//Auth.clientKey is specified in the auth0 client dashboard as client_secret
				Algorithm algorithm = Algorithm.HMAC256(AuthUtil.clientKey);
				// Verifies the signature for the issuer "https://rpaezbas.auth0.com/" with the
				// secretKey of our auth0 client
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("https://rpaezbas.auth0.com/").build();
				//In case it doesn´t verify it throws an exception
				DecodedJWT jwt = verifier.verify(idToken);
			} catch (Exception exception) {
				exception.printStackTrace();
				//If token doesnt verify redirect to login 
				response.sendRedirect("http://localhost:8080/auth0_3/Servlet");
			}
			
		} else {
			//If there is no query param means that the user has not logged in.
			response.sendRedirect("http://localhost:8080/auth0_3/Servlet");
		}

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
