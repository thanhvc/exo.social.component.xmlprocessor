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
package org.exoplatform.social.xmlprocessor.filter.xml.model;

import java.util.LinkedList;

/**
 * Model of XML node tree
 * 
 * @author Ly Minh Phuong - http://phuonglm.net
 * 
 */
public class Node {
  private Node parentNode = null;
  private String title = "";
  private Attributes attributes = new Attributes();
  private String content = "";
  private LinkedList<Node> childNodes = new LinkedList<Node>();

  /**
   * get Parent Node of current Node. If current Node is root, parent Node ==
   * null;
   * 
   * @return parent node
   */
  public Node getParentNode() {
    return parentNode;
  }
  
  public void setParentNode(Node parentNode) {
    this.parentNode = parentNode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String nodeTitle) {
    this.title = nodeTitle;
  }

  public Attributes getAttributes() {
    return attributes;
  }

  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LinkedList<Node> getChildNodes() {
    return childNodes;
  }

  public void setChildNodes(LinkedList<Node> childNodes) {
    this.childNodes = childNodes;
  }

  public void addChildNode(Node childNode) {
    this.childNodes.add(childNode);
  }

  public void addAttribute(String key, String value) {
    this.attributes.put(key, value);
  }

  public String toString() {
    StringBuilder xmlString = new StringBuilder("");
    boolean selfClosedTag = false;
    boolean textTag = false;

    if (attributes.size() == 0 && childNodes.size() == 0 && content.equals("")) {
      selfClosedTag = true;
    }
    if (attributes.size() == 0 && childNodes.size() == 0 && !content.equals("")
        && title.equals("")) {
      textTag = true;
    }

    if (textTag) {
      xmlString.append(this.content);
    } else {
      if (this.parentNode != null) {

        xmlString.append("<" + this.title);

        xmlString.append(attributes.xml());

        if (selfClosedTag) {
          xmlString.append(" /");
        }
        xmlString.append(">");
      }
      for (Node childNode : childNodes) {
        xmlString.append(childNode.toString());
      }

      if (this.parentNode != null && !selfClosedTag) {
        xmlString.append("</" + this.title + ">");
      }
    }
    return xmlString.toString();
  }
  
  public String toOpenString() {
    StringBuilder xmlString = new StringBuilder("");
    boolean selfClosedTag = false;
    boolean textTag = false;

    if (attributes.size() == 0 && childNodes.size() == 0 && content.equals("")) {
      selfClosedTag = true;
    }
    if (attributes.size() == 0 && childNodes.size() == 0 && !content.equals("")
        && title.equals("")) {
      textTag = true;
    }

    if (textTag) {
      xmlString.append(this.content);
    } else {
      if (this.parentNode != null) {

        xmlString.append("<" + this.title);

        xmlString.append(attributes.xml());

        if (selfClosedTag) {
          xmlString.append(" /");
        }
        xmlString.append(">");
      }
    }
    return xmlString.toString();
  }
  
  public String toCloseString() {
    StringBuilder xmlString = new StringBuilder("");
    boolean selfClosedTag = false;
    boolean textTag = false;

    if (attributes.size() == 0 && childNodes.size() == 0 && content.equals("")) {
      selfClosedTag = true;
    }
    if (attributes.size() == 0 && childNodes.size() == 0 && !content.equals("")
        && title.equals("")) {
      textTag = true;
    }

    if (textTag) {
      xmlString.append(this.content);
    } else {
      if (this.parentNode != null && !selfClosedTag) {
        xmlString.append("</" + this.title + ">");
      }
    }
    return xmlString.toString();
  }
  
  public void convertToContent(){
    if(parentNode!=null){
      int thisPostion = parentNode.getChildNodes().indexOf(this);      
      this.content = this.toOpenString();      
      String closeTag = this.toCloseString();
      this.title = "";
      if(!closeTag.equals("")){
        Node closeContentNode = new Node();
        closeContentNode.setContent(closeTag);
        childNodes.addLast(closeContentNode);
        moveAllChildNodesToOtherNode(parentNode, thisPostion);
      }
    }
  }
  
  public void moveAllChildNodesToParentNode(int insertPosition){
    if(parentNode!=null){
      moveAllChildNodesToOtherNode(parentNode, insertPosition);
    }
  }
  
  public void moveAllChildNodesToOtherNode(Node parentNode,int insertPosition){
    if(parentNode!=null){
      LinkedList<Node> parentChildNodes = parentNode.getChildNodes();
      int postShift = insertPosition;
      for(Node childNode : childNodes){
        postShift++;
        childNode.setParentNode(parentNode);
        parentChildNodes.add(postShift,childNode);        
      }
      childNodes.clear();
    }
  }
}
