package com.moonsworth.client.nethandler.client;

import com.moonsworth.client.nethandler.ByteBufWrapper;
import com.moonsworth.client.nethandler.LCPacket;
import com.moonsworth.client.nethandler.client.obj.ServerRule;
import com.moonsworth.client.nethandler.shared.LCNetHandler;
import lombok.Getter;

import java.io.IOException;

public final class LCPacketServerRule extends LCPacket {

    @Getter private ServerRule rule;
    @Getter private int intValue;
    @Getter private float floatValue;
    @Getter private boolean booleanValue;
    @Getter private String stringValue = "";

    public LCPacketServerRule() {}

    public LCPacketServerRule(ServerRule rule, float value) {
        this(rule);
        this.floatValue = value;
    }

    public LCPacketServerRule(ServerRule rule, boolean value) {
        this(rule);
        this.booleanValue = value;
    }

    public LCPacketServerRule(ServerRule rule, int value) {
        this(rule);
        this.intValue = value;
    }

    public LCPacketServerRule(ServerRule rule, String value) {
        this(rule);
        this.stringValue = value;
    }

    private LCPacketServerRule(ServerRule rule) {
        this.rule = rule;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(rule.getId());
        buf.buf().writeBoolean(booleanValue);
        buf.buf().writeInt(intValue);
        buf.buf().writeFloat(floatValue);
        buf.writeString(stringValue);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.rule = ServerRule.getRule(buf.readString());
        this.booleanValue = buf.buf().readBoolean();
        this.intValue = buf.buf().readInt();
        this.floatValue = buf.buf().readFloat();
        this.stringValue = buf.readString();
    }

    @Override
    public void process(LCNetHandler handler) {
        ((LCNetHandlerClient) handler).handleServerRule(this);
    }

}