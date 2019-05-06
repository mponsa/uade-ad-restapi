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
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	  @RequestMapping(value = "/", method = RequestMethod.GET) public String
	  home(Locale locale, Model model) {
	  logger.info("Welcome home! The client locale is {}.", locale);
	  
	  Date date = new Date(); DateFormat dateFormat =
	  DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	  
	  String formattedDate = dateFormat.format(date);
	  
	  model.addAttribute("serverTime", formattedDate );
	  
	  return "home"; }
	 
	/**Probed**/
	@RequestMapping(value = "/clientes", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getClientes() throws JsonProcessingException{
		List<ClienteView> list = Controlador.getInstancia().getClientes();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/pedido", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getPedidoById(@RequestParam int numero) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			PedidoView pedido = Controlador.getInstancia().getPedidoById(numero);
			return mapper.writeValueAsString(pedido);
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/rubros", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getRubros() throws JsonProcessingException{
		List<RubroView> list = Controlador.getInstancia().getRubros();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/subrubros", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getSubRubros() throws JsonProcessingException{
		List<SubRubroView> list = Controlador.getInstancia().getSubRubros();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/productos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getProductos() throws JsonProcessingException{
		List<ProductoView> list = Controlador.getInstancia().getProductos();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/productosRubro", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	public @ResponseBody String getProductosByRubro(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductoView> list = Controlador.getInstancia().getProductosByRubro(mapper.readValue(jsonStr, RubroView.class));
			return mapper.writeValueAsString(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(e.getMessage());
		}
		
		
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/productosSubRubro", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	public @ResponseBody String getProductosBySubRubro(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductoView> list = Controlador.getInstancia().getProductosBySubRubro(mapper.readValue(jsonStr, SubRubroView.class));
			return mapper.writeValueAsString(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(e.getMessage());
		}
	}
	
		
	/**Probed**/
	@RequestMapping(value = "/crearPedido", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody String crearPedido(@RequestHeader(value = "cuit") String cuit) throws JsonProcessingException{
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.writeValueAsString(Controlador.getInstancia().crearPedido(cuit));
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(e.getMessage());
			}
	}
	
	
	
//	@RequestMapping(value = "/crearPedido", method = RequestMethod.POST, produces = "application/json")
//		public @ResponseBody String crearPedidoView(@RequestBody String jsonStr) throws JsonProcessingException{
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				return mapper.writeValueAsString(Controlador.getInstancia().crearPedido(mapper.readValue(jsonStr, PedidoView.class)));
//			} catch (ClienteException e) {
//				// TODO Auto-generated catch block
//				return mapper.writeValueAsString(e.getMessage());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				return mapper.writeValueAsString(e.getMessage());
//			}
//	}
	
	
	/**Probed**/
	@RequestMapping(value = "/eliminarPedido", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String eliminarPedido(@RequestHeader(value = "numero") int numero) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		Controlador.getInstancia().eliminarPedido(numero);
		return mapper.writeValueAsString("Pedido Borrado");
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/facturarPedido", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String facturarPedido(@RequestHeader(value = "numero") int numero) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().facturarPedido(numero);
			return mapper.writeValueAsString("Pedido Facturado");
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}	
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/agregarProductos", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String agregarProductosEnPedido(@RequestHeader(value = "numero") int numero, @RequestHeader(value="idProducto") int idProducto, @RequestHeader(value="cantidad") int cantidad) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().agregarProductoEnPedido(numero, idProducto, cantidad);;
			return mapper.writeValueAsString("Pedido modificado");
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}	
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/altaProducto",method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String altaProducto(@RequestBody String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().altaProducto(mapper.readValue(jsonStr, ProductoView.class));
			return "Producto creado!";
		} catch (RubroException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		} catch (SubRubroException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		}
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/bajaProducto",method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String bajaProducto(@RequestBody String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().bajaProducto(mapper.readValue(jsonStr, ProductoView.class));
			return "Producto borrado!";
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		}
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/modificarProducto",method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String modificarProducto(@RequestBody String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().modificaProducto(mapper.readValue(jsonStr, ProductoView.class));
			return "Producto modificado!";
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e);
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json" )
	public @ResponseBody String login(@RequestHeader(value = "username") String username, @RequestHeader(value = "password") String password) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		logger.info("Usuario {} Password {}",username,password);
		try {
			boolean active = Controlador.getInstancia().login(username, password);
			return mapper.writeValueAsString(active);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (CambioPasswordException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (UsuarioException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}
	}	
}
