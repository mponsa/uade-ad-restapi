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

import com.distribuidas.mywebserver.dto.DetallePedidoDTO;
import com.distribuidas.mywebserver.dto.PedidoDTO;
import com.distribuidas.mywebserver.dto.UsuarioDTO;
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
	@RequestMapping(value = "/crearPedidoCuit", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody String crearPedido(@RequestBody String jsonStr) throws JsonProcessingException{
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.writeValueAsString(Controlador.getInstancia().crearPedido(mapper.readValue(jsonStr,PedidoDTO.class).getCuit()));
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(e.getMessage());
			}
	}
	
	
	
	@RequestMapping(value = "/crearPedido", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody String crearPedidoView(@RequestBody String jsonStr) throws JsonProcessingException{
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.writeValueAsString(Controlador.getInstancia().crearPedido(mapper.readValue(jsonStr, PedidoView.class)));
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return mapper.writeValueAsString(e.getMessage());
			}
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/eliminarPedido", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String eliminarPedido(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().eliminarPedido(mapper.readValue(jsonStr, PedidoDTO.class).getNumeroPedido());
			return mapper.writeValueAsString("Pedido Borrado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}
		
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/facturarPedido", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	public @ResponseBody String facturarPedido(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().facturarPedido(mapper.readValue(jsonStr, PedidoDTO.class).getNumeroPedido());
			return mapper.writeValueAsString("Pedido Facturado");
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}	
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/agregarProductos", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String agregarProductosEnPedido(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			DetallePedidoDTO detalle = mapper.readValue(jsonStr, DetallePedidoDTO.class);
			Controlador.getInstancia().agregarProductoEnPedido(detalle.getId(), detalle.getProductoId(), detalle.getCantidad());
			return mapper.writeValueAsString("Pedido modificado");
		} catch (PedidoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (ProductoException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (IOException e) {
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
	
	
	/**Probed**/
	@RequestMapping(value = "/login", method = RequestMethod.POST , produces = "application/json" , consumes = "application/json")
	public @ResponseBody String login(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			boolean active = Controlador.getInstancia().login(mapper.readValue(jsonStr, UsuarioDTO.class).getUsuario(), mapper.readValue(jsonStr, UsuarioDTO.class).getPassword());
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}
	}
	
	
	/**Probed**/
	@RequestMapping(value = "/cambioPassword", method = RequestMethod.POST , produces = "application/json" ,  consumes="application/json")
	public @ResponseBody String cambioPassword(@RequestBody String jsonStr) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Controlador.getInstancia().cambioPassword(mapper.readValue(jsonStr, UsuarioDTO.class).getUsuario(), mapper.readValue(jsonStr, UsuarioDTO.class).getPassword());
			return mapper.writeValueAsString("Password actualizado");
		} catch (CambioPasswordException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (UsuarioException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return mapper.writeValueAsString(e.getMessage());
		}
	}	
}
