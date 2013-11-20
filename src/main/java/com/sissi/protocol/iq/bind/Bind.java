package com.sissi.protocol.iq.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Feature;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.stream.Stream;

/**
 * @author Kim.shen 2013-10-20
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Bind extends Feature {

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-bind";

	private String jid;

	private Resource resource;

	@XmlElement(name = "jid")
	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Boolean hasResource() {
		return this.resource != null && this.resource.hasResource();
	}

	public Protocol clear() {
		super.clear();
		this.jid = null;
		this.resource = null;
		return this;
	}
}
