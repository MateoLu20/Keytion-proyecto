package com.example.keytionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Constantes para los conjuntos de caracteres
const val CHARS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
const val CHARS_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
const val CHARS_NUMBERS = "0123456789"
const val CHARS_SYMBOLS = "!@#$%^&*()-_+=[]{}|;:,.<>?/~"

// NAVEGACIÓN: DEFINICIÓN DE PANTALLAS
sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Checker : Screen("checker")
    object Generator : Screen("generator")
    object Info : Screen("info") // NUEVA PANTALLA
}

// --- TEMA DE LA APLICACIÓN ---
@Composable
fun KeytionTheme(content: @Composable () -> Unit) {
    // Se define un esquema de colores oscuros para esta app
    val darkColorScheme = darkColorScheme(
        primary = Color(0xFF6366F1),
        onPrimary = Color.White,
        background = Color(0xFF0F172A),
        surface = Color(0xFF1E293B),
        onBackground = Color.White,
        onSurface = Color.White,
        error = Color(0xFFEF4444)
    )
    MaterialTheme(
        colorScheme = darkColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}

// --- ACTIVITY PRINCIPAL ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeytionTheme {
                // El componente KeytionApp manejará el estado de la navegación central
                KeytionApp()
            }
        }
    }
}

// GESTOR DE NAVEGACIÓN CENTRAL
@Composable
fun KeytionApp() {
    // 1.Estado que rastrea la pantalla actual. Inicia en el menú.
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Menu) }

    // 2.Función que se pasa a los componentes para cambiar la pantalla
    val navigateTo: (Screen) -> Unit = { screen ->
        currentScreen = screen
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Se usa Box para apilar el contenido en el centro sin preocuparse por el padding del Scaffold
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (currentScreen) {
                is Screen.Menu -> MainMenuView(onNavigateTo = navigateTo)
                is Screen.Checker -> PasswordCheckerScreen(onNavigateTo = navigateTo)
                is Screen.Generator -> PasswordGeneratorScreen(onNavigateTo = navigateTo)
                is Screen.Info -> PasswordInfoScreen(onNavigateTo = navigateTo)
            }
        }
    }
}

// PANTALLA: INFORMACIÓN DE SEGURIDAD
@Composable
fun PasswordInfoScreen(onNavigateTo: (Screen) -> Unit, modifier: Modifier = Modifier) {
    // Lista de consejos de seguridad para los usuarios
    val infoItems = remember {
        listOf(
            SecurityInfo(
                title = "Longitud es Clave",
                description = "Una contraseña debe tener al menos 12 a 16 caracteres. Cuanto más larga, más tiempo le tomará a un atacante descifrarla.",
                icon = Icons.Default.ZoomIn,
                color = Color(0xFF10B981) // Emerald Green
            ),
            SecurityInfo(
                title = "Variedad de Caracteres",
                description = "Combina minúsculas, mayúsculas, números y símbolos. Esto aumenta la complejidad exponencialmente.",
                icon = Icons.Default.Shuffle,
                color = Color(0xFFF97316) // Orange
            ),
            SecurityInfo(
                title = "Evita Información Personal",
                description = "Nunca uses nombres, fechas de nacimiento, nombres de mascotas o información que se pueda encontrar fácilmente en redes sociales.",
                icon = Icons.Default.Warning,
                color = Color(0xFFEF4444) // Red
            ),
            SecurityInfo(
                title = "Contraseñas Únicas",
                description = "Utiliza una contraseña diferente para cada servicio. Si una se ve comprometida, las otras estarán seguras. Un gestor de contraseñas ayuda mucho.",
                icon = Icons.Default.Security,
                color = Color(0xFF6366F1) // Indigo
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Título y Botón de Regreso
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigateTo(Screen.Menu) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                }
                Text(
                    text = "¿Contraseña Segura?",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            // Mensaje introductorio
            Text(
                text = "Aprende los principios básicos para crear y mantener claves que realmente protejan tus cuentas y datos personales.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            )
        }

        // Lista de tarjetas de información
        items(infoItems) { info ->
            InfoCard(info = info)
        }
    }
}

// Componente para mostrar un consejo de seguridad
@Composable
fun InfoCard(info: SecurityInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono destacado
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(info.color.copy(alpha = 0.2f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = info.icon,
                    contentDescription = info.title,
                    tint = info.color,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(16.dp))

            // Título y Descripción
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = info.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = info.color
                )
                Text(
                    text = info.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// Data class para la información
data class SecurityInfo(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)


// PANTALLA: COMPROBADOR DE CONTRASEÑAS
@Composable
fun PasswordCheckerScreen(onNavigateTo: (Screen) -> Unit, modifier: Modifier = Modifier) {
    var password by remember { mutableStateOf("") }

    val strengthLevel = remember { mutableIntStateOf(0) }

    // Lógica: la fuerza depende del largo del password
    LaunchedEffect(password) {
        strengthLevel.intValue = when {
            password.length < 5 -> 0 // Baja
            password.length < 10 -> 1 // Media
            password.length < 15 -> 2 // Fuerte
            else -> 3 // Excelente
        }
    }

    // Se usa LazyColumn para que la pantalla sea desplazable si llega a ser necesario mas adelante
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            // Título y Botón de Regreso (Añadido para la navegación)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigateTo(Screen.Menu) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                }
                Text(
                    text = "Comprobador de Contraseñas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f) // Ocupa el espacio restante
                )
            }
        }

        item {
            // Mensaje de descripción
            Text(
                text = "Escribe tu nueva contraseña en la caja de abajo. Recuerda que esta clave será usada para proteger tus datos personales.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Input de la Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Introduce tu clave aquí") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Default.VpnKey, contentDescription = "Clave")
                }
            )
        }

        item {
            Spacer(Modifier.height(10.dp))
            // Barra de Fuerza
            PasswordStrengthBar(strengthLevel.intValue)
        }

        item {
            // Tarjeta de Requisitos
            RequirementChecklist(strengthLevel.intValue)
        }
    }
}

// Componente para el nivel de fuerza
@Composable
fun PasswordStrengthBar(strengthLevel: Int) {
    // Calculamos el progreso (0 a 1) para la barra
    val progress = strengthLevel.toFloat() / 3f

    // La lógica de los colores y etiquetas debe estar fuera del remember (Corregido aquí)
    val (color, label) = when (strengthLevel) {
        0 -> Pair(MaterialTheme.colorScheme.error, "Baja") // Rojo
        1 -> Pair(Color(0xFFFACC15), "Media") // Amarillo
        2 -> Pair(Color(0xFF22C55E), "Fuerte") // Verde
        3 -> Pair(Color(0xFF1D4ED8), "Excelente") // Azul
        else -> Pair(MaterialTheme.colorScheme.surfaceVariant, "Sin datos")
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Fuerza: $label",
            fontWeight = FontWeight.SemiBold,
            color = color
        )
        Spacer(Modifier.height(8.dp))
        // La barra de progreso
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

// Tarjeta para los requisitos de la contraseña
@Composable
fun RequirementChecklist(strengthLevel: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Requisitos de una Contraseña Segura",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Lista de requisitos simulados.
            // La condición (isMet) está vinculada a la misma lógica de largo del password para ser simple.
            val requirements = listOf(
                Pair("Mínimo 8 caracteres", strengthLevel >= 1),
                Pair("Incluir letras mayúsculas", strengthLevel >= 2),
                Pair("Incluir al menos un número", strengthLevel >= 1),
                Pair("Incluir al menos un símbolo (!@#$)", strengthLevel >= 3)
            )

            requirements.forEach { (text, isMet) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        // Icono de Check o Cancel
                        imageVector = if (isMet) Icons.Default.CheckCircle else Icons.Default.Cancel,
                        contentDescription = null,
                        tint = if (isMet) Color(0xFF22C55E) else MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(text, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}


// PANTALLA: GENERADOR DE CONTRASEÑAS
@Composable
fun PasswordGeneratorScreen(onNavigateTo: (Screen) -> Unit, modifier: Modifier = Modifier) {
    // ----------------------------------------------------
    // ESTADO
    // ----------------------------------------------------
    var length by remember { mutableStateOf(16f) } // Longitud inicial de 16
    var includeUppercase by remember { mutableStateOf(true) }
    var includeNumbers by remember { mutableStateOf(true) }
    var includeSymbols by remember { mutableStateOf(false) }
    var generatedPassword by remember { mutableStateOf("Pulsa Generar Clave") }

    // LÓGICA

    // Genera la contraseña basada en el estado actual.
    fun generatePassword() {
        var charPool = CHARS_LOWERCASE // Las minúsculas siempre van incluidas

        if (includeUppercase) charPool += CHARS_UPPERCASE
        if (includeNumbers) charPool += CHARS_NUMBERS
        if (includeSymbols) charPool += CHARS_SYMBOLS

        val finalLength = length.toInt()

        // Validación simple para evitar errores
        if (charPool.isEmpty() || finalLength == 0) {
            generatedPassword = "Error: Sin caracteres seleccionados"
            return
        }

        val newPassword = buildString {
            repeat(finalLength) {
                // Generación simple y aleatoria
                val randomIndex = (charPool.indices).random()
                append(charPool[randomIndex])
            }
        }
        generatedPassword = newPassword
    }

    // Se usa LazyColumn para que la pantalla sea desplazable si llega a ser necesario mas adelante
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            // Título y Botón de Regreso
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigateTo(Screen.Menu) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                }
                Text(
                    text = "Generador de Contraseñas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            // 1. CONTROL DE LONGITUD
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Longitud: ${length.toInt()} caracteres",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    value = length,
                    onValueChange = { length = it },
                    valueRange = 8f..32f, // Rango de 8 a 32 caracteres
                    steps = 23, // 32 - 8 = 24 valores (24 - 1 paso)
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            // 2. OPCIONES DE CARACTERES
            Text(
                text = "Incluir Tipos de Caracteres",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surface).padding(16.dp)) {

                // Opción Minúsculas (siempre activo)
                OptionCheckbox(text = "Minúsculas (a-z)", checked = true, onCheckedChange = {}, enabled = false)

                // Opción Mayúsculas
                OptionCheckbox(
                    text = "Mayúsculas (A-Z)",
                    checked = includeUppercase,
                    onCheckedChange = { includeUppercase = it }
                )
                // Opción Números
                OptionCheckbox(
                    text = "Números (0-9)",
                    checked = includeNumbers,
                    onCheckedChange = { includeNumbers = it }
                )
                // Opción Símbolos
                OptionCheckbox(
                    text = "Símbolos (!@#$%)",
                    checked = includeSymbols,
                    onCheckedChange = { includeSymbols = it }
                )
            }
        }

        item {
            // 3. BOTÓN GENERAR
            Button(
                onClick = { generatePassword() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Generar Contraseña", fontSize = 18.sp)
            }
        }

        item {
            // 4. RESULTADO
            OutlinedTextField(
                value = generatedPassword,
                onValueChange = {},
                readOnly = true,
                label = { Text("Contraseña Generada") },
                trailingIcon = {
                    IconButton(onClick = { /* TODO: Implementar copia al portapapeles */ }) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar Contraseña",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}


// --- MENÚ PRINCIPAL ---
@Composable
fun MainMenuView(onNavigateTo: (Screen) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Círculo para el logo
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Icono del logo
            Icon(
                Icons.Default.Lock,
                contentDescription = "Logo Keytion",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Nombre de la App
        Text(
            text = "Keytion",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(48.dp))

        // Botón 1: Comprobador de contraseñas
        MenuButton(
            label = "Comprobador de Contraseñas",
            icon = Icons.Default.CheckCircle,
            onClick = { onNavigateTo(Screen.Checker) }
        )

        Spacer(Modifier.height(25.dp))

        // Botón 2: Generador de contraseñas
        MenuButton(
            label = "Generador de Contraseñas",
            icon = Icons.Default.Refresh,
            onClick = { onNavigateTo(Screen.Generator) } //
        )

        Spacer(Modifier.height(25.dp))

        // Botón 3: Información de seguridad (ACTUALIZADO)
        MenuButton(
            label = "¿Qué es una Contraseña Segura?",
            icon = Icons.Default.Info,
            onClick = { onNavigateTo(Screen.Info) }
        )

    }
}

// Componente reutilizable para los botones del menú
@Composable
fun MenuButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Text(label, fontSize = 20.sp)
        }
    }
}

// Componente reutilizable para los Checkboxes de opciones.
@Composable
fun OptionCheckbox(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, enabled: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge, color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    }
}


// --- VISTA PREVIA (Para Android Studio) ---
@Preview(showBackground = true)
@Composable
fun InfoScreenPreview() {
    KeytionTheme {
        PasswordInfoScreen(onNavigateTo = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CheckerScreenPreview() {
    KeytionTheme {
        // Le pasamos una función lambda vacía al Preview
        PasswordCheckerScreen(onNavigateTo = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    KeytionTheme {
        // Le pasamos una función lambda vacía al Preview
        MainMenuView(onNavigateTo = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GeneratorScreenPreview() {
    KeytionTheme {
        // Le pasamos una función lambda vacía al Preview
        PasswordGeneratorScreen(onNavigateTo = {})
    }
}