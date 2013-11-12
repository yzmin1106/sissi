package com.sissi.protocol.iq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.session.Session;
import com.sissi.read.Collector;

/**
 * @author Kim.shen 2013-10-16
 */
@XmlRootElement(namespace = "")
public class IQ extends Protocol implements Collector {

	private final static List<Protocol> EMPTY_CHILDREN = new ArrayList<Protocol>();

	private List<Protocol> protocols;

	public IQ() {
		super();
	}

	public IQ(Type type) {
		super();
		super.setType(Type.RESULT.toString());
	}

	@XmlElements({ @XmlElement(name = "bind", type = Bind.class), @XmlElement(name = "session", type = Session.class), @XmlElement(name = "query", type = Roster.class) })
	public List<Protocol> getProtocols() {
		return protocols;
	}

	public void add(Protocol protocol) {
		if (this.protocols == null) {
			this.protocols = new CopyOnWriteArrayList<Protocol>();
		}
		protocol.setParent(this);
		this.protocols.add(protocol);
	}

	@Override
	public void set(String localName, Object ob) {
		this.add((Protocol.class.cast(ob)));
	}

	public List<Protocol> listChildren() {
		return this.protocols != null ? this.protocols : EMPTY_CHILDREN;
	}

	public Protocol clear() {
		this.protocols = null;
		return this;
	}
}
