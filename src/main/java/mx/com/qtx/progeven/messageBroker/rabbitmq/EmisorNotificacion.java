package mx.com.qtx.progeven.messageBroker.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import mx.com.qtx.progeven.core.IPublicadorNotificaciones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmisorNotificacion implements IPublicadorNotificaciones{
	protected String nombreExchange;
	protected String hostRabbitMQ;
	
	private static Logger bitacora = LoggerFactory.getLogger(EmisorNotificacion.class); 
	
	public EmisorNotificacion(String nombreIntermediario) {
		super();
		this.nombreExchange = nombreIntermediario;
	}
	public String getNombreExchange() {
		return nombreExchange;
	}
	public void setNombreExchange(String nombreExchange) {
		this.nombreExchange = nombreExchange;
	}

	public String getHostRabbitMQ() {
		return hostRabbitMQ;
	}

	public void setHostRabbitMQ(String hostRabbitMQ) {
		this.hostRabbitMQ = hostRabbitMQ;
	}

	public void emitirNotificacion(String mensaje) {
		ConnectionFactory fabricaConexiones = new ConnectionFactory();
		fabricaConexiones.setHost(this.hostRabbitMQ);
		try(Connection conexion = fabricaConexiones.newConnection();
			Channel canal = conexion.createChannel()	){
			
			canal.exchangeDeclare(this.nombreExchange, "fanout");
			
//			 void basicPublish​(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body) throws IOException
			canal.basicPublish(this.nombreExchange, "", 
					          null, mensaje.getBytes("UTF-8"));
			
			bitacora.info("Mensaje general enviado: " + mensaje);
		} 
		catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

}
