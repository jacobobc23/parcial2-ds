package exceptions;

/**
 *
 * @author jacobobc
 */
public class OrderCustomerException extends RuntimeException {
    public OrderCustomerException() {
        super("No se puede eliminar el cliente ya que actualmente tiene pedidos.");
    }
}
