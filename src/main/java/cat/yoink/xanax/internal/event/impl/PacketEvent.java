package cat.yoink.xanax.internal.event.impl;

import cat.yoink.xanax.internal.event.CustomEvent;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author yoink
 */
@Cancelable
public final class PacketEvent extends CustomEvent<PacketEvent>
{
    private final Packet<?> packet;
    private final Type type;

    public PacketEvent(Packet<?> packet, Type type)
    {
        this.packet = packet;
        this.type = type;
    }

    public Packet<?> getPacket()
    {
        return packet;
    }

    public Type getType()
    {
        return type;
    }

    public enum Type
    {
        INCOMING, OUTGOING
    }
}
