# API REST - RESERVAS (v1.0)

API REST encargada de realizar **reservas** para un restaurante empleando **Stripe** como servicio para gestionar los pagos.

![](https://gblobscdn.gitbook.com/assets%2F-LKmGSg4qhizjqeDPHJC%2F-LNYSvjOO0iDvi4jFPoT%2F-LNYTa1g9lqk3aPeXmh3%2Fimage.png?alt=media&token=270371cc-0d76-449b-a892-0bccc0ed8fa4)

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
He cargado el application.properties en este repositorio, pero la información sensible la he rellenado con **xxxx**
