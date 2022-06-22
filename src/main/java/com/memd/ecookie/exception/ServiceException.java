package com.memd.ecookie.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	  private Object[] params;

	  /**
	   * .ctr
	   **/
	  public ServiceException() {
	  }

	  /**
	   * .ctr
	   *
	   * @param message - the message of this exception.
	   **/
	  public ServiceException(String message) {
	    super(message);
	  }

	  /**
	   * .ctr
	   *
	   * @param message - the message of this exception.
	   * @param cause - the wrapped Throwable object.
	   **/
	  public ServiceException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  /**
	   * .ctr
	   *
	   * @param cause - the wrapped Throwable object.
	   **/
	  public ServiceException(Throwable cause) {
	    super(cause);
	  }

	  /**
	   * .ctr
	   *
	   * @param message - the message of this exception.
	   * @param params - an array of parameters which come with the exception.
	   **/
	  public ServiceException(String message, Object[] params) {
	    super(message);

	    this.params = params;
	  }

	  /**
	   * .ctr
	   *
	   * @param message - the message of this exception.
	   * @param params - an array of objects which come with the exception.
	   * @param cause - the wrapped Throwable object.
	   **/
	  public ServiceException(String message, Object[] params, Throwable cause) {
	    super(message, cause);

	    this.params = params;
	  }

	  /**
	   * Get the array of parameters.
	   *
	   * @return - the array of parameters which come with the exception.
	   **/
	  public Object[] getParams() {
	    return params;
	  }
}
