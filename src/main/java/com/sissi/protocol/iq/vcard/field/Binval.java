package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = VCard.XMLNS, localName = Binval.NAME)
@XmlRootElement(name = Binval.NAME)
public class Binval implements Field<String> {

	public final static String NAME = "BINVAL";

	private final StringBuffer value = new StringBuffer();

	public Binval() {
		super();
	}

	public Binval(String text) {
		super();
		this.value.append(text);
	}

	@XmlValue
	public String getValue() {
		return this.value.toString();
	}

	public Binval setText(String text) {
		this.value.append(text);
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}
