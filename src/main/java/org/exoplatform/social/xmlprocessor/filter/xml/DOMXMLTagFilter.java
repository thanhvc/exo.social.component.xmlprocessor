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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.exoplatform.social.xmlprocessor.Filter;
import org.exoplatform.social.xmlprocessor.filter.xml.model.Attributes;
import org.exoplatform.social.xmlprocessor.filter.xml.model.Node;


/**
 * this Filter travel through DOM tree and find if any TAG not satisfied the rules
 * spectify by whiteList. With wrong TAG it change it to content Type. 
 * @author Ly Minh Phuong - http://phuonglm.net
 *
 */
public class DOMXMLTagFilter implements Filter {
  private LinkedHashMap<String, Attributes> whiteList = new LinkedHashMap<String, Attributes>();
  
  public LinkedHashMap<String, Attributes> getWhiteList() {
    return whiteList;
  }
  /**
   * WhiteList Policy to TagFilter
   * @param whiteList
   */
  public void setWhiteList(LinkedHashMap<String, Attributes> whiteList) {
    this.whiteList = whiteList;
  }
  /**
   * set whitelist policy to DOMXMLagFilter
   * @param policy
   */
  public DOMXMLTagFilter(XMLTagFilterPolicy policy) {
    whiteList = policy.getWhiteList();
  }

  public Object doFilter(Object input) {
    if (input instanceof Node) {
      nodeFilter((Node) input);
    }
    return input;
  }

  private Node nodeFilter(Node currentNode){
    LinkedList<Node> currentChildNode = currentNode.getChildNodes();
    if(!currentNode.getTitle().equals("")){
      String tag = currentNode.getTitle();
      if(whiteList.containsKey(tag)){
        
        Attributes currentAttributes = currentNode.getAttributes();
        Attributes validedAttrubutes = new Attributes();
        
        for (Iterator<String> iterator = currentAttributes.getKeyIterator(); iterator.hasNext();) {
            String key = iterator.next();
            if(whiteList.get(tag).hasKey(key)){
              validedAttrubutes.put(key, currentAttributes.get(key)); 
            }
        }
        currentNode.setAttributes(validedAttrubutes);        
      } else {
        currentNode.convertToContent();
      }      
    }
    for(int i = 0; i < currentChildNode.size(); i++){
      nodeFilter(currentChildNode.get(i));
    }
    return currentNode;
  }
}
