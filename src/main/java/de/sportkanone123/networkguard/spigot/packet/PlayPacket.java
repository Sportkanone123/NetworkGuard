package de.sportkanone123.networkguard.spigot.packet;

import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import lombok.Getter;

@Getter
public class PlayPacket {
    private final NMSPacket rawPacket;
    private final byte packetId;
    private final PacketPlayReceiveEvent packetEvent;

    public PlayPacket(PacketPlayReceiveEvent event) {
        this.rawPacket = event.getNMSPacket();
        this.packetId = event.getPacketId();
        this.packetEvent = event;
    }

    public boolean isCustomPayload() { return packetId == PacketType.Play.Client.CUSTOM_PAYLOAD; }

    public boolean isBookEdit() { return packetId == PacketType.Play.Client.B_EDIT; }

    public boolean isFlying() { return packetId == PacketType.Play.Client.FLYING; }

    public boolean isKeepAlive() { return packetId == PacketType.Play.Client.KEEP_ALIVE; }

    public boolean isPosition() { return packetId == PacketType.Play.Client.POSITION; }

    public boolean isClientCommand() { return packetId == PacketType.Play.Client.CLIENT_COMMAND; }

    public boolean isCreativeSlot() { return packetId == PacketType.Play.Client.SET_CREATIVE_SLOT; }

    public boolean isWindowClick() { return packetId == PacketType.Play.Client.WINDOW_CLICK; }

    public boolean isItemUse() { return packetId == PacketType.Play.Client.USE_ITEM; }

    public boolean isUpdateSign() { return packetId == PacketType.Play.Client.UPDATE_SIGN; }
}