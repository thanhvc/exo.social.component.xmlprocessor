/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.social.xmlprocessor.filter.xml;

import java.util.LinkedHashMap;

import org.exoplatform.social.xmlprocessor.filter.xml.model.Attributes;


public class XMLTagFilterPolicy {
  private LinkedHashMap<String, Attributes> whiteList = new LinkedHashMap<String, Attributes>();
  
  public LinkedHashMap<String, Attributes> getWhiteList() {
    return whiteList;
  }

  public void setWhiteList(LinkedHashMap<String, Attributes> whiteList) {
    this.whiteList = whiteList;
  }
  
  public void addAllowTags(String... tagStrings){
    for (String tagString : tagStrings){
      String nomarlTag = tagString.toLowerCase();
      if(!whiteList.containsKey(nomarlTag)){
        whiteList.put(nomarlTag, new Attributes());
      }
    }
  }
  public void addAllowAttributes(String tag, String... attributes){
    String nomarlTag = tag.toLowerCase();
    if(whiteList.containsKey(nomarlTag)){      
      Attributes attributesHashMap = whiteList.get(tag);
      for(String attribute: attributes){
        attributesHashMap.put(attribute.toLowerCase(), null);
      }
    }
  }
  
  
  public void removeAllowedAttribute(String tag, String... attributes){
    if(whiteList.containsKey(tag)){
      Attributes attributesHashMap = whiteList.get(tag);
      for(String attribute: attributes){
        if(attributesHashMap.hasKey(attribute)){
          attributesHashMap.remove(attribute);
        }
      }
    }
  }
}
