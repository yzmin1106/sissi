package com.sissi.offline;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-15
 */
public interface StorageBox {

	public List<Protocol> pull(JID jid);

	public void push(Protocol protocol);
}