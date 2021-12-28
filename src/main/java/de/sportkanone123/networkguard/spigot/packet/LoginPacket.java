package de.sportkanone123.networkguard.spigot.packet;

import io.github.retrooper.packetevents.event.impl.PacketLoginReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import lombok.Getter;

@Getter
public class LoginPacket {
    private final NMSPacket rawPacket;
    private final byte packetId;
    private final PacketLoginReceiveEvent packetEvent;

    public LoginPacket(PacketLoginReceiveEvent event) {
        this.rawPacket = event.getNMSPacket();
        this.packetId = event.getPacketId();
        this.packetEvent = event;
    }

}