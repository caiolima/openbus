## O projeto
O OpenBus é um projeto de inovação aberta focada no densenvolvimento de uma aplicação social para compartilhamento de informações sobre o transporte público rodoviário.

Resumindo um pouco o que é o projeto, podemos chamá-lo de "Waze para quem anda de ônibus".

O objetivo deste projeto é criar um aplicativo para que o acesso a informações como "Quais ônibus devo pegar para chegar no lugar X" ou "Quais foram os últimos reports sobre a linha que estou esperando?" estejam acessíveis a todos.

## Arquitetura

O maior diferencial do OpenBus é a sua arquitetura. Para que os reports sejam efetuados e acessados publicamente, utilizaremos o Twitter como plataforma social. Com isso, utilizando hashtags padronizadas, será possível recuperar informações de reports sobre determinada linha em tempo real. Essa arquitetura também possibilita que todas as informações e reports sejam recuperadas por outras aplicações. Abaixo temos um esquema de como funciona nossa arquitetura.

<imagem da arquitetura>

As informações sobre itinerários serão armazenadas no próprio smartphone, onde a conexão com a internet passa a ser desnecessária para que o usuário tenha acesso a esse tipo de informação. 

## Custo de desenvolvimento

Como primeiramente estamos utilizando o twitter para armazenar as informações dos usuários, não haverá necessidade de uma aplicação servidora dedicada para que a aplicação funcione e seja validada. Dessa forma, o único custo será o desenvolvimento da aplicação em si.

 
