# TingoID

## Modelos API
En la carpeta api/model estan todos los modelos que tienen la estructura que se pasa por request a la api, por ejemplo para inicio de sesión se tiene: correo y password, entonces se pasan de esa forma a la api. Dentro de esa carpeta se deben crear todos esos body que seran pasados en la api


## Funciones para utilizar la API dentro de la APP
En el archivo TingoApi se pone el url base y las rutas para acceder a las api, con sus respectivas funciones por ejemplo
```
@POST("usuarios/sesion")
    Call <User> login (@Body LoginBody loginBody);
```

donde @POST es el método, lo que esta en dentro del paréntesis esa la ruta declarada en la api, y lo de abajo es como se va a llamar esa funcion dentro de la APP para acceder y conectarese con la api.
Dentro de este archivo se debe agregar todo eso que se implementa en la api


## Activity y Layout
Las Activities son todas la pantallas que aparecen en la app y se conectan entre ellas atraves de los botones y los startActivity()
Cada Activity tiene asociado un layaout que es lo visible, en la activity va la lógica. En cada layout cada cosa tiene un id que es lo que se llama en la activity asi que si se modifica algun nombre o algo se debe cambiar también en la activity y para eso hay que ocupar el Rename asi se cambian todas y se evitan errores.


## Android Manifest
En el AndroidManifest van declaradas todas las activity que se agregan de manera autómatica casi siempre, pero nunca esta demas revisar que esten todas, porque si no aparecen no se pueden utlizar dentro del aplicación.


## Imágenes
Dentro de la carpaeta Drawable van todas las imágenes que se utilizan en los layouts


## Build
El build solo se modifica para agregar funcionalidades a la app que es para importar librerias externas.
