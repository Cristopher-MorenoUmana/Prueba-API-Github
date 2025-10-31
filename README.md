# Prueba-API-Github

## Requisitos
Para ejecutar el programa se necesita **JavaFX versión 25** y **Java 25**.

## Endpoints usados
- https://api.github.com/users/{username}
- https://api.github.com/users/{username}/repos?per_page=100&sort=updated
- https://api.github.com/repos/{username}/{repo}/languages

Estos endpoints se utilizan para obtener los datos del usuario de GitHub, la información de los repositorios y la lista de lenguajes usados en cada repositorio, con el fin de generar los gráficos correspondientes.

---

![Interfaz inicial](https://github.com/user-attachments/assets/7361d30f-7efb-4458-abf1-b997f4d3d016)

En esta captura de pantalla se puede observar la **interfaz inicial**.  

En ella se debe ingresar el nombre del usuario a consultar. Las opciones estarán bloqueadas hasta que el nombre de usuario sea válido y exista en GitHub.  

Para esto, se realiza una consulta a `https://api.github.com/users/{username}` para verificar si devuelve un valor con el nombre ingresado.  

Esto se hace con el fin de simplificar el código más adelante y evitar errores por valores nulos al mostrar los datos en la interfaz.

---

![Perfil del usuario](https://github.com/user-attachments/assets/76f6d227-e849-453d-aba1-cbdc6c941c00)

En esta otra captura se muestra la opción **“Información de perfil”**, la cual permite visualizar los datos del perfil del usuario.  

Si algunos campos aparecen vacíos o con valor `null`, es porque el JSON devuelto por la API contiene `""` o directamente `null` cuando el usuario no ha proporcionado esa información en su perfil.

---

![Información de los repos](https://github.com/user-attachments/assets/5186afdf-b4cc-4583-9f98-479cdee7af48)

![Información de los repos](https://github.com/user-attachments/assets/1d7012c0-a293-47ee-9cd6-92c080eebf99)

En estas últimas capturas se utilizó la opción **“Información de repos”**, que muestra los datos de todos los repositorios públicos del usuario.

Para el diseño de esta interfaz se buscó evitar la saturación visual, por lo que se optó por un formato similar al de un libro, con botones **Siguiente** y **Anterior** para navegar entre la información de los repositorios sin mostrarlos todos al mismo tiempo.  

Este enfoque resulta útil porque permite visualizar cualquier cantidad de repositorios (`n`) de forma ordenada y sin ocupar demasiado espacio en pantalla.

Además, se incluye un gráfico que muestra de manera visual qué tan utilizados fueron los diferentes lenguajes de programación en cada repositorio.

---
