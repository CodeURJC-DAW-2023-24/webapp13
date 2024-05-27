package es.gualapop.backend.security.jwt;

public class AuthResponse {

	private Status status;
	private String message;
	private String error;
	private Token accessToken;
	private Token refreshToken;

	public enum Status {
		SUCCESS, FAILURE
	}

	public AuthResponse() {
	}

	public AuthResponse(Status status, String message) {
		this.status = status;
		this.message = message;
	}

	public AuthResponse(Status status, String message, String error) {
		this.status = status;
		this.message = message;
		this.error = error;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Token getAccessToken() {
        return accessToken;
    }

	public void setAccessToken(Token accessToken) {
        this.accessToken = accessToken;
    }

	public Token getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Token refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AuthResponse [status=" + status + ", message=" + message + ", error=" + error + ", accessToken=" + accessToken + "+ \", refreshToken=\" + refreshToken + \"]";
    }

}
