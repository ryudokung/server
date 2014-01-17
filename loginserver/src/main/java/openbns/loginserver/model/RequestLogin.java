package openbns.loginserver.model;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 17.01.14
 * Time: 23:01
 */
public class RequestLogin
{
  private String loginName;

  public String getLoginName()
  {
    return loginName;
  }

  public void setLoginName( String loginName )
  {
    this.loginName = loginName;
  }
}
