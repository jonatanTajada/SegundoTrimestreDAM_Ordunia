package com.chat.utilities;

public class Constantes {

    // Configuración del servidor
    public static String SERVIDOR_IP = "127.0.0.1";
    public static int SERVIDOR_PUERTO = 10101;

    // --- TÍTULOS ---
    // Generales
    public static final String TITULO_CONFIGURACION = "Configuración";
    public static final String ERROR_TITULO = "Error";

    // Servidor
    public static final String TITULO_SERVIDOR = "Servidor de Chat";
    public static final String TITULO_LOG_SERVIDOR = "Log del Servidor";
    public static final String TITULO_CHAT_PRIVADO_SERVIDOR = "Chat privado en el servidor: ";

    // Cliente
    public static final String TITULO_CLIENTE = "Cliente de Chat";
    public static final String TITULO_CHAT_PRIVADO = "Chat Privado: ";

    // --- MENSAJES ---
    // Cliente
    public static final String MENSAJE_INTRODUCE_NOMBRE = "Introduce tu nombre:";
    public static final String ERROR_NOMBRE_OBLIGATORIO = "El nombre de usuario es obligatorio.";
    public static final String MENSAJE_CONEXION_EXITOSA = "Conectado al servidor como ";
    public static final String ERROR_CONEXION_SERVIDOR = "No se pudo conectar al servidor: ";
    public static final String ERROR_CONEXION_PERDIDA = "Conexión perdida con el servidor.";
    public static final String ERROR_CERRAR_CONEXION = "Error al cerrar la conexión: ";

    // Servidor
    public static final String MENSAJE_SERVIDOR_INICIADO = "Servidor iniciado en el puerto: ";
    public static final String MENSAJE_ESPERANDO_CLIENTES = "Esperando clientes...";
    public static final String MENSAJE_CLIENTE_CONECTADO = "Cliente conectado desde: ";
    public static final String MENSAJE_ERROR_SERVIDOR = "Error en el servidor: ";
    public static final String MENSAJE_USUARIO_CONECTADO = " se ha conectado.";
    public static final String MENSAJE_USUARIO_DESCONECTADO = " se ha desconectado.";
    public static final String MENSAJE_SERVIDOR_USUARIO_DESCONECTADO = "El usuario no está conectado.";
    public static final String MENSAJE_USUARIOS_ACTUALIZADOS = "Usuarios conectados actualizados: ";
    public static final String MENSAJE_SERVIDOR_DETENIDO = "Servidor detenido.";
    public static final String MENSAJE_ERROR_SERVIDOR_DETENER = "Error al detener el servidor: ";
    public static final String ERROR_NOMBRE_DUPLICADO = "Error: El nombre de usuario ya está en uso. Por favor, elige otro nombre.";

    // --- PREFIJOS DE MENSAJES ---
    public static final String PREFIJO_USUARIOS_CONECTADOS = "Usuarios conectados:";
    public static final String PREFIJO_GLOBAL = "Global:";
    public static final String PREFIJO_PRIVADO = "(Privado):";
    public static final String PREFIJO_ENVIO_GLOBAL = "(Global): ";
    public static final String PREFIJO_TU = "Tú: ";
    public static final String PREFIJO_TU_PRIVADO = "Tú (Privado): ";

    // --- TEXTOS GENERALES ---
    public static final String TODOS = "Todos";
    public static final String BOTON_ENVIAR = "Enviar";
}
