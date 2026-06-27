package mx.com.qtx.progeven.messageBroker;

import mx.com.qtx.progeven.core.IPublicadorNotificaciones;
import mx.com.qtx.progeven.messageBroker.rabbitmq.EmisorNotificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificadorFactory {
    private static Logger bitacora = LoggerFactory.getLogger(NotificadorFactory.class);

    public static IPublicadorNotificaciones getEmisorNotificacion(String hostRabbitmq, String nomIntermediario) {
        EmisorNotificacion emisorTarea = new EmisorNotificacion(nomIntermediario);
        emisorTarea.setHostRabbitMQ(hostRabbitmq);
        bitacora.info("EmisorNotificacion(" + hostRabbitmq + ", " + nomIntermediario + ") instanciado");
        return emisorTarea;
    }
}
