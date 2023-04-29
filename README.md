# ⚓ BATTLESHIP GAME ⚓
<h4> 
	🚧 🚀 Em construção...  🚧
</h4>

### Tabela de conteúdos
<!--ts-->
* [Sobre](#Sobre)
* [Features](#Features)
* [Pré-Requisitos](#Pré-Requisitos)
* [Tecnologias](#tecnologias)
<!--te-->

### Sobre

<p>BatlleShip ( Batalha naval ) é um jogo de tabuleiro de dois jogadores, no qual os jogadores têm de adivinhar em que 
quadrados estão os navios do oponente. Seu objectivo é derrubar os barcos do oponente adversário, ganha quem derrubar 
todos os navios adversários primeiro. </p>

<p>Antes do início do jogo, cada jogador coloca os seus navios nos quadros, alinhados horizontalmente ou verticalmente. 
O número de navios permitidos é igual para ambos jogadores e os navios não podem se sobrepor.</p>

<p>Após os navios terem sido posicionados o jogo continua numa série de turnos. Em cada turno, um jogador diz 
um quadrado, o qual é identificado pela letra e número, na grelha do oponente, se houver um navio nesse quadrado, 
é colocada uma marca vermelha, senão houver é colocada uma marca branca.<p/>

<p>Os tipos de navios são:</p>

- porta-aviões (cinco quadrados)
- navios-tanque (quatro quadrados)
- contratorpedeiros (três quadrados)
- submarinos (dois quadrados)

<p>Vale notar que os quadrados que compõem um navio devem estar conectados e em fila reta.</p>

<p>Numa das variações deste jogo, as grelhas são de dimensão 10x10, e o número de navios são: 1, 2, 3 e 4 respectivamente.</p>

### Features

- [ ] | Cadastro de um novo jogado
- [ ] | Convite para uma partida
  - [ ] | Após um outro jogador se juntar a partida, torne o convite inválido. 
- [ ] | Juntar-se a uma partida
- [ ] | Lógica de ataque
  - [ ] | Se existe uma embarcação no alvo deve subtrair 1 ponto da vida e marca o local como atingido, caso contratio apenas marque o local como atingido
  - [ ] | Deve alternar o jogado liberado para ataque a cada turno.
  - [ ] | Se todas as embarcações de um jogador forem atingidas o jogo termina e o jogador com embarcações no tabuleiro é o vencedor. 
- [ ] | Swagger

[//]: # (- [ ] | )

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [sdkman](https://sdkman.io/install), [Docker Desktop](https://www.docker.com/products/docker-desktop/) e o JDK17 do Java.

Você pode instalar o java atraves do sdkman:

```bash
# Amazon corretto 17.0.6
$ sdk install java 17.0.6-amzn
```
Além disto é bom ter um editor para trabalhar com o código como o [Intellij](https://www.jetbrains.com/pt-br/idea/download/) ou [VSCode](https://code.visualstudio.com/)


### Plugins
- ktlint


### 🎲 Rodando o Back End (servidor)

```bash
# Clone este repositório
$ git clone https://github.com/Jose-Isaac/back-battleship-service.git
```

```bash
Abra o projeto no intellij ou se preferir via comando line:
# Vá para a pasta desse projeto
$ cd ./back-battleship-service
```

```bash
# Instale as dependências
$ ./gradle build
```

```bash
# Será necessãrio informar os dados de conecxão com a base e informar a secret usada para o JWT:

# config local para a base de dados
spring.jpa.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/battleship
spring.datasource.username=development
spring.datasource.password=development

# config local para as migrations
flyway.user=development
flyway.password=development
flyway.schemas=battleship

# # config local para a secret usada na assinatura do token
api.security.jwt.secret=JWT_SECRET

# Esses dados são utilizadas no nosso application.properties que fica localizado em:
$ cd /src/main/resources
```

``` bash
# Você pode iniciar o banco de dados pelo docker do projeto, acesse a pasta docker:
$ cd docker

# Com o docker iniciado, rode o seguinte comando:
$ docker-compose -f docker-compose-dev up
```

```bash
O servidor inciará na porta 8080 - acesse <http://localhost:8080>
```

### 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:
- TBD
