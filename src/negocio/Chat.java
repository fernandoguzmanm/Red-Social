package negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import launcher.view.ViewUtils;

public class Chat {

	public List<InfoUsuario> usuarios = new ArrayList<>();
	public static record Info(String nombreUser, String mensaje) {
	};
	private List<Info> _mensajes;

	private InfoUsuario _emisor;
	private InfoUsuario _receptor;
	
	public Chat() {
		this.usuarios = new ArrayList<>();
	}

	public Chat(InfoUsuario emisor, InfoUsuario receptor) {
		_emisor = emisor;
		_receptor = receptor;
		_mensajes = new ArrayList<>();
		init_mensajes();
	}

	public void addMensaje(String userEmisor, String integrante_2, String textoMensaje) {
		
		if( userEmisor.equalsIgnoreCase(_emisor.get_nombre())  && integrante_2.equalsIgnoreCase(_receptor.get_nombre()))
			_mensajes.add(new Info(userEmisor, textoMensaje));
		else if( userEmisor.equalsIgnoreCase(_receptor.get_nombre())  && integrante_2.equalsIgnoreCase(_emisor.get_nombre()))
			_mensajes.add(new Info(userEmisor, textoMensaje));
	
	}

	private void init_mensajes() {
		try {
			InputStream archivoMensajes = new FileInputStream(new File("mensajes/mensajes.json"));
			JSONObject json = new JSONObject(new JSONTokener(archivoMensajes));

			JSONArray chats_usuarios = json.optJSONArray("chats");

			if (chats_usuarios != null) {
				for (int i = 0; i < chats_usuarios.length(); i++) {
					JSONObject chat = chats_usuarios.getJSONObject(i);

					String emisor = chat.optString("emisor");
					String receptor = chat.optString("receptor");
					JSONArray array = chat.optJSONArray("mensajes");
					if (array != null && emisor != null && receptor != null) {
						for (int k = 0; k < array.length(); k++) {
							JSONObject info = array.getJSONObject(k);
							String user = info.getString("user");
							String texto = info.getString("texto");

							addMensaje(user, receptor, texto);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			ViewUtils.showErrorMsg("No se pudo encontrar el archivo con los mensajes");
		}
	}

	public List<Info> get_mensajes() {
		return Collections.unmodifiableList(_mensajes);
	}

	private JSONObject build_json() {
		JSONObject json_chats = new JSONObject();
		JSONArray chats_usuarios = new JSONArray();
		try {
			// No puedo perder los el resto de mensajes de otros chats, asi que haré un
			// bucle
			// para guardar los mensajes que ya habia
			InputStream archivoMensajes = new FileInputStream(new File("mensajes/mensajes.json"));
			JSONObject json = new JSONObject(new JSONTokener(archivoMensajes));
			JSONArray chats = json.optJSONArray("chats");

			if (chats != null) {
				for (int i = 0; i < chats.length(); i++) {
					JSONObject chat = chats.getJSONObject(i);
					chats_usuarios.put(chat);
				}
			}

		} catch (Exception e) {
			ViewUtils.showErrorMsg("Error al cargar el archivo de mensajes");
		}

		// y ahora voy a añadir los mensajes de este chat
		JSONArray chat = new JSONArray();
		for (Info e : _mensajes) {
			JSONObject info = new JSONObject();

			info.put("user", e.nombreUser);
			info.put("texto", e.mensaje);
			chat.put(info);
		}
		JSONObject json = new JSONObject();
		json.put("emisor", _emisor.get_nombre());
		json.put("receptor", _receptor.get_nombre());
		json.put("mensajes", chat);

		chats_usuarios.put(json);

		json_chats.put("chats", chats_usuarios);

		return json_chats;

	}

	public void actualizarFichero() {
		try {
			// Creo la comunicacion para poder escribir los mensajes en el archivo
			JSONObject json = build_json();

			OutputStream archivoMensajes = new FileOutputStream(new File("mensajes/mensajes.json"));
			PrintStream p = new PrintStream(archivoMensajes);
			p.println(json);

		} catch (FileNotFoundException e) {
			ViewUtils.showErrorMsg("No se pudo encontrar el archivo con los mensajes");
		}
	}

}
