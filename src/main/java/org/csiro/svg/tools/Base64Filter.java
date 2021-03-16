/**
 *
 * Copyright (c) 2000 CSIRO Mathematical and Information Sciences.
 *
 * $Id: Base64Filter.java,v 1.1 2002/02/21 07:48:40 brett Exp $
 */

package org.csiro.svg.tools;

import java.io.ByteArrayOutputStream;
import java.util.TreeMap;

public class Base64Filter
{
  static private Base64Filter s_instance = null;
  static private final char[] s_code =
    { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
      'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
      'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
      'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
      's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
      '3', '4', '5', '6', '7', '8', '9', '+', '/' };


  private TreeMap m_encodeMappings;
  private TreeMap m_decodeMappings;


  private Base64Filter()
  {
    loadEncodeMappings();
    loadDecodeMappings();
  }


  static public Base64Filter instance()
  {
    if (s_instance == null)
      s_instance = new Base64Filter();

    return s_instance;
  }


  public byte[] decode(String encoded)
  {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

    int index = 0;
    boolean done = false;
    while (!done)
    {
      int numBytes = 3;

      char charOne;
      char charTwo;
      char charThree;
      char charFour;

      if (index == encoded.length())
      {
        // stop right here
        done = true;
        continue;
      }

        charOne = encoded.charAt(index++);
        while (discardable(charOne))
        {
          charOne = encoded.charAt(index++);
        }
        charTwo = encoded.charAt(index++);
        while (discardable(charTwo))
        {
          charTwo = encoded.charAt(index++);
        }
        charThree = encoded.charAt(index++);
        while (discardable(charThree))
        {
         charThree = encoded.charAt(index++);
        }
        charFour = encoded.charAt(index++);
        while (discardable(charFour))
        {
          charFour = encoded.charAt(index++);
        }

      byte groupOne = ((Byte)(m_decodeMappings.get(new Character(charOne)))).byteValue();
      byte groupTwo = ((Byte)(m_decodeMappings.get(new Character(charTwo)))).byteValue();
      byte groupThree = 0; // this initialisation is meaningless but it shuts the compiler up
      byte groupFour = 0; // this initialisation is meaningless but it shuts the compiler up

      if (charThree == '=')
      {
        done = true;
        numBytes = 1;
      }
      else
      {
        groupThree = ((Byte)(m_decodeMappings.get(new Character(charThree)))).byteValue();
        if (charFour == '=')
        {
          done = true;
          numBytes = 2;
        }
        else
        {
          groupFour = ((Byte)(m_decodeMappings.get(new Character(charFour)))).byteValue();
        }
      }

      byte byteOne = (byte)(leftShift(groupOne, 2) | rightShift(groupTwo, 4));
      byteOut.write(byteOne);
      byte byteTwo = 0; // pointless
      byte byteThree = 0; // pointless

      if (numBytes > 1)
      {
        byteTwo = (byte)(leftShift((byte)(groupTwo&15), 4) | rightShift(groupThree, 2));
        byteOut.write(byteTwo);
        if (numBytes > 2)
        {
          byteThree = (byte)(leftShift((byte)(groupThree&3), 6) | groupFour);
          byteOut.write(byteThree);
        }
      }
    }

    return byteOut.toByteArray();
  }


  public byte[] encode(byte[] data)
  {
    StringBuffer buf = new StringBuffer();
    int numTriplets = data.length / 3;
    int bytesRemaining = data.length % 3;

    for (int i=0; i<=numTriplets; i++)
    {
      int numBytes = (i==numTriplets) ? bytesRemaining : 3;

      byte byteOne = (byte)0; byte byteTwo = (byte)0; byte byteThree = (byte)0;
      byte groupOne = 0; byte groupTwo = 0; byte groupThree = 0; byte groupFour = 0;
      char charOne = '='; char charTwo = '='; char charThree = '='; char charFour = '=';

      if (numBytes > 0)
      {
        byteOne = data[i*3];
        if (numBytes > 1)
        {
          byteTwo = data[i*3+1];
          if (numBytes > 2)
          {
            byteThree = data[i*3+2];
          }
        }
      }

      if (numBytes > 0)
      {
        groupOne = rightShift(byteOne, 2);
        groupTwo=(byte)(leftShift((byte)(byteOne&3),4)|rightShift(byteTwo, 4));
        charOne = ((Character)(m_encodeMappings.get(new Byte(groupOne)))).charValue();
        charTwo = ((Character)(m_encodeMappings.get(new Byte(groupTwo)))).charValue();

        if (numBytes > 1)
        {
          groupThree=(byte)(leftShift((byte)(byteTwo&15),2)|rightShift(byteThree,6));
          charThree=((Character)(m_encodeMappings.get(new Byte(groupThree)))).charValue();
          if (numBytes > 2)
          {
            groupFour = (byte)(byteThree&63);
            charFour=((Character)(m_encodeMappings.get(new Byte(groupFour)))).charValue();
          }
        }
      }

      if (numBytes > 0)
      {
        buf.append(charOne);
        buf.append(charTwo);
        buf.append(charThree);
        buf.append(charFour);
        if (buf.length() % 73 == 72)
        {
          buf.append("\n");
        }
      }
    }

    return buf.toString().getBytes();
  }


  private void loadEncodeMappings()
  {
    m_encodeMappings = new TreeMap();
    for (int i=0; i<64; i++)
    {
      m_encodeMappings.put(new Byte((byte)i), new Character(s_code[i]));
    }
  }


  private void loadDecodeMappings()
  {
    m_decodeMappings = new TreeMap();
    for (int i=0; i<64; i++)
    {
      m_decodeMappings.put(new Character(s_code[i]), new Byte((byte)i));
    }
  }


  private boolean discardable(char aChar)
  {
    if (aChar == '=')
    {
      return false;
    }
    if (m_decodeMappings.containsKey(new Character(aChar)))
    {
      return false;
    }

    return true;
  }


  private byte rightShift(byte theByte, int shift)
  {
    // don't get the idea that you can simply replace all
    // this nonsense with
    //   return (byte)(theByte >> shift);
    // or even
    //   return (byte)(theByte >>> shift)
    // neither of them work on bytes, because theByte is first
    // unary arithmetic cast to an int, and this cast treats
    // the byte as signed.

    return (byte)((theByte >>> shift) & ((1 << (8-shift)) - 1));
  }


  private byte leftShift(byte theByte, int shift)
  {
    // see above
    return (byte)((theByte & ((1 << (8-shift)) - 1)) << shift);
  }

}
