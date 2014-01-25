package openbns.loginserver.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 25.01.14
 * Time: 15:15
 */
public class Account
{
  private String login;
  private String password;
  private Date lastLogin;
  private AccessLevel accessLevel;
  private String lastIp;

  public String getLogin()
  {
    return login;
  }

  public void setLogin( String login )
  {
    this.login = login;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword( String password )
  {
    this.password = password;
  }

  public Date getLastLogin()
  {
    return lastLogin;
  }

  public void setLastLogin( Date lastLogin )
  {
    this.lastLogin = lastLogin;
  }

  public AccessLevel getAccessLevel()
  {
    return accessLevel;
  }

  public void setAccessLevel( AccessLevel accessLevel )
  {
    this.accessLevel = accessLevel;
  }

  public String getLastIp()
  {
    return lastIp;
  }

  public void setLastIp( String lastIp )
  {
    this.lastIp = lastIp;
  }

  @Override
  public boolean equals( Object o )
  {
    if( this == o )
    {
      return true;
    }
    if( o == null || getClass() != o.getClass() )
    {
      return false;
    }

    Account account = (Account) o;

    if( !login.equals( account.login ) )
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    return login.hashCode();
  }

  @Override
  public String toString()
  {
    return "Account{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", lastLogin=" + lastLogin +
            ", accessLevel=" + accessLevel +
            ", lastIp='" + lastIp + '\'' +
            '}';
  }
}
