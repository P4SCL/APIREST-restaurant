# API REST - RESERVAS (v1.0)

API REST encargada de realizar **reservas** para un restaurante empleando **Stripe** como servicio para gestionar los pagos.

![](https://firebasestorage.googleapis.com/v0/b/certificadoapp-3bb25.appspot.com/o/imagenes%2F3%20capas.jpg?alt=media&token=7045f5de-c5ee-4b5c-916c-a2f816c63be1)

### Tecnologías empleadas:
- Java 8.
- Maven.
- Spring Java Mail.
- Spring 5 .
- Spring boot 2.4.3.
- Spring Data JPA.
- Swagger.
- Stripe - API.
- Heroku Cloud.
- MYSQL 8.

------------
#### FLUJO
1.  Persona hace una reserva en un restaurante. Tiene dos opciones: puede sólo reservar o reservar y pagar.
2. Al reservar se le enviará un mensaje a su correo indicando que su reserva se ha realizado, para implementar esta funcionalidad esto se ha empleado Java Mail. 
3. Al reservar se le brindará un **código único** que le servirá para cancelar la reserva en caso no pueda asistir al restaurante.
4. Para realizar el pago se tendrá que introducir los datos de la tarjeta. Este pago es manejado por Stripe.

#### ISSUES
1.- Esta API REST estará desplegada en Heroku y documentada con Swagger.
2.- Añadir más funcionalidades a los controladores para hacer más completa la aplicación.

#### NOTA
He cargado el application.properties en este repositorio, la información sensible la he rellenado con **xxxx**.
