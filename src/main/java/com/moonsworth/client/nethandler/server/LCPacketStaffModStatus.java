package com.moonsworth.client.nethandler.server;

import com.moonsworth.client.nethandler.ByteBufWrapper;
import com.moonsworth.client.nethandler.LCPacket;
import com.moonsworth.client.nethandler.shared.LCNetHandler;
import lombok.Getter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class LCPacketStaffModStatus extends LCPacket {

    @Getter private Set<String> enabled;

    public LCPacketStaffModStatus() {
        this.enabled = new HashSet<>();
    }

    public LCPacketStaffModStatus(Set<String> enabled) {
        this.enabled = enabled;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeVarInt(enabled.size());

        for (String mod : enabled) {
            buf.writeString(mod);
        }
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        int size = buf.readVarInt();

        for (int i = 0; i < size; i++) {
            enabled.add(buf.readString());
        }
    }

    @Override
    public void process(LCNetHandler handler) {
        ((LCNetHandlerServer) handler).handleStaffModStatus(this);
    }

}