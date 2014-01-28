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

/**
 * The default {@link StsResponse} implementation.
 */
public class DefaultStsResponse extends DefaultStsMessage implements StsResponse
{

  private StsResponseStatus status;

  /**
   * Creates a new instance.
   *
   * @param version the HTTP version of this response
   * @param status  the getStatus of this response
   */
  public DefaultStsResponse( StsVersion version, StsResponseStatus status )
  {
    this( version, status, true );
  }

  /**
   * Creates a new instance.
   *
   * @param version         the HTTP version of this response
   * @param status          the getStatus of this response
   * @param validateHeaders validate the headers when adding them
   */
  public DefaultStsResponse( StsVersion version, StsResponseStatus status, boolean validateHeaders )
  {
    super( version, validateHeaders );
    if( status == null )
    {
      throw new NullPointerException( "status" );
    }
    this.status = status;
  }

  @Override
  public StsResponseStatus getStatus()
  {
    return status;
  }

  @Override
  public StsResponse setStatus( StsResponseStatus status )
  {
    if( status == null )
    {
      throw new NullPointerException( "status" );
    }
    this.status = status;
    return this;
  }

  @Override
  public StsResponse setProtocolVersion( StsVersion version )
  {
    super.setProtocolVersion( version );
    return this;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append( StringUtil.simpleClassName( this ) );
    buf.append( "(decodeResult: " );
    buf.append( getDecoderResult() );
    buf.append( ')' );
    buf.append( StringUtil.NEWLINE );
    buf.append( getProtocolVersion().text() );
    buf.append( ' ' );
    buf.append( getStatus().toString() );
    buf.append( StringUtil.NEWLINE );
    appendHeaders( buf );

    // Remove the last newline.
    buf.setLength( buf.length() - StringUtil.NEWLINE.length() );
    return buf.toString();
  }
}
