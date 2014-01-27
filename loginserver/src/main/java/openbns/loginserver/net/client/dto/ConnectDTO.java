package openbns.loginserver.net.client.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene Chipachenko
 * Date: 17.01.14
 * Time: 22:48
 */
@XStreamAlias( "Connect" )
public class ConnectDTO
{
  private int connType;
  private String address;
  private int productType;
  private int appIndex;
  private long epoch;
  private int program;
  private int build;
  private int process;

  public int getConnType()
  {
    return connType;
  }

  public void setConnType( int connType )
  {
    this.connType = connType;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress( String address )
  {
    this.address = address;
  }

  public int getProductType()
  {
    return productType;
  }

  public void setProductType( int productType )
  {
    this.productType = productType;
  }

  public int getAppIndex()
  {
    return appIndex;
  }

  public void setAppIndex( int appIndex )
  {
    this.appIndex = appIndex;
  }

  public long getEpoch()
  {
    return epoch;
  }

  public void setEpoch( long epoch )
  {
    this.epoch = epoch;
  }

  public int getProgram()
  {
    return program;
  }

  public void setProgram( int program )
  {
    this.program = program;
  }

  public int getBuild()
  {
    return build;
  }

  public void setBuild( int build )
  {
    this.build = build;
  }

  public int getProcess()
  {
    return process;
  }

  public void setProcess( int process )
  {
    this.process = process;
  }
}
