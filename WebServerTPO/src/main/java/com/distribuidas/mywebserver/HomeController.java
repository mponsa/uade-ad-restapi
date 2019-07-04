package com.distribuidas.mywebserver;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.distribuidas.mywebserver.dto.DetallePedidoDTO;
import com.distribuidas.mywebserver.dto.PedidoDTO;
import com.distribuidas.mywebserver.dto.UsuarioDTO;
import com.distribuidas.mywebserver.message.ErrorCode;
import com.distribuidas.mywebserver.message.ReturnMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlador.Controlador;
import exceptions.CambioPasswordException;
import exceptions.ClienteException;
import exceptions.LoginException;
import exceptions.PedidoException;
import exceptions.ProductoException;
import exceptions.RubroException;
import exceptions.SubRubroException;
import exceptions.UsuarioException;
import view.ClienteView;
import view.PedidoView;
import view.ProductoView;
import view.RubroView;
import view.SubRubroView;

/**
 * Handles requests for the application home page.
 */
@Controller
@CrossOrigin(origins ="*")
public class HomeController {

	ReturnMessage rm = null; 
	
	@RequestMapping(value = "/", method = RequestMethod.GET) public String
	  home(Locale locale, Model model) {
	  //logger.info("Welcome home! The client locale is {}.", locale);
	  
	  Date date = new Date(); DateFormat dateFormat =
	  DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	  
	  String formattedDate = dateFormat.format(date);
	  
	  model.addAttribute("serverTime", formattedDate );
	  
	  return "home"; }
	
	/**Final**/
	@RequestMapping(value = "/clientes", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getClientes() throws JsonProcessingException{
		List<ClienteView> list = Controlador.getInstancia().getClientes();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",list));
	}
	
	
	/**Final**/
	@RequestMapping(value = "/pedido", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getPedidoById(@RequestParam int numero) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			PedidoView pedido = Controlador.getInstancia().getPedidoById(numero);
			return mapper.writeValueAsString(new ReturnMessage(0, "", "Consulta exitosa!", pedido));
		} catch (PedidoException e) {
			// TODO Auto-generated catch block			
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(), e.getStackTrace().toString(), e.getMessage(), "" ));
		}
	}
	
	

	
	/** Modificación: Método para obtener los pedidos con un estado particular. **/
	@RequestMapping(value = "/pedidos/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getPedidoByEstado(@RequestParam String estado) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<PedidoView> pedidos = Controlador.getInstancia().getPedidoByEstado(estado);
			return mapper.writeValueAsString(new ReturnMessage(0, "", "Consulta exitosa!", pedidos));
		} catch (PedidoException e) {
			// TODO Auto-generated catch block			
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(), e.getStackTrace().toString(), e.getMessage(), "" ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return(mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null)));
		}
	}
	
	
	/** Modificación: Método para obtener todos los pedidos. **/
	@RequestMapping(value = "/pedidos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getPedidos() throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<PedidoView> pedidos = Controlador.getInstancia().getPedidos();
			return mapper.writeValueAsString(new ReturnMessage(0, "", "Consulta exitosa!", pedidos));
		} catch (PedidoException e) {
			// TODO Auto-generated catch block			
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(), e.getStackTrace().toString(), e.getMessage(), "" ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return(mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null)));
		}
	}
	
	
	/** Modificación: Método para obtener los pedidos de un cliente. **/
	@RequestMapping(value = "/pedidos/cliente", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getPedidoByCliente(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<PedidoView> pedidos = Controlador.getInstancia().getPedidoByCliente(mapper.readValue(jsonStr, ClienteView.class).getNumero());
			return mapper.writeValueAsString(new ReturnMessage(0, "", "Consulta exitosa!", pedidos));
		} catch (PedidoException e) {
			// TODO Auto-generated catch block			
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(), e.getStackTrace().toString(), e.getMessage(), "" ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return(mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null)));
		}
	}
	
	
	
	/**Final**/
	@RequestMapping(value = "/rubros", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getRubros() throws JsonProcessingException{
		List<RubroView> list = Controlador.getInstancia().getRubros();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",list));
	}
	
	
	/**Final**/
	@RequestMapping(value = "/subrubros", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getSubRubros() throws JsonProcessingException{
		List<SubRubroView> list = Controlador.getInstancia().getSubRubros();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",list));
	}
	
	
	/**Final**/
	@RequestMapping(value = "/productos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getProductos() throws JsonProcessingException{
		List<ProductoView> list = Controlador.getInstancia().getProductos();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",list));
	}
	
	@RequestMapping(value = "/productoById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getProductoById(@RequestParam int identificador) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		ProductoView product = null;
		try {
			product = Controlador.getInstancia().getProductoByIdentificador(identificador);
			return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",product));
		} catch (ProductoException e) {
			return(mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PRODUCTO.getValue(),e.getStackTrace().toString(),e.getMessage(),null)));
		} 
		
		
	}
	
	
	/**Final**/
	@RequestMapping(value = "/productosRubro", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	public @ResponseBody String getProductosByRubro(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductoView> list = Controlador.getInstancia().getProductosByRubro(mapper.readValue(jsonStr, RubroView.class));
			return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",list));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return(mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null)));
		}
		
		
	}
	
	
	/**Final**/
	@RequestMapping(value = "/productosSubRubro", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	public @ResponseBody String getProductosBySubRubro(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductoView> list = Controlador.getInstancia().getProductosBySubRubro(mapper.readValue(jsonStr, SubRubroView.class));
			return mapper.writeValueAsString(new ReturnMessage(0,"","Consulta exitosa!",list));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return(mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null)));
		}
	}
	
		
	/**Final**/
	@RequestMapping(value = "/crearPedidoCuit", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody String crearPedido(@RequestBody String jsonStr) throws JsonProcessingException{
			ObjectMapper mapper = new ObjectMapper();
			try {
				int number = Controlador.getInstancia().crearPedido(mapper.readValue(jsonStr,PedidoDTO.class).getCuit());
				return mapper.writeValueAsString(new ReturnMessage(0,"","Pedido creado!",Controlador.getInstancia().getPedidoById(number)));
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_CLIENTE.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
			} catch (PedidoException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
			}
	}
	
	
	/**Final**/
	@RequestMapping(value = "/crearPedido", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody String crearPedidoView(@RequestBody String jsonStr) throws JsonProcessingException{
			ObjectMapper mapper = new ObjectMapper();
			try {
				int number = Controlador.getInstancia().crearPedido(mapper.readValue(jsonStr, PedidoView.class));
				return mapper.writeValueAsString(new ReturnMessage(0,"","Pedido creado!",Controlador.getInstancia().getPedidoById(number)));
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_CLIENTE.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
			} catch (PedidoException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
			}
	}
	
	
	/**Final**/
	@RequestMapping(value = "/eliminarPedido", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String eliminarPedido(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().eliminarPedido(mapper.readValue(jsonStr, PedidoDTO.class).getNumeroPedido());
			return mapper.writeValueAsString(new ReturnMessage(0,"","Pedido borrado!",null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}
		
	}
	
	
	/**Final**/
	@RequestMapping(value = "/facturarPedido", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	public @ResponseBody String facturarPedido(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().facturarPedido(mapper.readValue(jsonStr, PedidoDTO.class).getNumeroPedido());
			return mapper.writeValueAsString(new ReturnMessage(0,"","Pedido Facturado!",Controlador.getInstancia().getPedidoById(mapper.readValue(jsonStr, PedidoDTO.class).getNumeroPedido())));
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}	
	}
	
	
	/**Final**/
	@RequestMapping(value = "/agregarProductos", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String agregarProductosEnPedido(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			DetallePedidoDTO detalle = mapper.readValue(jsonStr, DetallePedidoDTO.class);
			Controlador.getInstancia().agregarProductoEnPedido(detalle.getId(), detalle.getProductoId(), detalle.getCantidad());
			return mapper.writeValueAsString(new ReturnMessage(0,"","Pedido modificado!",Controlador.getInstancia().getPedidoById(detalle.getId())));
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PEDIDO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PRODUCTO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}	
	}
	
	
	/**Final**/
	@RequestMapping(value = "/altaProducto",method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String altaProducto(@RequestBody String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().altaProducto(mapper.readValue(jsonStr, ProductoView.class));
			return mapper.writeValueAsString(new ReturnMessage(0,"","Producto creado!",mapper.readValue(jsonStr, ProductoView.class)));
		} catch (RubroException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_RUBRO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (SubRubroException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_SUBRUBRO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}
	}
	
	
	/**Final**/
	@RequestMapping(value = "/bajaProducto",method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String bajaProducto(@RequestBody String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().bajaProducto(mapper.readValue(jsonStr, ProductoView.class));
			return mapper.writeValueAsString(new ReturnMessage(0,"","Producto borrado!",null));
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PRODUCTO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}
	}
	
	
	/**Final**/
	//Nota: No funciona el método en el controlador del negocio, no hace el update a los campos.
	@RequestMapping(value = "/modificarProducto",method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String modificarProducto(@RequestBody String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().modificaProducto(mapper.readValue(jsonStr, ProductoView.class));
			return mapper.writeValueAsString(new ReturnMessage(0,"","Producto modifiado!",mapper.readValue(jsonStr, ProductoView.class)));
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_PRODUCTO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}
	}
	
	
	/**Final**/
	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json" , consumes = "application/json")
	public @ResponseBody String login(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			boolean active = Controlador.getInstancia().login(mapper.readValue(jsonStr, UsuarioDTO.class).getUsuario(), mapper.readValue(jsonStr, UsuarioDTO.class).getPassword());
			return mapper.writeValueAsString(new ReturnMessage(0,"","Usuario logueado!",active));
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_LOGIN.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (CambioPasswordException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_CAMBIOPASSWORD.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (UsuarioException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_USUARIO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}
	}
	
	
	/**Final**/
	@RequestMapping(value = "/cambioPassword", method = RequestMethod.POST , produces = "application/json" ,  consumes="application/json")
	public @ResponseBody String cambioPassword(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().cambioPassword(mapper.readValue(jsonStr, UsuarioDTO.class).getUsuario(), mapper.readValue(jsonStr, UsuarioDTO.class).getPassword());
			return mapper.writeValueAsString(new ReturnMessage(0,"","Password actualizado!",null));
		} catch (CambioPasswordException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_CAMBIOPASSWORD.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (UsuarioException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_USUARIO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(new ReturnMessage(ErrorCode.ERROR_IO.getValue(),e.getStackTrace().toString(),e.getMessage(),null));
		}
	}	
}
