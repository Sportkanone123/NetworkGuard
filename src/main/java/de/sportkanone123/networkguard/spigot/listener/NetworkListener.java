package de.sportkanone123.networkguard.spigot.listener;

import de.sportkanone123.networkguard.spigot.packet.LoginPacket;
import de.sportkanone123.networkguard.spigot.packet.PlayPacket;
import de.sportkanone123.networkguard.spigot.packet.processor.ReceivingPacketProcessor;
import io.github.retrooper.packetevents.event.PacketListenerDynamic;
import io.github.retrooper.packetevents.event.impl.PacketLoginReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.event.priority.PacketEventPriority;

public class NetworkListener extends PacketListenerDynamic{
    public NetworkListener() {
        super(PacketEventPriority.HIGH);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        ReceivingPacketProcessor.handlePlayPacket(event.getPlayer(), new PlayPacket(event));
    }

    @Override
    public void onPacketLoginReceive(PacketLoginReceiveEvent event) {
        ReceivingPacketProcessor.handleLoginPacket(event.getSocketAddress(), new LoginPacket(event));
    }
}