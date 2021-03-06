package com.sissi.protocol.muc;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.presence.X;
import com.sissi.ucenter.relation.muc.MucItem;
import com.sissi.ucenter.relation.muc.status.CodeStatus;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XUser.XMLNS, localName = X.NAME)
@XmlType(namespace = XUser.XMLNS)
@XmlRootElement
public class XUser extends X implements CodeStatus, Collector, Field<String> {

	public final static String XMLNS = "http://jabber.org/protocol/muc#user";

	@SuppressWarnings("serial")
	private final static Set<ItemStatus> creator = new HashSet<ItemStatus>() {
		{
			add(ItemStatus.parse("201"));
		}
	};

	private Set<ItemStatus> statuses;

	private Invite invite;

	private Decline decline;

	private Destory destory;

	private Password password;

	private boolean hidden;

	private Item item;

	private JID group;

	private JID jid;

	public XUser() {
		super();
	}

	public XUser(JID group, JID jid, boolean hidden) {
		this.jid = jid;
		this.group = group;
		this.hidden = hidden;
	}

	public String group() {
		return this.group.asStringWithBare();
	}

	public boolean loop() {
		return this.jid != null ? this.jid.same(this.item.getJid()) : false;
	}

	public boolean hidden() {
		return this.hidden;
	}

	public boolean contain() {
		return !this.statuses.isEmpty();
	}

	public boolean contain(String code) {
		return this.statuses != null ? this.statuses.contains(ItemStatus.parse(code)) : false;
	}

	public XUser add(String code) {
		if (this.statuses == null) {
			this.statuses = new HashSet<ItemStatus>();
		}
		this.statuses.add(ItemStatus.parse(code));
		return this;
	}

	public XUser add(Set<String> codes) {
		for (String code : codes) {
			this.add(code);
		}
		return this;
	}

	public XUser clear() {
		if (this.statuses != null) {
			this.statuses.clear();
		}
		return this;
	}

	public XUser item(Item item) {
		this.item = item;
		return this;
	}

	public XUser item(MucItem item) {
		if (item.getClass() == Item.class) {
			this.item(Item.class.cast(item));
		}
		return this;
	}

	@XmlElement
	public Item getItem() {
		return this.item;
	}

	public XUser invite(Invite invite) {
		this.invite = invite;
		return this;
	}

	public boolean invite() {
		return this.getInvite() != null;
	}

	@XmlElement
	public Invite getInvite() {
		return this.invite;
	}

	@XmlElement
	public Decline getDecline() {
		return this.decline;
	}

	public boolean decline() {
		return this.getDecline() != null;
	}

	@XmlElement
	public Password getPassword() {
		return this.password;
	}

	public XUser password(String password) {
		this.password = password != null ? new Password(password) : null;
		return this;
	}

	public boolean password() {
		return this.getPassword() != null;
	}

	public XUser destory(Destory destory) {
		this.destory = destory;
		return this;
	}

	@XmlElement
	public Destory getDestory() {
		return destory;
	}

	@XmlElements({ @XmlElement(name = ItemStatus.NAME, type = ItemStatus.class) })
	public Set<ItemStatus> getStatuses() {
		return this.statuses == null ? this.statuses : this.statuses.contains(ItemStatus.parse("201")) ? creator : this.statuses;
	}

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}

	public <T extends CodeStatus> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case Invite.NAME:
			this.invite = Invite.class.cast(ob);
			return;
		case Decline.NAME:
			this.decline = Decline.class.cast(ob);
			return;
		case Password.NAME:
			this.password = Password.class.cast(ob);
			return;
		}
	}
}
