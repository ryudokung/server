/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package openbns.commons.net.codec.sts;

import io.netty.util.internal.StringUtil;

import java.util.Map;

/**
 * The default {@link StsMessage} implementation.
 */
public abstract class DefaultStsMessage extends DefaultStsObject implements StsMessage
{

  private StsVersion version;
  private final StsHeaders headers;

  /**
   * Creates a new instance.
   */
  protected DefaultStsMessage( final StsVersion version )
  {
    this( version, true );
  }

  protected DefaultStsMessage( final StsVersion version, boolean validate )
  {
    if( version == null )
    {
      throw new NullPointerException( "version" );
    }
    this.version = version;
    headers = new DefaultStsHeaders( validate );
  }

  @Override
  public StsHeaders headers()
  {
    return headers;
  }

  @Override
  public StsVersion getProtocolVersion()
  {
    return version;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append( StringUtil.simpleClassName( this ) );
    buf.append( "(version: " );
    buf.append( getProtocolVersion().text() );
    buf.append( ')' );
    buf.append( StringUtil.NEWLINE );
    appendHeaders( buf );

    // Remove the last newline.
    buf.setLength( buf.length() - StringUtil.NEWLINE.length() );
    return buf.toString();
  }

  @Override
  public StsMessage setProtocolVersion( StsVersion version )
  {
    if( version == null )
    {
      throw new NullPointerException( "version" );
    }
    this.version = version;
    return this;
  }

  void appendHeaders( StringBuilder buf )
  {
    for( Map.Entry<String, String> e : headers() )
    {
      buf.append( e.getKey() );
      buf.append( ": " );
      buf.append( e.getValue() );
      buf.append( StringUtil.NEWLINE );
    }
  }
}
